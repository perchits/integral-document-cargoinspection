package com.docum.view.dict;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.ArticleFeature;

@Controller("articleBean")
@Scope("session")
public class ArticleView extends BaseView {
	private static final long serialVersionUID = -3958815651039578018L;
	private static final String sign = "Товар";

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
		return null;
	}
	
	public List<ArticleFeature> getFeatures() {
		return null;
	}

}
