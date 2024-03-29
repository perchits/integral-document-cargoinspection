package com.docum.util.cargo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
			return new AverageCargoPackageWeights(grossWeight, grossWeight-tareWeight, tareWeight);
		} else {
			return null;
		}
	}

	public static List<List<Cargo>> collectByArticleAndCategory(Collection<Cargo> cargoes) {
		List<List<Cargo>> result = new ArrayList<List<Cargo>>();
//		for()
		return result;
	}

	/**
	 * Расчет средних значений повреждений по грузам.
	 * @param cargoes Список грузов.
	 * @return Рассчитанные средние значения повреждений.
	 * Поскольку грузы должны быть одного и того же товара с одинаковыми характеристиками и одной
	 * категории, поля наименований товаров и категорий берутся из первого груза.
	 */
	public static Stats.CargoDefects calcAverageDefects(Collection<Cargo> cargoes)
			throws IllegalArgumentException {
		if(cargoes.isEmpty()) {
			return null;
		}
		
		Stats.CargoDefects result = null;
		double[] averagePercentages = null;
		for(Cargo cargo : cargoes) {
			Stats.CargoDefects defects = calcAverageDefects(cargo);
			if(result == null) {
				result = new Stats.CargoDefects();
				result.setCargoName(defects.getCargoName());
				result.setCargoEnglishName(defects.getCargoEnglishName());
				result.setCategoryNames(defects.getCategoryNames());
				result.setCategoryEnglishNames(defects.getCategoryEnglishNames());
				result.setAverageCalibreDefects(new Stats.CargoCalibreDefects());
				averagePercentages = defects.getAverageCalibreDefects().getPercentages();
				result.getAverageCalibreDefects().setPercentages(averagePercentages);
			} else {
				double[] percentages = defects.getAverageCalibreDefects().getPercentages();
				if(!result.getCargoName().equals(defects.getCargoName()) ||
						result.getCategoryNames().length != defects.getCategoryNames().length ||
						averagePercentages.length != percentages.length) {
					throw new IllegalArgumentException("Inconsistent cargo list");
				}
				
				for(int i = 0; i < averagePercentages.length; i++) {
					averagePercentages[i] += percentages[i];
				}
			}
		}
		for(int i = 0; i < averagePercentages.length; i++) {
			averagePercentages[i] /= cargoes.size();
		}
		return result;
	}
	
	public static Stats.CargoDefects calcAverageDefects(Cargo cargo) {
		Stats.CargoDefects result = createCargoDefects(cargo);
		List<Stats.CargoCalibreDefects> resultDefects = new ArrayList<Stats.CargoCalibreDefects>();
		for(CargoPackage cargoPackage : cargo.getCargoPackages()) {
			Collection<CargoPackageCalibre> calibres = cargoPackage.getCalibres();
			if(!calibres.isEmpty()) {
				List<ArticleCategory> cargoCategories = getCargoCategoriesForDefects(cargo);
				int categoriesCount = cargoCategories.size();
				Stats.CargoCalibreDefects averageCalibreDefects = new Stats.CargoCalibreDefects();
				averageCalibreDefects.setPackageCount(calcPackageCount(calibres));
				double[] averagePercentages = new double[categoriesCount+1];
				averageCalibreDefects.setPercentages(averagePercentages);
				averagePercentages[0] = 100.0;
				assignCategoryNames(result, getCargoCategories(cargo));
				result.setAverageCalibreDefects(averageCalibreDefects);

				for(CargoPackageCalibre calibre : calibres) {
					Stats.CargoCalibreDefects calibreDefects = new Stats.CargoCalibreDefects();
					calibreDefects.setCalibreName(calibre.getCalibreValue());
					calibreDefects.setPackageCount(calibre.getPackageCount());
					double[] defectsPercentages = new double[categoriesCount+1];
					calibreDefects.setPercentages(defectsPercentages);
					defectsPercentages[0] = 100.0;
					resultDefects.add(calibreDefects);
					
					Iterator<ArticleCategory> categoryIt = cargoCategories.iterator();
					Iterator<CargoCalibreDefect> defectIt = calibre.getCalibreDefects().iterator();
					// начинаем индексацию дефектов с единицы (первый дефект - расчетный)
					int i = 1;
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
						defectsPercentages[0] -= cargoCalibreDefect.getPercentage();
						defectsPercentages[i] = cargoCalibreDefect.getPercentage();
						
						//расчет среднего % как СУММА(КОЛ-ВО УП. В КАЛИБРЕ / ОБЩ КОЛ-ВО УП. * % В КАЛИБРЕ)
						double average = averagePercentages[i];
						average += cargoCalibreDefect.getCalibre().getPackageCount()
								/ averageCalibreDefects.getPackageCount()
								* cargoCalibreDefect.getPercentage();
						averagePercentages[i] = average;
						i++;
					}
				}
				for(int i=1; i<averagePercentages.length; i++) {
					averagePercentages[0] -= averagePercentages[i];
				}
			}
		}
		result.setCalibreDefects(resultDefects.toArray(
				new Stats.CargoCalibreDefects[resultDefects.size()]));
		return result;
	}

	private static double calcPackageCount(Collection<CargoPackageCalibre> calibres) {
		double result = 0.0;
		for(CargoPackageCalibre calibre : calibres) {
			result += calibre.getPackageCount();
		}
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
			Collection<CargoCalibreDefect> calibreDefects) {
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

	public static List<ArticleCategory> getCargoCategoriesForDefects(Cargo cargo) {
		List<ArticleCategory> result = new ArrayList<ArticleCategory>();
		boolean firstCategory = true;
		for(ArticleCategory category : getCargoCategories(cargo)) {
			if(firstCategory) {
				firstCategory = false;
				continue;
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
