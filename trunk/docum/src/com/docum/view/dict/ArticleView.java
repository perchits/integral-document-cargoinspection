package com.docum.view.dict;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.ArticleFeature;
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
			super.getBaseService().saveObject(this.category);
		}
		HashMap<String, SortOrderEnum> sortFields = new HashMap<String, SortOrderEnum>();
		sortFields.put("id", SortOrderEnum.ASC);
		super.getBaseService().getAll(this.category.getClass(), sortFields);
	}
	
	public void newCategory() {
		setTitle("Новая категория");
		this.category = new ArticleCategory();
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

}
