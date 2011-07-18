package com.docum.view.dict;

import java.util.ArrayList;
import java.util.Collection;
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
import com.docum.util.AlgoUtil;
import com.docum.view.wrapper.ArticleFeaturePresentation;
import com.docum.view.wrapper.ArticlePresentation;
import com.docum.view.wrapper.ArticleTransformer;

@Controller("articleBean")
@Scope("session")
public class ArticleView extends BaseView {
	private static final long serialVersionUID = -3958815651039578018L;
	private static final String sign = "Товар";

	@Autowired
	ArticleService articleService;

	private String categoryTitle;
	private Article article = new Article();
	private List<ArticlePresentation> articles;
	private ArticleCategory category = new ArticleCategory();
	private ArticleFeature feature = new ArticleFeature();
	private ArticleFeatureInstance featureInstance = new ArticleFeatureInstance();

	public Article getArticle() {
		return article;
	}

	public ArticlePresentation getWrappedArticle(){
		return new ArticlePresentation(article);
	}
	
	public void setWrappedArticle(ArticlePresentation article){		
		this.article = article != null ? article.getArticle() : null;				
	}
	
	public List<ArticleCategory> getCategories() {
		return getWrappedArticle().getCategories();
	}

	public List<ArticleFeaturePresentation> getFeatures() {
		return getWrappedArticle().getFeatures();
	}

	public List<ArticleFeatureInstance> getInstances() {
		return new ArticleFeaturePresentation(feature).getInstances();
	}

	public List<ArticlePresentation> getArticles() {
		Collection<Article> c = articleService.getAll(Article.class, null);
		articles = new ArrayList<ArticlePresentation>(c.size());
		AlgoUtil.transform(articles, c, new ArticleTransformer());
		return articles;
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

	public void saveCategory() {
		if (category.getId() == null) {
			article.addCategory(category);
		}
		article = getBaseService().save(article);
	}

	public void saveFeature() {
		if (feature.getId() == null) {
			article.addFeature(feature);
		}
		article = getBaseService().save(article);
	}

	public void saveFeatureInstance() {
		if (featureInstance.getId() == null) {
			feature.addInstance(featureInstance);
		}
		feature = getBaseService().save(feature);
		article = getBaseService().save(article);
	}

	public void deleteCategory() {
		article.removeCategory(category);
		article = getBaseService().save(article);
	}

	public void deleteFeature() {
		article.removeFeature(feature);
		article = getBaseService().save(article);
	}

	public void deleteFeatureInstance() {
		feature.removeInstance(featureInstance);
		feature = getBaseService().save(feature);
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
		this.feature =  feature;
	}

	public void setWrappedFeature(ArticleFeaturePresentation feature) {		
		this.feature = feature !=  null ? feature.getArticleFeature() : null;
	}
	
	public ArticleFeaturePresentation getWrappedFeature() {
		return new ArticleFeaturePresentation(feature);
	}

}
