package com.docum.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Stats {

	/**
	 * Описывает партию груза с уникальными характеристиками в рамках инвойса
	 */
	public static class CargoParty implements Serializable {
		private static final long serialVersionUID = 7915309761612127824L;

		private String articleName;
		private String articleEnglishName;
		private String categoryName;
		private String categoryEnglishName;
		private CargoDefects defects;

		public CargoParty(String articleName, String articleEnglishName, String categoryName,
				String categoryEnglishName, CargoDefects defects) {
			super();
			this.articleName = articleName;
			this.articleEnglishName = articleEnglishName;
			this.categoryName = categoryName;
			this.categoryEnglishName = categoryEnglishName;
			this.defects = defects;
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
		public CargoDefects getDefects() {
			return defects;
		}
	}
	
	public static class CargoDefects implements Serializable {
		private static final long serialVersionUID = -372166682283425892L;

		private List<CategoryDefects> categoryDefects = new ArrayList<Stats.CategoryDefects>();

		public CargoDefects() {
			super();
		}
		public List<CategoryDefects> getCategoryDefects() {
			return categoryDefects;
		}
		public void addCategoryDefects(CategoryDefects defects) {
			categoryDefects.add(defects);
		}
		//Главная категория - всегда первая
		public CategoryDefects getMainCategory() {
			return categoryDefects.isEmpty() ? null : categoryDefects.iterator().next();
		}
	}
	
	/**
	 * Средние значения дефектов по категориям
	 */
	
	public static class CategoryDefects implements Serializable {
		private static final long serialVersionUID = 382955697301254228L;

		private String categoryName;
		private String categoryEnglishName;
		private double percentage = 0.0;

		public CategoryDefects(String categoryName, String categoryEnglishName) {
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
