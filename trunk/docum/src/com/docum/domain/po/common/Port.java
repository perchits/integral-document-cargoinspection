package com.docum.domain.po.common;

import javax.persistence.Entity;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class Port extends IdentifiedEntity {
	private static final long serialVersionUID = -107225230839559627L;
	private String name;
	private String englishName;


	public Port() {
		super();
	}

	public Port(String name, String englishName) {
		this.name = name;
		this.englishName = englishName;
	}

	public Port(Port other) {
		copy(other);
	}

	public void copy(Port other) {
		this.name = other.name;
		this.englishName = other.englishName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

}
