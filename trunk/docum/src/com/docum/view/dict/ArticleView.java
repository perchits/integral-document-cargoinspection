package com.docum.view.dict;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.ArticleFeature;
import com.docum.domain.po.common.ArticleFeatureInstance;
import com.docum.service.ArticleService;

@Controller("articleBean")
@Scope("session")
public class ArticleView extends BaseView {
	private static final long serialVersionUID = -3958815651039578018L;
	private static final String sign = "Товар";

	@Autowired
	ArticleService articleService;

	private String categoryTitle;
	private Article article = new Article();
	private ArticleCategory category = new ArticleCategory();
	private ArticleFeature feature = new ArticleFeature();
	private List<ArticleFeature> features = new ArrayList<ArticleFeature>();
	private List<ArticleFeatureInstance> featureInstances = new ArrayList<ArticleFeatureInstance>();
	private ArticleFeatureInstance featureInstance = new ArticleFeatureInstance();

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	@Override
	public void newObject() {
		super.newObject();
		article = new Article();
	}

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return article.getName();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return article != null ? this.article : new Article();
	}

	public List<ArticleCategory> getCategories() {
		if (this.article == null || this.article.getId() == null) {
			return null;
		} else {
			List<ArticleCategory> result = new ArrayList<ArticleCategory>(article.getCategories());
			Collections.sort(result, new ArticleCategoryComparator());
			return result;
		}
	}

	public List<ArticleFeature> getFeatures() {
		if (this.article == null || this.article.getId() == null) {
			return null;
		} else {
			this.features = new ArrayList<ArticleFeature>(article.getFeatures());
			Collections.sort(this.features, new ArticleFeatureComparator());
			return this.features;
		}
	}

	private static class ArticleCategoryComparator implements Comparator<ArticleCategory> {
		@Override
		public int compare(ArticleCategory o1, ArticleCategory o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}

	private static class ArticleFeatureComparator implements Comparator<ArticleFeature> {
		@Override
		public int compare(ArticleFeature o1, ArticleFeature o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}

	public void saveCategory() {
		if (category.getId() == null) {
			article.addCategory(category);
		}
		article = getBaseService().save(article);
		category = null;
		feature = null;
	}

	public void saveFeature() {
		if (this.feature != null) {
			if (this.feature.getId() == null) {
				this.feature.setArticle(this.article);
			}
			super.getBaseService().save(this.feature);
		}
	}

	public void saveFeatureInstance() {
		if (this.featureInstance != null) {
			if (this.featureInstance.getId() == null) {
				this.featureInstance.setArticleFeature(this.feature);
			}
			super.getBaseService().save(this.featureInstance);
		}
		this.featureInstances = articleService
				.getArticleFeatureInstanceByArticle(this.feature.getId());
	}

	public void deleteCategory() {
		article.removeCategory(category);
		article = getBaseService().save(article);		
	}

	public void deleteFeature() {
		super.getBaseService().deleteObject(this.feature.getClass(),
				this.feature.getId());
	}

	public void deleteFeatureInstance() {
		super.getBaseService().deleteObject(this.featureInstance.getClass(),
				this.featureInstance.getId());
		this.featureInstances = articleService
				.getArticleFeatureInstanceByArticle(this.feature.getId());
		this.feature.setInstances(new HashSet<ArticleFeatureInstance>(
				this.featureInstances));
		super.getBaseService().save(this.feature);
	}

	public void refreshFeatureInstances() {
		this.featureInstances = articleService
				.getArticleFeatureInstanceByArticle(this.feature.getId());
	}

	public void newCategory() {
		setTitle("Новая категория");
		category = new ArticleCategory();
	}

	public void newFeature() {
		setTitle("Новая характеристика");
		this.feature = new ArticleFeature();
	}

	public void newFeatureInstance() {
		setTitle("Новая характеристика");
		this.featureInstance = new ArticleFeatureInstance();
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public ArticleCategory getCategory() {
		return category;
	}

	public void setCategory(ArticleCategory category) {
		this.category = category;
	}

	public ArticleFeatureInstance getFeatureInstance() {
		return featureInstance;
	}

	public void setFeatureInstance(ArticleFeatureInstance featureInstance) {
		this.featureInstance = featureInstance;
	}

	public ArticleFeature getFeature() {
		return feature;
	}

	public void setFeature(ArticleFeature feature) {
		this.feature = feature;
		if (this.feature != null) {
			this.featureInstances = new ArrayList<ArticleFeatureInstance>(
					this.feature.getInstances());
		}
	}

	public List<ArticleFeatureInstance> getFeatureInstances() {
		return featureInstances;
	}

}
