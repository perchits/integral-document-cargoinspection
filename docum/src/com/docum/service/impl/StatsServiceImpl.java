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
import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Invoice;
import com.docum.service.ContainerService;
import com.docum.service.InvoiceService;
import com.docum.service.StatsService;
import com.docum.util.cargo.CargoUtil;
import com.docum.util.stats.StatsCargoGroup;
import com.docum.util.stats.StatsCargoGroupInfo;
import com.docum.util.stats.StatsHelper;

@Service(StatsService.SERVICE_NAME)
@Transactional
public class StatsServiceImpl implements StatsService, Serializable {
	private static final long serialVersionUID = 1932858392646056209L;

	@Autowired
	private InvoiceService invoiceSvc;
	@Autowired
	private ContainerService containerSvc;


	@Override
	public List<Stats.CargoDefects> calcAverageDefects(Long containerId) {
		List<Stats.CargoDefects> result = new ArrayList<Stats.CargoDefects>();
		Container container = containerSvc.getContainer(containerId);

		for(Cargo cargo : container.getActualCondition().getCargoes()) {
			result.add(CargoUtil.calcAverageDefects(cargo));
		}
		return result;
	}
	
	
	
	
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
		
		List<Stats.CargoDefectsOld> allDefects = new ArrayList<Stats.CargoDefectsOld>();
		//Сначала рассчитываем все категории по каждому грузу
		for(Cargo cargo : group.getCargoes()) {
			allDefects.add(extractCargoDefects(cargo));
		}
		//Далее находим среднее арифметическое всех значений
		//Заодно проверяем, что все списки дефектов категорий имеют один размер
		Stats.CargoDefectsOld averageDefects = new Stats.CargoDefectsOld();
		List<Iterator<Stats.CategoryDefectsOld>> iterators =
			new ArrayList<Iterator<Stats.CategoryDefectsOld>>();
		//Итератор для определения позиции в расчете
		Iterator<Stats.CategoryDefectsOld> refIterator = null;
		int count =-1;
		for(Stats.CargoDefectsOld defects : allDefects) {
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
			Stats.CategoryDefectsOld refCatDefects = refIterator.next();
			Stats.CategoryDefectsOld catDefects =
				new Stats.CategoryDefectsOld(refCatDefects.getCategoryName(),
						refCatDefects.getCategoryEnglishName());
			double percentage = 0.0;
			for(Iterator<Stats.CategoryDefectsOld> i : iterators) {
				percentage += i.next().getPercentage();
			}
			catDefects.setPercertage(percentage/iterators.size());
			averageDefects.addCategoryDefects(catDefects);
		}
		
		Stats.CargoParty party = new Stats.CargoParty(article.getName(), article.getEnglishName(),
				articleCategory.getName(), articleCategory.getEnglishName(), averageDefects);
		return party;
	}

	public Stats.CargoDefectsOld extractCargoDefects(Cargo cargo) {
		Stats.CargoDefectsOld cargoDefectsOld = new Stats.CargoDefectsOld();
//		boolean isFirstCategory = true;
//		double categoryPercentage = 0.0;
//		for(CargoDefectGroup defectGroup : cargo.getInspectionInfo().getDefectGroups()) {
//			Stats.CategoryDefectsOld catDefects = new Stats.CategoryDefectsOld(
//					defectGroup.getArticleCategory().getName(),
//					defectGroup.getArticleCategory().getEnglishName());
//			cargoDefects.addCategoryDefects(catDefects);
//			if(isFirstCategory) {
//				//Первую категорию сейчас не считаем. Будет рассчитываться потом.
//				isFirstCategory = false;
//			} else {
//				//Во вторую и последующие категории записываем проценты, рассчитанные в
//				//предыдущих итерациях
//				catDefects.setPercertage(categoryPercentage);
//				categoryPercentage = 0.0;
//			}
//			//Рассчитываем проценты для следующей категории
//			for(CargoDefect defect : defectGroup.getDefects()) {
//				categoryPercentage += defect.getPercentage();
//			}
//		}
//		//Добавляем категорию с браком и рассчитываем первую категорию.
//		//TODO Сделать имена брака настраиваемыми
//		Stats.CategoryDefectsOld wasteDefects = new Stats.CategoryDefectsOld("Брак", "Waste");
//		cargoDefects.addCategoryDefects(wasteDefects);
//		CategoryDefectsOld mainCategory = cargoDefects.getMainCategory();
//		categoryPercentage = 100.0;
//		for(Stats.CategoryDefectsOld category : cargoDefects.getCategoryDefects()) {
//			if(!category.equals(mainCategory)) {
//				categoryPercentage -= category.getPercentage();
//			}
//		}
//		mainCategory.setPercertage(categoryPercentage);
		return cargoDefectsOld;
	}
}
