package com.docum.dao;

import java.util.List;

import com.docum.domain.po.common.ArticleCategory;

public interface ArticleDao extends BaseDao {

	public static final String GET_ARTICLE_CATEGORIES_BY_ARTICLE_QUERY = "getArticleCategoryByArticle";

	public List<ArticleCategory> getArticleCategoryByArticle(Long articleId);

}