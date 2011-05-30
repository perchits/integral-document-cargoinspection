package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class ArticleCategory extends IdentifiedEntity {
	private static final long serialVersionUID = -3149818615410332097L;

	private String name;
	private String englishName;
	@ManyToOne
	private Article article;
	
	public ArticleCategory() {
		super();
	}

	public ArticleCategory(String name, String englishName) {
		this.name = name;
		this.englishName = englishName;
	}

	public ArticleCategory(ArticleCategory articleCategory) {
		copy(articleCategory);
	}

	public void copy(ArticleCategory articleCategory) {
		this.name = articleCategory.name;
		this.englishName = articleCategory.englishName;
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

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
}
