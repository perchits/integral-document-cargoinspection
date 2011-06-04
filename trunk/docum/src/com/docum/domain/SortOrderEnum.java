package com.docum.domain;

public enum SortOrderEnum {
	DESC("DESC"), ASC("ASC");

	private final String name;

	private SortOrderEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return name;
	}
}
