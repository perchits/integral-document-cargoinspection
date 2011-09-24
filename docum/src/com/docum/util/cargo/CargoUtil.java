package com.docum.util.cargo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.docum.domain.Stats;
import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoCalibreDefect;
import com.docum.domain.po.common.CargoPackage;
import com.docum.domain.po.common.CargoPackageCalibre;
import com.docum.domain.po.common.CargoPackageWeight;

public class CargoUtil {
	public static AverageCargoPackageWeights calcAverageWeights(
			Collection<CargoPackageWeight> weights) {
		double grossWeight = 0.0;
		double tareWeight = 0.0;
		if(!weights.isEmpty()) {
			for(CargoPackageWeight weight : weights) {
				grossWeight += weight.getGrossWeight();
				tareWeight += weight.getTareWeight();
			}
			grossWeight /= weights.size();
			tareWeight /= weights.size();
		}
		return new AverageCargoPackageWeights(grossWeight, grossWeight-tareWeight, tareWeight);
	}

	public static Stats.CargoDefects calcAverageDefects(Cargo cargo) {
		Stats.CargoDefects result = createCargoDefects(cargo);
		List<Stats.CargoCalibreDefects> resultDefects = new ArrayList<Stats.CargoCalibreDefects>();
		for(CargoPackage cargoPackage : cargo.getCargoPackages()) {
			Collection<CargoPackageCalibre> calibres = cargoPackage.getCalibres();
			if(!calibres.isEmpty()) {
				List<ArticleCategory> cargoCategories = getCargoCategories(cargo);
				Stats.CargoCalibreDefects averageCalibreDefects = new Stats.CargoCalibreDefects();
				averageCalibreDefects.setPackageCount(cargoPackage.getCount());
				averageCalibreDefects.setPercentages(new double[cargoCategories.size()]);
				assignCategoryNames(result, cargoCategories);
				result.setAverageCalibreDefects(averageCalibreDefects);

				for(CargoPackageCalibre calibre : calibres) {
					Stats.CargoCalibreDefects calibreDefects = new Stats.CargoCalibreDefects();
					calibreDefects.setCalibreName(calibre.getCalibreValue());
					calibreDefects.setPackageCount(calibre.getPackageCount());
					calibreDefects.setPercentages(new double[cargoCategories.size()]);
					resultDefects.add(calibreDefects);
					
					Iterator<ArticleCategory> categoryIt = cargoCategories.iterator();
					Iterator<CargoCalibreDefect> defectIt = calibre.getCalibreDefects().iterator();
					int i = 0;
					while(categoryIt.hasNext() && defectIt.hasNext()) {
						ArticleCategory category = categoryIt.next();
						CargoCalibreDefect cargoCalibreDefect = defectIt.next();
						if(!category.equals(cargoCalibreDefect.getArticleCategory())) {
							cargoCalibreDefect = findCargoCalibreDefect(category, calibre.getCalibreDefects());
						}
						if(cargoCalibreDefect == null) {
							//TODO: error
							i++;
							continue;
						}
						calibreDefects.getPercentages()[i] = cargoCalibreDefect.getPercentage();
						
						//расчет среднего % как СУММА(КОЛ-ВО УП. В КАЛИБРЕ / ОБЩ КОЛ-ВО УП. * % В КАЛИБРЕ)
						double average = averageCalibreDefects.getPercentages()[i];
						average += cargoCalibreDefect.getCalibre().getPackageCount()
								/ cargoPackage.getCount() * cargoCalibreDefect.getPercentage();
						averageCalibreDefects.getPercentages()[i] = average;
						i++;
					}
				}
			}
		}
		result.setCalibreDefects(resultDefects.toArray(
				new Stats.CargoCalibreDefects[resultDefects.size()]));
		return result;
	}

	private static void assignCategoryNames(Stats.CargoDefects result,
			List<ArticleCategory> categories) {
		String[] names = new String[categories.size()];
		String[] englishNames = new String[categories.size()];
		int i=0;
		for(ArticleCategory category : categories) {
			names[i] = category.getName();
			englishNames[i] = category.getEnglishName();
			i++;
		}
		result.setCategoryNames(names);
		result.setCategoryEnglishNames(englishNames);
	}
	
	private static CargoCalibreDefect findCargoCalibreDefect(ArticleCategory category,
			Set<CargoCalibreDefect> calibreDefects) {
		for(CargoCalibreDefect defect : calibreDefects) {
			if(defect.getArticleCategory().equals(category)) {
				return defect;
			}
		}
		return null;
	}

	public static List<ArticleCategory> getCargoCategories(Cargo cargo) {
		List<ArticleCategory> result = new ArrayList<ArticleCategory>();
		boolean categoryFound = false;
		for(ArticleCategory category : cargo.getArticle().getCategories()) {
			if(!categoryFound) {
				if(category.equals(cargo.getArticleCategory())) {
					categoryFound = true;
				} else {
					continue;
				}
			}
			result.add(category);
		}
		return result;
	}
	
	private static Stats.CargoDefects createCargoDefects(Cargo cargo) {
		Stats.CargoDefects result = new Stats.CargoDefects();
		Article article = cargo.getArticle();
		ArticleCategory category = cargo.getArticleCategory();
		result.setCargoName(article.getName() + ", " + category.getName());
		result.setCargoEnglishName(article.getEnglishName() + ", " + category.getEnglishName());
		return result;
	}
}
