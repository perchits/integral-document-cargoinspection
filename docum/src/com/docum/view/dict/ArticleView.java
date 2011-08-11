package com.docum.view.dict;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.NamedEntity;
import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.ArticleDefect;
import com.docum.domain.po.common.ArticleFeature;
import com.docum.domain.po.common.ArticleFeatureInstance;
import com.docum.domain.po.common.NormativeDocument;
import com.docum.service.ArticleService;
import com.docum.util.AlgoUtil;
import com.docum.util.DocumLogger;
import com.docum.view.wrapper.ArticleCategoryPresentation;
import com.docum.view.wrapper.ArticleCategoryTransformer;
import com.docum.view.wrapper.ArticleFeaturePresentation;
import com.docum.view.wrapper.ArticlePresentation;
import com.docum.view.wrapper.ArticleTransformer;
import com.docum.view.wrapper.NormativeDocumentPresentation;

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
	private ArticleDefect defect;
	private NormativeDocument document;
	private boolean enableOrdChange = false;

	public Article getArticle() {
		return article;
	}	
	
	public ArticlePresentation getWrappedArticle(){
		return new ArticlePresentation(article);
	}
	
	public void setWrappedArticle(ArticlePresentation article){		
		this.article = article != null ? article.getArticle() : null;				
	}
	
	public List<ArticleCategoryPresentation> getCategories() {
		Collection<ArticleCategory> categories = getWrappedArticle().getCategories();
		List<ArticleCategoryPresentation> result = null;
		if (categories != null) {
			result = new ArrayList<ArticleCategoryPresentation>(categories.size());
			AlgoUtil.transform(result, categories, new ArticleCategoryTransformer());
		}
		return result;
	}
	
	public List<ArticleFeaturePresentation> getFeatures() {
		return getWrappedArticle().getFeatures();
	}
	
	public List<NormativeDocumentPresentation> getDocuments() {
		return getWrappedArticle().getDocuments();
	}

	public List<ArticleFeatureInstance> getInstances() {
		return new ArticleFeaturePresentation(feature).getInstances();
	}
	
	public List<ArticleDefect> getDefects() {
		return new ArticleCategoryPresentation(this.category).getDefects();
	}

	public List<ArticlePresentation> getArticles() {
		if (articles == null) {
			refreshObjects();
		}
		return articles;
	}
	
	@Override
	public void refreshObjects() {
		Collection<Article> c = articleService.getAll(Article.class, null);
		articles = new ArrayList<ArticlePresentation>(c.size());
		AlgoUtil.transform(articles, c, new ArticleTransformer());		
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
	public String getBriefInfo() {
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
	
	public void saveDocument() {
		if (document.getId() == null) {
			article.addDocument(document);
		}
		article = getBaseService().save(article);
	}

	public void saveFeatureInstance() {
		if (featureInstance.getId() == null) {
			feature.addInstance(featureInstance);
		}
	}
	
	public void saveDefect() {
		this.category.addDefect(this.defect);
	}

	public void deleteCategory() {
		article.removeCategory(category);
		article = getBaseService().save(article);
	}
		
	public void beforeDeleteCategory(ActionEvent actionEvent) {
		validateAction(this.category, ArticleCategory.class);
	}
	
	public void deleteDocument() {
		if (validateAction(this.document, NormativeDocument.class)) {
			article.removeDocument(document);
			article = getBaseService().save(article);
		}
	}
	
	public void beforeDeleteDocument() {
		validateAction(this.document, NormativeDocument.class);
	}
	
	public void beforeDeleteFeature() {
		validateAction(this.feature, ArticleFeature.class);
	}
	
	public void beforeDeleteFeatureInstance() {
		validateAction(this.featureInstance, ArticleFeatureInstance.class);
	}
	
	public void beforeDeleteDefect() {
		validateAction(this.defect, ArticleDefect.class);
	}

	public void deleteFeature() {
		if (validateAction(this.feature, ArticleFeature.class)) {
			article.removeFeature(feature);
			article = getBaseService().save(article);
		}
	}

	public void deleteFeatureInstance() {
		if (validateAction(this.featureInstance, ArticleFeatureInstance.class)) {
			feature.removeInstance(featureInstance);
			feature = getBaseService().save(feature);
		}
	}
	
	public void deleteDefect() {
		if (validateAction(this.defect, ArticleDefect.class)) {
			this.category.removeDefect(this.defect);
			this.category = getBaseService().save(this.category);
		}
	}

	public void newCategory() {
		if (validateAction(this.article, Article.class)) {
			setTitle("Новая категория");
			category = new ArticleCategory();
		} 
	}
	
	public void newDocument() {
		if (validateAction(this.article, Article.class)) {
			setTitle("Новый документ");
			this.document = new NormativeDocument();
		}
	}

	public void newFeature() {
		if (validateAction(this.article, Article.class)) {
			setTitle("Новая характеристика");
			this.feature = new ArticleFeature();
		}
	}

	public void newFeatureInstance() {
		setTitle("Новая характеристика");
		this.featureInstance = new ArticleFeatureInstance();
	}
	
	public void newDefect() {
		setTitle("Новый дефект");
		this.defect = new ArticleDefect();
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
	
	public NormativeDocument getDocument() {
		return document;
	}
	
	public void setDocument(NormativeDocument document) {
		this.document = document;
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
	
	public void setWrappedCategory(ArticleCategoryPresentation category) {		
		this.category = category !=  null ? category.getArticleCategory() : null;
	}
	
	public void setWrappedDocument(NormativeDocumentPresentation normativeDocument) {
		this.document = normativeDocument != null ? normativeDocument.getDocument() :null;
	}
	
	public ArticleFeaturePresentation getWrappedFeature() {
		return new ArticleFeaturePresentation(feature);
	}
	
	public ArticleCategoryPresentation getWrappedCategory() {
		return new ArticleCategoryPresentation(category);
	}
	
	public NormativeDocumentPresentation getWrappedDocument() {
		return new NormativeDocumentPresentation(document);
	}
	
	public void editCategory(ActionEvent actionEvent) {
		if (validateAction(this.category, ArticleCategory.class)) {
			setTitle("Правка: " + this.category.getName());
		}
	}
	
	public void editDocument(ActionEvent actionEvent) {
		if (validateAction(this.document, NormativeDocument.class)) {
			setTitle("Правка: " + this.document.getName());
		}
	}
	
	public void editFeature(ActionEvent actionEvent) {
		if (validateAction(this.feature, ArticleFeature.class)) {
			setTitle("Правка: " + this.feature.getName());
		}
	}
	
	public void editFeatureInstance() {
		if (validateAction(this.featureInstance, ArticleFeatureInstance.class)) {
			setTitle("Правка: " + this.featureInstance.getName());
		}
	}
	
	public void editDefect() {
		if (validateAction(this.defect, ArticleDefect.class)) {
			setTitle("Правка: " + this.defect.getName());
		}
	}
	
	private <T extends IdentifiedEntity> boolean validateAction(T identifiedEntity, Class<T> clazz) {
		try {
			if (identifiedEntity != null && identifiedEntity.getId() != null) {
				return true;
			} else {
				T t = clazz.newInstance();
				String message = t.getEntityName() + " для редактирования не выбран";
				if (t.getEntityGender().equals(NamedEntity.GenderEnum.FEMALE)) {
					message += "а";
				}
				showErrorMessage(message);
				addCallbackParam("dontShow", true);
				return false;
			}
		} catch (Exception e) {
			DocumLogger.log(e);
			return false;
		}
	}
	
	public boolean getEnableOrdChanged() {
		return this.enableOrdChange;
	}
	
	public void setOrdUp(ArticleCategoryPresentation categoryPresentation) {
		this.article.moveCategoryUp(categoryPresentation.getArticleCategory());
		super.getBaseService().save(this.article);
	}
	
	public void setOrdDown(ArticleCategoryPresentation categoryPresentation) {
		this.article.moveCategoryDown(categoryPresentation.getArticleCategory());
		super.getBaseService().save(this.article);
	}

	public ArticleDefect getDefect() {
		return defect;
	}

	public void setDefect(ArticleDefect defect) {
		this.defect = defect;
	}

	public boolean isEnableOrdChange() {
		return enableOrdChange;
	}

	public void setEnableOrdChange(boolean enableOrdChange) {
		this.enableOrdChange = enableOrdChange;
	}
	
	public int getMaxCategoryOrd() {
		return this.article.getCategories().size() - 1;
	}
}
