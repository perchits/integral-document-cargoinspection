package com.docum.view.handbook;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.docum.persistence.IdentifiedEntity;
import com.docum.persistence.common.Article;
import com.docum.service.ArticleService;
import com.docum.view.handbook.dialog.BaseDialog;

@ManagedBean(name = "articleBean")
@SessionScoped
public class ArticleView extends BaseDialog implements Serializable {
	private static final long serialVersionUID = -3958815651039578018L;
	private static final String sing = "Груз";
	@ManagedProperty(value = "#{articleService}")
	private ArticleService articleService;
	private List<Article> articles;
	private Article article = new Article();

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public List<Article> getArticles() {
		if (articles == null) {
			refreshArticles();
		}
		return articles;
	}

	public void refreshArticles() {
		articles = articleService.getAllArticles();
	}

	public void deleteArticle() {
		articleService.deleteArticle(articleService.getArticle(article.getId()));
		refreshArticles();
	}

	public void newArticle() {
		article = new Article();
		setTitle("Новый " + getSing());
	}

	public void saveArticleAction() {
		if (this.article.getId() != null) {
			Article article = articleService.getArticle(this.article
					.getId());
			article.copy(this.article);
			this.article = article;
		}
		this.article = articleService.saveArticle(article);
		refreshArticles();
	}
	
	@Override
	public String getSing() {
		return sing;
	}

	@Override
	public String getBase() {
		return article.getShortName();
	}

	@Override
	public IdentifiedEntity getBeanObject() {		
		return article;
	}

}

