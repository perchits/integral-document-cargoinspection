package com.docum.test.data;

import java.util.ArrayList;
import java.util.List;

import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleFeature;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoArticleFeature;
import com.docum.domain.po.common.CargoCondition;
import com.docum.domain.po.common.CargoPackage;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Measure;
import com.docum.domain.po.common.Supplier;

public class CargoDataPreparator {

	private static Double[] cargoCounts = new Double[] { 12300.0, 22300.0,
			24200.0, 20520.0, 19800.0, 25000.0, 23300.0, 21630.0, 26300.0,
			8700.0 };

	private static Double[] temperatures = new Double[] { 4.2, 3.0, 12.5, 6.1,
			8.9, 4.7, 11.0, 9.3, 6.8, 7.0 };

	private static TestDataEntityCounter<Article> articleCounter;
	private static TestDataEntityCounter<Supplier> supplierCounter;
	private static TestDataEntityCounter<Container> containerCounter;
	private static TestDataEntityCounter<Measure> measureCounter;
	private static TestDataEntityCounter<Double> countCounter =
		new TestDataEntityCounter<Double>(cargoCounts);
	private static TestDataEntityCounter<Double> temperatureCounter =
		new TestDataEntityCounter<Double>(temperatures);

	public static List<Cargo> prepareCargoes(TestDataPersister persister,
			List<Article> articles, List<Supplier> suppliers,
			List<Container> containers, List<Measure> measures) {
		
		articleCounter = new TestDataEntityCounter<Article>(articles);
		supplierCounter = new TestDataEntityCounter<Supplier>(suppliers);
		containerCounter = new TestDataEntityCounter<Container>(containers);
		measureCounter = new TestDataEntityCounter<Measure>(measures);
		
		List<Cargo> result = new ArrayList<Cargo>();
		for (int i = 0; i < 20; i++) {
			Cargo cargo = new Cargo(articleCounter.next(), supplierCounter.next(),
					containerCounter.next());
			persister.persist(cargo);
			cargo.setDeclaredCondition(prepareCondition(persister, cargo));
			cargo.setActualCondition(prepareCondition(persister, cargo));
			cargo.setFeatures(prepareFeatures(persister, cargo));
			result.add(cargo);
		}
		persister.persist(result);
		return result;
	}

	private static List<CargoArticleFeature> prepareFeatures(
			TestDataPersister persister, Cargo cargo) {
		List<CargoArticleFeature> result = new ArrayList<CargoArticleFeature>();
		CargoArticleFeature cargoArticleFeature;
		for(ArticleFeature feature : cargo.getArticle().getFeatures()) {
			if(feature.isList()) {
				cargoArticleFeature =
					new CargoArticleFeature(cargo, feature, feature.getInstances().get(0));
			} else {
				cargoArticleFeature =
					new CargoArticleFeature(cargo, feature, "2010", "2010");
			}
			result.add(cargoArticleFeature);
		}
		persister.persist(result);
		return result;
	}

	private static CargoCondition prepareCondition(TestDataPersister persister, Cargo cargo) {
		CargoCondition condition = new CargoCondition(cargo, temperatureCounter.next());
		persister.persist(condition);
		condition.setCargoPackages(preparePackages(persister, condition));
		return condition;
	}

	private static List<CargoPackage> preparePackages(TestDataPersister persister,
			CargoCondition condition) {
		List<CargoPackage> packages = new ArrayList<CargoPackage>();
		for (int i = 0; i<3; i++) {
			packages.add(new CargoPackage(condition, measureCounter.next(), countCounter.next()));
		}
		persister.persist(packages);
		return packages;
	}

}
