package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;
import com.docum.util.OrderedEntityUtil;

@Entity
public class Article extends IdentifiedEntity {
	private static final long serialVersionUID = -6988218269163708874L;

	private String name;
	private String englishName;
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderColumn(name="ord")
	private List<ArticleCategory> categories = new ArrayList<ArticleCategory>();
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
		return categories;
	}

	public void setCategories(List<ArticleCategory> categories) {		
		this.categories = categories;		
	}
	
	public void addCategory(ArticleCategory category){
		category.setArticle(this);
		OrderedEntityUtil.add(category, categories);
	}
	
	public void removeCategory(ArticleCategory category){
		if(OrderedEntityUtil.remove(category, categories)) {
			category.setArticle(null);
		}
	}

	public void setCategoryOrd(ArticleCategory category, int ord) {
		OrderedEntityUtil.setOrd(category, categories, ord);
	}

	public void moveCategoryUp(ArticleCategory category) {
		OrderedEntityUtil.moveUp(category, categories);
	}

	public void moveCategoryDown(ArticleCategory category) {
		OrderedEntityUtil.moveDown(category, categories);
	}
	
	public Set<ArticleFeature> getFeatures() {
		return features;
	}

	public void setFeatures(Set<ArticleFeature> features) {		
		this.features = features;
	}
	
	public void addFeature(ArticleFeature feature) {
		features.add(feature);
		feature.setArticle(this);
	}

	public void removeFeature(ArticleFeature feature) {
		if (features.remove(feature)) {
			feature.setArticle(null);
		}
		
	}	
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Article)) {
			return false;
		}
		
		if(getId() == null || ((Article) obj).getId() == null) {
			return false;
		}
		return EqualsHelper.equals(getId(), ((Article) obj).getId());
	}

	public int hashCode() {
		if(getId() == null) {
			return super.hashCode();
		}
		return HashCodeHelper.hashCode(getId());
	}
	
	@Override
	public String getEntityName() {
		return "Товар";
	}
	
	@Override
	public GenderEnum getEntityGender() {
		return GenderEnum.MALE;
	}
}
