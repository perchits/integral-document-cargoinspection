package com.docum.service;

import java.util.List;

import com.docum.domain.po.common.ArticleCategory;

public interface ArticleService extends BaseService {
	public static final String SERVICE_NAME = "articleService";
	
	public List<ArticleCategory> getArticleCategoryByArticle(Long articleId);
}
