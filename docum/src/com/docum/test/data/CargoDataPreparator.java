package com.docum.test.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.docum.domain.po.common.ActualCargoCondition;
import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoArticleFeature;
import com.docum.domain.po.common.CargoCondition;
import com.docum.domain.po.common.CargoPackage;
import com.docum.domain.po.common.CargoPackageCalibre;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.DeclaredCargoCondition;
import com.docum.domain.po.common.Measure;
import com.docum.domain.po.common.Supplier;

public class CargoDataPreparator {

	private static Double[] cargoCounts = new Double[] { 12300.0, 22300.0,
			24200.0, 20520.0, 19800.0, 25000.0, 23300.0, 21630.0, 26300.0,
			8700.0 };

	private static Double[] temperatures = new Double[] { 4.2, 3.0, 12.5, 6.1,
			8.9, 4.7, 11.0, 9.3, 6.8, 7.0 };

	private static String[] calibreNames = new String[] { "30/40", "20/40",
		"34mm", "18-32", "25/35"};
	
	private static TestDataEntityCounter<Article> articleCounter;
	private static TestDataEntityCounter<Supplier> supplierCounter;
	private static TestDataEntityCounter<Container> containerCounter;
	private static TestDataEntityCounter<Measure> measureCounter;
	private static TestDataEntityCounter<Double> countCounter =
		new TestDataEntityCounter<Double>(cargoCounts);
	private static TestDataEntityCounter<Double> temperatureCounter =
		new TestDataEntityCounter<Double>(temperatures);
	private static TestDataEntityCounter<String> calibreCounter =
		new TestDataEntityCounter<String>(calibreNames);

	private static int categoryCounter = 0;
	
	public static List<Cargo> prepareCargoes(TestDataPersister persister,
			List<Article> articles, List<Supplier> suppliers,
			List<Container> containers, List<Measure> measures) {
		
		articleCounter = new TestDataEntityCounter<Article>(articles);
		supplierCounter = new TestDataEntityCounter<Supplier>(suppliers);
		containerCounter = new TestDataEntityCounter<Container>(containers);
		measureCounter = new TestDataEntityCounter<Measure>(measures);
		
		List<Cargo> result = new ArrayList<Cargo>();
		for (int i = 0; i < 20; i++) {
			Container container = containerCounter.next();
			CargoCondition condition = container.getDeclaredCondition();
			Cargo cargo = new Cargo(articleCounter.next(), supplierCounter.next(), condition);
			cargo.setCargoPackages(new HashSet<CargoPackage>(preparePackages(persister, cargo)));
			prepareFeatures(persister, cargo);
			cargo.setArticleCategory(prepareCategory(persister, cargo));
			container.setDeclaredCondition(prepareDeclaredCondition(persister, container));
			container.setActualCondition(prepareActualCondition(persister, container));			
			result.add(cargo);
		}
		persister.persist(result);
		return result;
	}

	private static ArticleCategory prepareCategory(TestDataPersister persister,
			Cargo cargo) {
		Article article = cargo.getArticle();
		if(categoryCounter >= article.getCategories().size())
			categoryCounter = 0;		
		return new ArrayList<ArticleCategory>(article.getCategories()).get(categoryCounter);
	}

	private static void prepareFeatures(TestDataPersister persister, Cargo cargo) {
		for(CargoArticleFeature cargoArticleFeature : cargo.getFeatures()) {
			if(cargoArticleFeature.getFeature().isList()) {
				cargoArticleFeature.setFeatureInstance(cargoArticleFeature.getFeature().getInstances().iterator().next());
			} else {
				cargoArticleFeature.setValue("2010");
				cargoArticleFeature.setEnglishValue("2010");
			}
		}
		persister.persist(cargo);
	}

	private static DeclaredCargoCondition prepareDeclaredCondition(TestDataPersister persister, Container container) {
		DeclaredCargoCondition condition = container.getDeclaredCondition();
		condition.setMinTemperature(temperatureCounter.next());
		condition.setMaxTemperature(temperatureCounter.next());
		persister.persist(condition);
		return condition;
	}

	private static ActualCargoCondition prepareActualCondition(TestDataPersister persister, Container container) {
		ActualCargoCondition condition = container.getActualCondition();
		condition.setTemperature(temperatureCounter.next());
		return condition;
	}
	
	private static List<CargoPackage> preparePackages(TestDataPersister persister, Cargo cargo) {
		List<CargoPackage> packages = new ArrayList<CargoPackage>();
		for (int i = 0; i<3; i++) {
			CargoPackage pkg = new CargoPackage(cargo, measureCounter.next(),
					countCounter.next());
			packages.add(pkg);
			if(i==0) {
				pkg.setCalibres(new HashSet<CargoPackageCalibre>(prepareCalibres(persister, pkg)));
			}
		}
		return packages;
	}

	private static List<CargoPackageCalibre> prepareCalibres(
			TestDataPersister persister, CargoPackage pkg) {
		List<CargoPackageCalibre> calibres = new ArrayList<CargoPackageCalibre>();
		for (int i = 0; i<2; i++) {
			CargoPackageCalibre calibre = new CargoPackageCalibre(pkg, calibreCounter.next(),
					pkg.getCount()/3*(i+1));
			calibres.add(calibre);
		}
		return calibres;
	}
}
