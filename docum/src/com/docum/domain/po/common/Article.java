package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class Article extends IdentifiedEntity {
	private static final long serialVersionUID = -6988218269163708874L;

	private String name;
	private String englishName;
	@OneToMany(mappedBy = "article")
	private List<ArticleCategory> categories = new ArrayList<ArticleCategory>();
	@OneToMany(mappedBy = "article")
	private List<ArticleFeature> features = new ArrayList<ArticleFeature>();


	public Article() {
		super();
	}

	public Article(String name, String englishName) {
		this.name = name;
		this.englishName = englishName;
	}

	public Article(Article article) {
		copy(article);
	}

	public void copy(Article article) {
		this.name = article.name;
		this.englishName = article.englishName;
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

	public List<ArticleCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<ArticleCategory> categories) {
		this.categories = categories;
	}

	public List<ArticleFeature> getFeatures() {
		return features;
	}

	public void setFeatures(List<ArticleFeature> features) {
		this.features = features;
	}
}
