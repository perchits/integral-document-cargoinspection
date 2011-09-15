package com.docum.domain;

import java.util.List;

public class Stats {

	/**
	 * Описывает партию груза с уникальными характеристиками в рамках инвойса
	 */
	public static class CargoParty {
		private String articleName;
		private String articleEnglishName;
		private String categoryName;
		private String categoryEnglishName;
		private CargoDefects defects;
	}
	
	public static class CargoDefects {
		private List<CategoryDefects> categoryDefects;
		private double wastePercentage; //Все, что не попало в категории, т.е. брак
	}
	
	/**
	 * Средние значения дефектов по категориям
	 */
	
	public static class CategoryDefects {
		private String categoryName;
		private String categoryEnglishName;
		private double percentage;
	}

}
