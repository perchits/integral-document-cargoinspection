package com.docum.view.container;

import java.io.Serializable;

import com.docum.service.ArticleService;
import com.docum.service.BaseService;

public class ContainerContext implements Serializable {
	private static final long serialVersionUID = -8618919955694844921L;

	private BaseService baseService;
	private ArticleService articleService;

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public BaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

}