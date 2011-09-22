package com.docum.test.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.ArticleInspectionOption;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoArticleFeature;
import com.docum.domain.po.common.CargoCalibreDefect;
import com.docum.domain.po.common.CargoCondition;
import com.docum.domain.po.common.CargoInspectionInfo;
import com.docum.domain.po.common.CargoInspectionOption;
import com.docum.domain.po.common.CargoPackage;
import com.docum.domain.po.common.CargoPackageCalibre;
import com.docum.domain.po.common.CargoPackageWeight;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Measure;
import com.docum.domain.po.common.Supplier;

public class CargoDataPreparator extends AbstractDataPreparator {

	public static final Double[] cargoCounts = new Double[] { 12300.0, 22300.0,
			24200.0, 20520.0, 19800.0, 25000.0, 23300.0, 21630.0, 26300.0,
			8700.0 };

	public static final String[] calibreNames = new String[] { "30/40", "20/40",
		"34mm", "18-32", "25/35"};
	
	public static final Double[] percents = new Double[] { 0.2, 2.3, 1.5, 2.7,
		0.9, 3.4, 1.2, 3.3, 12.8, 7.2 };

	public static final Double[] weights = new Double[] { 1.4, 12.2, 15.1, 12.7,
		0.7, 23.2, 1.6, 12.9, 0.2, 8.3 };
	
	private TestDataEntityCounter<Article> articleCounter;
	private TestDataEntityCounter<Supplier> supplierCounter;
	private TestDataEntityCounter<Container> containerCounter;
	private TestDataEntityCounter<Measure> measureCounter;
	private TestDataEntityCounter<Double> countCounter =
		new TestDataEntityCounter<Double>(cargoCounts);
	private TestDataEntityCounter<String> calibreCounter =
		new TestDataEntityCounter<String>(calibreNames);
	private TestDataEntityCounter<Double> percentsCounter =
		new TestDataEntityCounter<Double>(percents);
	private TestDataEntityCounter<Double> weightsCounter =
		new TestDataEntityCounter<Double>(weights);


	private int categoryCounter = 0;

	public CargoDataPreparator() {
		super();
	}

	public CargoDataPreparator(TestDataPersister persister) {
		super(persister);
	}


	public List<Cargo> prepareCargoes(List<Article> articles, List<Supplier> suppliers,
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
			cargo.setCargoPackages(new HashSet<CargoPackage>(preparePackages(cargo)));
			prepareFeatures(cargo);
			cargo.setArticleCategory(prepareCategory(cargo));
			result.add(cargo);
			
			if(container.getInspection() != null) {
				Cargo actualCargo = new Cargo(cargo.getArticle(), cargo.getSupplier(),
						container.getActualCondition());
				container.getActualCondition().addCargo(actualCargo);
				actualCargo.setCargoPackages(new HashSet<CargoPackage>(
						preparePackages(actualCargo)));
				prepareFeatures(actualCargo);
				actualCargo.setArticleCategory(prepareCategory(actualCargo));
				prepareCargoInspection(actualCargo);
				result.add(actualCargo);
			}
		}
		persister.persist(result);
		return result;
	}

	private void prepareCargoInspection(Cargo cargo) {
		CargoInspectionInfo info = new CargoInspectionInfo(cargo);
		
		List<ArticleInspectionOption> articleInspectionOptions =
			cargo.getArticle().getInspectionOptions();
		List<ArticleInspectionOption> leaves = new ArrayList<ArticleInspectionOption>();
		for(ArticleInspectionOption aio : articleInspectionOptions) {
			getArticleInspectionOptionLeaves(aio, leaves);
		}
		Set<CargoInspectionOption> options = new HashSet<CargoInspectionOption>();
		for(ArticleInspectionOption leaf : leaves) {
			CargoInspectionOption cio = new CargoInspectionOption(info, leaf);
			cio.setValue(1.1);
			options.add(cio);
		}
		info.setInspectionOptions(options);
		info.setCalibreDefects(prepareCalibreDefects(info));
		persister.persist(info);
	}
	
	private void getArticleInspectionOptionLeaves(
			ArticleInspectionOption parent, List<ArticleInspectionOption> leaves) {
		List<ArticleInspectionOption> children = parent.getChildren();
		if(children.isEmpty()) {
			leaves.add(parent);
			return;
		} else {
			for(ArticleInspectionOption child : children) {
				getArticleInspectionOptionLeaves(child, leaves);
			}
		}
	}

	private ArticleCategory prepareCategory(Cargo cargo) {
		Article article = cargo.getArticle();
		if(categoryCounter >= article.getCategories().size())
			categoryCounter = 0;		
		return new ArrayList<ArticleCategory>(article.getCategories()).get(categoryCounter);
	}

	private void prepareFeatures(Cargo cargo) {
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

	
	private List<CargoPackage> preparePackages(Cargo cargo) {
		List<CargoPackage> packages = new ArrayList<CargoPackage>();
		for (int i = 0; i<3; i++) {
			CargoPackage pkg = new CargoPackage(cargo, measureCounter.next(),
					countCounter.next());
			packages.add(pkg);
			if(i==0) {
				pkg.setCalibres(new HashSet<CargoPackageCalibre>(prepareCalibres(pkg)));
			}
			if(cargo.getCondition().isSurveyable()) {
				prepareWeights(pkg);
			}
		}
		return packages;
	}

	private void prepareWeights(CargoPackage pkg) {
		for(int i = 0; i<10; i++) {
			CargoPackageWeight weight = new CargoPackageWeight(pkg, weightsCounter.next(),
					weightsCounter.next()) ;
			pkg.getWeights().add(weight);
		}
	}

	private List<CargoPackageCalibre> prepareCalibres(CargoPackage pkg) {
		List<CargoPackageCalibre> calibres = new ArrayList<CargoPackageCalibre>();
		for (int i = 0; i<2; i++) {
			CargoPackageCalibre calibre = new CargoPackageCalibre(pkg, calibreCounter.next(),
					pkg.getCount()/3*(i+1));
			calibres.add(calibre);
		}
		return calibres;
	}
	
	private Set<CargoCalibreDefect> prepareCalibreDefects(CargoInspectionInfo info) {
		Cargo cargo = info.getCargo();
		Set<CargoCalibreDefect> result = new HashSet<CargoCalibreDefect>();
		for(CargoPackage cp : cargo.getCargoPackages()) {
			for(CargoPackageCalibre calibre : cp.getCalibres()) {
				for(ArticleCategory cat : cargo.getArticle().getCategories()) {
					CargoCalibreDefect defect = new CargoCalibreDefect(info, cat, calibre);
					defect.setPercentage(percentsCounter.next());
					result.add(defect);
				}
			}
		}
		return result;
	}
}
