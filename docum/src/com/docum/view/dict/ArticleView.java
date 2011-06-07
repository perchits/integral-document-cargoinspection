package com.docum.view.dict;

import java.util.ArrayList;
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
	private List<ArticleFeatureInstance> featureInstances =new ArrayList<ArticleFeatureInstance>();
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
			return articleService.getArticleCategoryByArticle(this.article.getId());
		}
	}
	
	public List<ArticleFeature> getFeatures() {
		if (this.article == null || this.article.getId() == null) {
			return null;
		} else {
			return articleService.getArticleFeatureByArticle(this.article.getId());
		}
	}
	
	public void saveCategory() {
		if (this.category != null && this.category.getId() != null) {
			super.getBaseService().updateObject(this.category);
		} else {
			this.category.setArticle(this.article);
			super.getBaseService().saveObject(this.category);
		}
		super.getBaseService().getAll(this.category.getClass(), DEAFULT_SORT_FIELDS);
	}
	
	public void saveFeature() {
		if (this.feature != null && this.feature.getId() != null) {
			super.getBaseService().updateObject(this.feature);
		} else {
			this.feature.setArticle(this.article);
			super.getBaseService().saveObject(this.feature);
		}
		super.getBaseService().getAll(this.feature.getClass(), DEAFULT_SORT_FIELDS);
	}
	
	public void saveFeatureInstance() {
		if (this.featureInstance != null && this.featureInstance.getId() != null) {
			super.getBaseService().updateObject(this.featureInstance);
		} else {
			this.featureInstance.setArticleFeature(this.feature);
			super.getBaseService().saveObject(this.featureInstance);
		}
		this.featureInstances = 
			articleService.getArticleFeatureInstanceByArticle(this.feature.getId()); 
	}
	
	public void deleteCategory() {
		super.getBaseService().deleteObject(this.category.getClass(), this.category.getId());
		refreshObjects();
	}
	
	public void refreshFeatureInstances() {
		this.featureInstances = 
			articleService.getArticleFeatureInstanceByArticle(this.feature.getId()); 
	}
	
	public void newCategory() {
		setTitle("Новая категория");
		this.category = new ArticleCategory();
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
			this.featureInstances = this.feature.getInstances();
		}
	}

	public List<ArticleFeatureInstance> getFeatureInstances() {
		return featureInstances;
	}

}
