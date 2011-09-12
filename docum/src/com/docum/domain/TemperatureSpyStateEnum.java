package com.docum.domain;

public enum TemperatureSpyStateEnum {
	UNKNOWN("Нет данных"),
	NOT_FOUND("Не найден"),
	FOUND_OK("Найден без отклонений"),
	FOUND_DEVIATED("Найден с отклонениями"),
	FOUND_BROKEN("Найден в нерабочем состоянии");

	private final String name;
	
	private TemperatureSpyStateEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public String toString() {
		return name;
	}
}
