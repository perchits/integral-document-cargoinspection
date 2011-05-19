package com.docum.domain;

public enum ContainerStateEnum {
	BEFORE_CUSTOMS("Не растаможен"),
	AFTER_CUSTOMS("Растаможен"),
	HANDLED("Отработан"),
	REPORTED("Отчет сдан"),
	ABANDONED("Невозможно отработать");

	private final String name;
	
	private ContainerStateEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public String toString() {
		return name;
	}
}
