package com.docum.domain;

public enum ContainerStateEnum {
	NOT_HANDLED("Не отработан"),
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
