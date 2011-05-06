package com.docum.domain;

public enum ContainerStateEnum {
	BEFORE_CUSTOMS("Затаможен"),
	AFTER_CUSTOMS("Растаможен"),
	CHECKED("Проверен"),
	FINISHED("Обработан");

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
