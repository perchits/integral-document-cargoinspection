package com.docum.persistence.common;

import javax.persistence.Entity;

import com.docum.persistence.IdentifiedEntity;

@Entity
public class Article extends IdentifiedEntity {
	private static final long serialVersionUID = -6988218269163708874L;

	private String name;
	private String shortName;
	private String englishName;

	public Article() {
		super();
	}

	public Article(String name, String shortName, String englishName) {
		this.name = name;
		this.shortName = shortName;
		this.englishName = englishName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

}