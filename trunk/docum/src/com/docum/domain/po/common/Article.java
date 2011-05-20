package com.docum.domain.po.common;

import javax.persistence.Entity;

import com.docum.domain.po.IdentifiedEntity;

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

	public Article(Article article) {
		copy(article);
	}

	public void copy(Article article) {
		this.name = article.name;
		this.shortName = article.shortName;
		this.englishName = article.englishName;
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
