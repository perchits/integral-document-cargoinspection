package com.docum.domain.po;

public enum SecurityRoleEnum {
	SUPERUSER("Все права"),
	USER("Обычный пользователь");
	
	private String name;
	
	private SecurityRoleEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public String toString() {
		return name;
	}
}
