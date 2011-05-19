package com.docum.view.handbook;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.docum.persistence.IdentifiedEntity;
import com.docum.persistence.common.Article;

@ManagedBean(name = "articleBean")
@SessionScoped
public class ArticleView extends BaseView implements Serializable {
	private static final long serialVersionUID = -3958815651039578018L;
	private static final String sing = "Товар";

	private Article article = new Article();

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
		return sing;
	}

	@Override
	public String getBase() {
		return article.getShortName();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return article != null ? this.article : new Article(); 
	}

}
