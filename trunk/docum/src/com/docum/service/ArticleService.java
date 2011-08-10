package com.docum.service;

import java.util.List;

import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.ArticleFeature;
import com.docum.domain.po.common.ArticleFeatureInstance;
import com.docum.domain.po.common.NormativeDocument;

public interface ArticleService extends BaseService {
	public static final String SERVICE_NAME = "articleService";	
	public List<ArticleCategory> getArticleCategoryByArticle(Long articleId);
	public List<ArticleFeature> getArticleFeatureByArticle(Long articleId);
	public List<ArticleFeatureInstance> getArticleFeatureInstanceByArticle(Long articleFeatureId);
	public List<NormativeDocument> getNormativeDocumentByArticle(Long articleId);
}	
