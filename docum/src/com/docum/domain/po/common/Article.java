package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class Article extends IdentifiedEntity {
	private static final long serialVersionUID = -6988218269163708874L;

	private String name;
	private String englishName;
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<ArticleCategory> categories = new HashSet<ArticleCategory>();
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<ArticleFeature> features = new HashSet<ArticleFeature>();


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
		return new ArrayList<ArticleCategory>(categories);
	}

	public void setCategories(List<ArticleCategory> categories) {
		this.categories.clear();
		this.categories.addAll(categories);		
	}
	
	public void addCategory(ArticleCategory category){
		categories.add(category);
		category.setArticle(this);
	}
	
	public void removeCategory(ArticleCategory category){
		categories.remove(category);
		category.setArticle(null);
	}

	public List<ArticleFeature> getFeatures() {
		return new ArrayList<ArticleFeature>(features);
	}

	public void setFeatures(List<ArticleFeature> features) {
		this.features.clear();
		this.features.addAll(features);
	}
	
	public void addFeature(ArticleFeature feature) {
		features.add(feature);
		feature.setArticle(this);
	}

	public void removeFeature(ArticleFeature feature) {
		features.remove(feature);
		feature.setArticle(null);
	}	
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Article)) {
			return false;
		}

		return EqualsHelper.equals(getId(), ((Article) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
}
