package com.docum.util.cargo;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.docum.domain.Stats;
import com.docum.domain.po.common.ActualCargoCondition;
import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoCalibreDefect;
import com.docum.domain.po.common.CargoPackage;
import com.docum.domain.po.common.CargoPackageCalibre;
import com.docum.test.data.AllDataPreparator;

public class CargoUtilTest {

	private AllDataPreparator preparator;

	@Before
	public void setUp() {
		preparator = new AllDataPreparator();
		preparator.prepareAllData();
	}

	@Test
	public void testCalcAverageDefects() {
		Article article = new Article("Помидоры", "Tomatoes");
		Cargo cargo = new Cargo(article, null, new ActualCargoCondition());
		CargoPackage cargoPackage = new CargoPackage(cargo, null, 21.0);
		cargo.addPackage(cargoPackage);
		List<ArticleCategory> categories = new ArrayList<ArticleCategory>();
		categories.add(new ArticleCategory(article, "1", "1"));
		categories.add(new ArticleCategory(article, "2", "2"));
		categories.add(new ArticleCategory(article, "3", "3"));
		categories.add(new ArticleCategory(article, "4", "4"));
		article.setCategories(categories);
		cargo.setArticleCategory(categories.get(0));
		
		cargoPackage.addCalibre(createCalibre(cargoPackage, categories,
				"45", 10.0, new double[]{73.3, 15.9, 5.4, 5.4}));
		cargoPackage.addCalibre(createCalibre(cargoPackage, categories,
				"54", 6.0, new double[]{90.0, 7.8, 1.1, 1.1}));
		cargoPackage.addCalibre(createCalibre(cargoPackage, categories,
				"60", 5.0, new double[]{83.1, 9.7, 4.7, 2.5}));

		Stats.CargoDefects defects = CargoUtil.calcAverageDefects(cargo);
		Assert.assertEquals(defects.getCalibreDefects().length, 3);
	}

	private CargoPackageCalibre createCalibre(CargoPackage cargoPackage,
			List<ArticleCategory> categories, String name, double count, double[] percentages) {
		CargoPackageCalibre calibre;
		calibre = new CargoPackageCalibre(cargoPackage, name, count);
		int i = 0;
		for(ArticleCategory category : categories) {
			calibre.getCalibreDefects().add(createDefect(category, calibre, percentages[i++]));
		}
		return calibre;
	}

	private CargoCalibreDefect createDefect(ArticleCategory category, CargoPackageCalibre calibre,
			double percentage) {
		CargoCalibreDefect defect;
		defect = new CargoCalibreDefect(category, calibre);
		defect.setPercentage(percentage);
		return defect;
	}

}
