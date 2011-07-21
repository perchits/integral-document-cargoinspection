package com.docum.domain.po;

public abstract class NamedEntity {
	public static enum GenderEnum {
		MALE,
		FEMALE;
	}
	
	public String getEntityName() {return null;}
	public GenderEnum getEntityGender() {return null;}
}
