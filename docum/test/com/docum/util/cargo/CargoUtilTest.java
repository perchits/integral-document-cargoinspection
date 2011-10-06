package com.docum.util.cargo;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.docum.domain.Stats;
import com.docum.domain.Stats.CargoCalibreDefects;
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
		
		String[] names = new String[]{"45", "54", "60"};
		double[] counts = new double[]{10.0, 6.0, 5.0};
		double[][] percentages = new double[][]{{15.9, 5.4, 5.4}, {7.8, 1.1, 1.1}, {9.7, 4.7, 2.5}};

		Cargo cargo = createCargo(names, counts, percentages);

		Stats.CargoDefects defects = CargoUtil.calcAverageDefects(cargo);
		Assert.assertEquals(defects.getCalibreDefects().length, 3);
	}

	@Test
	public void testCalcAverageDefectsList() {
		
		String[] names1 = new String[]{"45", "54", "60"};
		double[] counts1 = new double[]{10.0, 6.0, 5.0};
		double[][] percentages1 = new double[][]{{15.9, 5.4, 5.4}, {7.8, 1.1, 1.1}, {9.7, 4.7, 2.5}};

		String[] names2 = new String[]{"45", "54", "60"};
		double[] counts2 = new double[]{6.0, 9.0, 3.0};
		double[][] percentages2 = new double[][]{{12.3, 3.5, 2.2}, {4.6, 2.7, 0.4}, {5.8, 3.9, 3.1}};
		
		Cargo cargo1 = createCargo(names1, counts1, percentages1);

		Cargo cargo2 = createCargo(names2, counts2, percentages2);
		List<Cargo> cargoes = new ArrayList<Cargo>();
		cargoes.add(cargo1);
		cargoes.add(cargo2);
		Stats.CargoDefects defects = CargoUtil.calcAverageDefects(cargoes);
		CargoCalibreDefects averageDefects = defects.getAverageCalibreDefects();
		Assert.assertEquals(Math.round(averageDefects.getPercentages()[0]*100), 8421);
		Assert.assertEquals(Math.round(averageDefects.getPercentages()[1]*100), 974);
		Assert.assertEquals(Math.round(averageDefects.getPercentages()[2]*100), 359);
		Assert.assertEquals(Math.round(averageDefects.getPercentages()[3]*100), 247);
	}
	
	private Cargo createCargo(String[] names, double[] counts, double[][] percentages) {
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
		
		for(int i=0; i<names.length; i++) {
			cargoPackage.addCalibre(createCalibre(cargoPackage, categories,
					names[i], counts[i], percentages[i]));
		}
		return cargo;
	}

	private CargoPackageCalibre createCalibre(CargoPackage cargoPackage,
			List<ArticleCategory> categories, String name, double count, double[] percentages) {
		CargoPackageCalibre calibre;
		calibre = new CargoPackageCalibre(cargoPackage, name, count);
		int i = 0;
		boolean firstCategory = true;
		for(ArticleCategory category : categories) {
			if(firstCategory) {
				firstCategory = false;
				continue;
			}
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
