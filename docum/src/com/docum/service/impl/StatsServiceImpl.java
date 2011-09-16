package com.docum.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.domain.Stats;
import com.docum.domain.Stats.CategoryDefects;
import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoDefect;
import com.docum.domain.po.common.CargoDefectGroup;
import com.docum.domain.po.common.Invoice;
import com.docum.service.InvoiceService;
import com.docum.service.StatsService;
import com.docum.util.stats.StatsCargoGroup;
import com.docum.util.stats.StatsCargoGroupInfo;
import com.docum.util.stats.StatsHelper;

@Service(StatsService.SERVICE_NAME)
@Transactional
public class StatsServiceImpl implements StatsService, Serializable {
	private static final long serialVersionUID = 1932858392646056209L;

	@Autowired
	private InvoiceService invoiceSvc;

	@Override
	public List<Stats.CargoParty> getCargoParties(Long invoiceId) {
		List<Stats.CargoParty> result = new ArrayList<Stats.CargoParty>();
		Invoice invoice = invoiceSvc.getObject(Invoice.class, invoiceId);
		if(invoice == null) {
			return result;
		}
		Collection<StatsCargoGroup> groups = StatsHelper.getCargoGroups(invoice.getContainers());
		for(StatsCargoGroup group : groups) {
			Stats.CargoParty party = makeParty(group);
			
			result.add(party);
		}
		return result;
	}

	private Stats.CargoParty makeParty(StatsCargoGroup group) {
		if(group.getCargoes().isEmpty()) {
			//TODO error
			return null;
		}
		
		StatsCargoGroupInfo info = group.getInfo();
		ArticleCategory articleCategory = info.getCategory();
		Article article = articleCategory.getArticle();
		
		List<Stats.CargoDefects> allDefects = new ArrayList<Stats.CargoDefects>();
		//Сначала рассчитываем все категории по каждому грузу
		for(Cargo cargo : group.getCargoes()) {
			allDefects.add(extractCargoDefects(cargo));
		}
		//Далее находим среднее арифметическое всех значений
		//Заодно проверяем, что все списки дефектов категорий имеют один размер
		Stats.CargoDefects averageDefects = new Stats.CargoDefects();
		List<Iterator<Stats.CategoryDefects>> iterators =
			new ArrayList<Iterator<Stats.CategoryDefects>>();
		//Итератор для определения позиции в расчете
		Iterator<Stats.CategoryDefects> refIterator = null;
		int count =-1;
		for(Stats.CargoDefects defects : allDefects) {
			if(count == -1) {
				count = defects.getCategoryDefects().size();
				refIterator = defects.getCategoryDefects().iterator();
			} else if(count != defects.getCategoryDefects().size()) {
				throw new RuntimeException("Wrong number of elements in cargo defects");
			}
			iterators.add(defects.getCategoryDefects().iterator());
		}
		//Собственно расчет среднего арифметического
		while(refIterator.hasNext()) {
			Stats.CategoryDefects refCatDefects = refIterator.next();
			Stats.CategoryDefects catDefects =
				new Stats.CategoryDefects(refCatDefects.getCategoryName(),
						refCatDefects.getCategoryEnglishName());
			double percentage = 0.0;
			for(Iterator<Stats.CategoryDefects> i : iterators) {
				percentage += i.next().getPercentage();
			}
			catDefects.setPercertage(percentage/iterators.size());
			averageDefects.addCategoryDefects(catDefects);
		}
		
		Stats.CargoParty party = new Stats.CargoParty(article.getName(), article.getEnglishName(),
				articleCategory.getName(), articleCategory.getEnglishName(), averageDefects);
		return party;
	}

	public Stats.CargoDefects extractCargoDefects(Cargo cargo) {
		Stats.CargoDefects cargoDefects = new Stats.CargoDefects();
		boolean isFirstCategory = true;
		double categoryPercentage = 0.0;
		for(CargoDefectGroup defectGroup : cargo.getInspectionInfo().getDefectGroups()) {
			Stats.CategoryDefects catDefects = new Stats.CategoryDefects(
					defectGroup.getArticleCategory().getName(),
					defectGroup.getArticleCategory().getEnglishName());
			cargoDefects.addCategoryDefects(catDefects);
			if(isFirstCategory) {
				//Первую категорию сейчас не считаем. Будет рассчитываться потом.
				isFirstCategory = false;
			} else {
				//Во вторую и последующие категории записываем проценты, рассчитанные в
				//предыдущих итерациях
				catDefects.setPercertage(categoryPercentage);
				categoryPercentage = 0.0;
			}
			//Рассчитываем проценты для следующей категории
			for(CargoDefect defect : defectGroup.getDefects()) {
				categoryPercentage += defect.getPercentage();
			}
		}
		//Добавляем категорию с браком и рассчитываем первую категорию.
		//TODO Сделать имена брака настраиваемыми
		Stats.CategoryDefects wasteDefects = new Stats.CategoryDefects("Брак", "Waste");
		cargoDefects.addCategoryDefects(wasteDefects);
		CategoryDefects mainCategory = cargoDefects.getMainCategory();
		categoryPercentage = 100.0;
		for(Stats.CategoryDefects category : cargoDefects.getCategoryDefects()) {
			if(!category.equals(mainCategory)) {
				categoryPercentage -= category.getPercentage();
			}
		}
		mainCategory.setPercertage(categoryPercentage);
		return cargoDefects;
	}
}
