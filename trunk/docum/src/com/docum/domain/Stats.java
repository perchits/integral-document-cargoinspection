package com.docum.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Stats {

	/**
	 * CargoCalibreDefects - строка в таблице повреждений
	 */
	public static class CargoCalibreDefects implements Serializable {
		private static final long serialVersionUID = -1703079367878760638L;

		private String calibreName;
		private double packageCount;
		private double[] percentages;

		public CargoCalibreDefects() {
			super();
		}

		public CargoCalibreDefects(String calibreName, double packageCount) {
			super();
			this.calibreName = calibreName;
			this.packageCount = packageCount;
		}
		
		public String getCalibreName() {
			return calibreName;
		}
		public void setCalibreName(String calibre) {
			this.calibreName = calibre;
		}
		public double getPackageCount() {
			return packageCount;
		}
		public void setPackageCount(double packageCount) {
			this.packageCount = packageCount;
		}
		public double[] getPercentages() {
			return percentages;
		}
		public void setPercentages(double[] percentages) {
			this.percentages = percentages;
		}
	}
	
	/**
	 * CargoDefects - таблица повреждений
	 */
	public static class CargoDefects implements Serializable {
		private static final long serialVersionUID = -968234605437616504L;

		private String cargoName;
		private String cargoEnglishName;
		private String[] categoryNames;
		private String[] categoryEnglishNames;
		private CargoCalibreDefects[] calibreDefects;
		private CargoCalibreDefects averageCalibreDefects;

		public CargoDefects() {
			super();
		}

		public CargoDefects(String cargoName, String cargoEnglishName) {
			super();
			this.cargoName = cargoName;
			this.cargoEnglishName = cargoEnglishName;
		}
		
		public String getCargoName() {
			return cargoName;
		}
		public void setCargoName(String cargoName) {
			this.cargoName = cargoName;
		}
		public String getCargoEnglishName() {
			return cargoEnglishName;
		}
		public void setCargoEnglishName(String cargoEnglishName) {
			this.cargoEnglishName = cargoEnglishName;
		}
		public String[] getCategoryNames() {
			return categoryNames;
		}
		public void setCategoryNames(String[] categoryNames) {
			this.categoryNames = categoryNames;
		}
		public String[] getCategoryEnglishNames() {
			return categoryEnglishNames;
		}
		public void setCategoryEnglishNames(String[] categoryEnglishNames) {
			this.categoryEnglishNames = categoryEnglishNames;
		}
		public CargoCalibreDefects[] getCalibreDefects() {
			return calibreDefects;
		}
		public void setCalibreDefects(CargoCalibreDefects[] calibreDefects) {
			this.calibreDefects = calibreDefects;
		}
		public CargoCalibreDefects getAverageCalibreDefects() {
			return averageCalibreDefects;
		}
		public void setAverageCalibreDefects(CargoCalibreDefects averageCalibreDefects) {
			this.averageCalibreDefects = averageCalibreDefects;
		}
	}
	
	/**
	 * Описывает партию груза с уникальными характеристиками в рамках инвойса
	 */
	public static class CargoParty implements Serializable {
		private static final long serialVersionUID = 7915309761612127824L;

		private String articleName;
		private String articleEnglishName;
		private String categoryName;
		private String categoryEnglishName;
		private CargoDefects averageDefects;

		public CargoParty(String articleName, String articleEnglishName, String categoryName,
				String categoryEnglishName, CargoDefects averageDefects) {
			super();
			this.articleName = articleName;
			this.articleEnglishName = articleEnglishName;
			this.categoryName = categoryName;
			this.categoryEnglishName = categoryEnglishName;
			this.averageDefects = averageDefects;
		}
		public String getArticleName() {
			return articleName;
		}
		public String getArticleEnglishName() {
			return articleEnglishName;
		}
		public String getCategoryName() {
			return categoryName;
		}
		public String getCategoryEnglishName() {
			return categoryEnglishName;
		}
		public CargoDefects getAverageDefects() {
			return averageDefects;
		}
	}
	
	public static class CargoDefectsOld implements Serializable {
		private static final long serialVersionUID = -372166682283425892L;

		private List<CategoryDefectsOld> categoryDefectsOld = new ArrayList<Stats.CategoryDefectsOld>();

		public CargoDefectsOld() {
			super();
		}
		public List<CategoryDefectsOld> getCategoryDefects() {
			return categoryDefectsOld;
		}
		public void addCategoryDefects(CategoryDefectsOld defects) {
			categoryDefectsOld.add(defects);
		}
		//Главная категория - всегда первая
		public CategoryDefectsOld getMainCategory() {
			return categoryDefectsOld.isEmpty() ? null : categoryDefectsOld.iterator().next();
		}
	}
	
	/**
	 * Средние значения дефектов по категориям
	 */
	
	public static class CategoryDefectsOld implements Serializable {
		private static final long serialVersionUID = 382955697301254228L;

		private String categoryName;
		private String categoryEnglishName;
		private double percentage = 0.0;

		public CategoryDefectsOld(String categoryName, String categoryEnglishName) {
			super();
			this.categoryName = categoryName;
			this.categoryEnglishName = categoryEnglishName;
		}
		public String getCategoryName() {
			return categoryName;
		}
		public String getCategoryEnglishName() {
			return categoryEnglishName;
		}
		public double getPercentage() {
			return percentage;
		}
		public void setPercertage(double percentage) {
			this.percentage = percentage;
		}
	}

}
