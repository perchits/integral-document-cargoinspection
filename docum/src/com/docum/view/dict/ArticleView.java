package com.docum.view.dict;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.NamedEntity;
import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.ArticleDefect;
import com.docum.domain.po.common.ArticleFeature;
import com.docum.domain.po.common.ArticleFeatureInstance;
import com.docum.domain.po.common.ArticleInspectionOption;
import com.docum.domain.po.common.NormativeDocument;
import com.docum.exception.SQLException;
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
public class ArticleView extends BaseView implements Serializable {
	private static final long serialVersionUID = -3958815651039578018L;
	private static final String sign = "Товар";

	@Autowired
	ArticleService articleService;

	private String categoryTitle;
	private Article article = new Article();
	private List<ArticlePresentation> articles;
	private ArticleCategory category = new ArticleCategory();
	private ArticleCategory categoryCopy;
	private ArticleFeature feature = new ArticleFeature();
	private ArticleFeature featureCopy;

	private ArticleFeatureInstance featureInstance = new ArticleFeatureInstance();
	private ArticleDefect defect;
	private NormativeDocument document;
	private boolean enableOrdChange = false;
	private ArticleInspectionOption option, optionCopy, childOpt;

	private void saveArticle() {
		Article articleDao = null;
		try {
			articleDao = getBaseService().save(article);
		} catch (DataIntegrityViolationException e) {
			SQLException.integrityViolation(e);
		} catch (Exception e) {
			SQLException.commonException(e);
		} finally {
			refreshArticle(articleDao);
		}
	}

	private void refreshArticle(Article articleDao) {
		if (articleDao != null) {
			article = articleDao;
		} else {
			if (article != null && article.getId() != null) {
				article = getBaseService().getObject(article.getClass(), article.getId());
			}
		}

		ArticlePresentation ap = new ArticlePresentation(article);
		int index = articles.indexOf(ap);
		if (index != -1) {
			articles.remove(index);
			articles.add(index, ap);
		} else {
			articles.add(ap);
		}

	}

	public Article getArticle() {
		return article;
	}

	public ArticlePresentation getWrappedArticle() {
		return new ArticlePresentation(article);
	}

	public void setWrappedArticle(ArticlePresentation article) {
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
		return new ArticleFeaturePresentation(featureCopy).getInstances();
	}

	public List<ArticleDefect> getDefects() {
		return new ArticleCategoryPresentation(this.categoryCopy).getDefects();
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
		if (categoryCopy.getId() == null) {
			article.addCategory(categoryCopy);
		} else {
			category.deepCopy(categoryCopy);
		}
		saveArticle();
	}

	public void saveFeature() {
		if (featureCopy.getId() == null) {
			article.addFeature(featureCopy);
		} else {
			feature.deepCopy(featureCopy);
		}
		saveArticle();
	}

	public void saveDocument() {
		if (document.getId() == null) {
			article.addDocument(document);
		}
		saveArticle();
	}

	public void deleteCategory() {
		article.removeCategory(category);
		saveArticle();
	}

	public void beforeDeleteCategory(ActionEvent actionEvent) {
		validateAction(this.category, ArticleCategory.class);
	}

	public void deleteDocument() {
		if (validateAction(this.document, NormativeDocument.class)) {
			article.removeDocument(document);
			saveArticle();
		}
	}

	public void beforeDeleteDocument() {
		validateAction(this.document, NormativeDocument.class);
	}

	public void beforeDeleteFeature() {
		validateAction(this.feature, ArticleFeature.class);
	}

	public void deleteFeature() {
		if (validateAction(this.feature, ArticleFeature.class)) {
			article.removeFeature(feature);
			saveArticle();
		}
	}

	public void deleteFeatureInstance() {
		featureCopy.removeInstance(featureInstance);
	}

	public void deleteDefect() {
		categoryCopy.removeDefect(defect);
	}

	public void newCategory() {
		if (validateAction(this.article, Article.class)) {
			setTitle("Новая категория");
			categoryCopy = new ArticleCategory();
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
			this.featureCopy = new ArticleFeature();
		}
	}

