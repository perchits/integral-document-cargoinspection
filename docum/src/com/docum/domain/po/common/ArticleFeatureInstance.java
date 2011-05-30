package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class ArticleFeatureInstance extends IdentifiedEntity {
	private static final long serialVersionUID = 6553357822861656964L;

	@ManyToOne(optional=false)
	private ArticleFeature articleFeature;
	
	private String name;
	
	private String englishName;
	
	public ArticleFeatureInstance() {
		super();
	}

	public ArticleFeatureInstance(ArticleFeature articleFeature, String name, String englishName) {
		this.articleFeature = articleFeature;
		this.name = name;
		this.englishName = englishName;
	}

	public ArticleFeatureInstance(ArticleFeatureInstance other) {
		copy(other);
	}

	public void copy(ArticleFeatureInstance other) {
		this.articleFeature = other.articleFeature;
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

	public ArticleFeature getArticleFeature() {
		return articleFeature;
	}

	public void setArticleFeature(ArticleFeature articleFeature) {
		this.articleFeature = articleFeature;
	}
}