	public void newFeatureInstance() {
		featureCopy.addInstance(new ArticleFeatureInstance());
	}

	public void newDefect() {
		categoryCopy.addDefect(new ArticleDefect());
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
		this.feature = feature;
	}

	public void setWrappedFeature(ArticleFeaturePresentation feature) {
		this.feature = feature != null ? feature.getArticleFeature() : null;
	}

	public void setWrappedCategory(ArticleCategoryPresentation category) {
		this.category = category != null ? category.getArticleCategory() : null;
	}

	public void setWrappedDocument(NormativeDocumentPresentation normativeDocument) {
		this.document = normativeDocument != null ? normativeDocument.getDocument() : null;
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
			categoryCopy = new ArticleCategory();
			categoryCopy.deepCopy(category);
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
			featureCopy = new ArticleFeature();
			featureCopy.deepCopy(feature);
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
				String message = t.getEntityName() + " для редактирования или удаления не выбран!";
				if (t.getEntityGender().equals(NamedEntity.GenderEnum.FEMALE)) {
					message += "а";
				}
				if (t.getEntityGender().equals(NamedEntity.GenderEnum.NEUTER)) {
					message += "о";
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

	public List<ArticleInspectionOption> getOptions() {
		return article != null ? article.getInspectionOptions() : null;
	}

	public ArticleInspectionOption getOption() {
		return option;
	}

	public void setOption(ArticleInspectionOption option) {
		this.option = option;
	}

	public void editOption() {
		if (validateAction(this.option, ArticleInspectionOption.class)) {
			setTitle("Правка: " + this.option.getName());
			optionCopy = new ArticleInspectionOption();
			optionCopy.deepCopy(option);
		}
	}

	public void addOption() {
		setTitle("Новое свойство");
		this.optionCopy = new ArticleInspectionOption();
	}

	public void saveOption() {
		if (optionCopy.getId() == null) {
			article.addInspectionOption(optionCopy);
		} else {
			option.deepCopy(optionCopy);
		}
		saveArticle();
	}

	public void beforeDeleteOption() {
		validateAction(this.option, ArticleInspectionOption.class);
	}

	public void removeOption() {
		article.getInspectionOptions().remove(option);
		saveArticle();
	}

	public void setOptionUp(ArticleInspectionOption option) {
		article.moveInspectionOptionUp(option);
		super.getBaseService().save(this.article);
	}

	public void setOptionDown(ArticleInspectionOption option) {
		article.moveInspectionOptionDown(option);
		super.getBaseService().save(this.article);
	}

	public List<ArticleInspectionOption> getChildOptions() {
		return optionCopy != null ? optionCopy.getChildren() : null;
	}

	public ArticleInspectionOption getChildOpt() {
		return childOpt;
	}

	public void setChildOpt(ArticleInspectionOption childOpt) {
		this.childOpt = childOpt;
	}

	public void removeChildOpt() {
		optionCopy.removeChild(childOpt);
	}

	public void addChildOpt() {
		optionCopy.addChild(new ArticleInspectionOption());
	}

	public void setChildOptUp(ArticleInspectionOption child) {
		optionCopy.moveChildUp(child);
	}

	public void setChildOptDown(ArticleInspectionOption child) {
		optionCopy.moveChildDown(child);
	}

	public ArticleFeature getFeatureCopy() {
		return featureCopy;
	}

	public void setFeatureCopy(ArticleFeature featureCopy) {
		this.featureCopy = featureCopy;
	}

	public ArticleCategory getCategoryCopy() {
		return categoryCopy;
	}

	public void setCategoryCopy(ArticleCategory categoryCopy) {
		this.categoryCopy = categoryCopy;
	}

	public ArticleInspectionOption getOptionCopy() {
		return optionCopy;
	}

	public void setOptionCopy(ArticleInspectionOption optionCopy) {
		this.optionCopy = optionCopy;
	}
}
