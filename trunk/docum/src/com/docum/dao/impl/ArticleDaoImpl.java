package com.docum.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.docum.dao.ArticleDao;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.ArticleFeature;
import com.docum.domain.po.common.ArticleFeatureInstance;

@Repository
public class ArticleDaoImpl extends BaseDaoImpl implements ArticleDao {
	private static final long serialVersionUID = 8659307383722024378L;

	@Override
	public List<ArticleCategory> getArticleCategoryByArticle(Long articleId) {
		Query query = entityManager.createNamedQuery(GET_ARTICLE_CATEGORIES_BY_ARTICLE_QUERY);
		query.setParameter("articleId", articleId);
		@SuppressWarnings("unchecked")
		List<ArticleCategory> result = query.getResultList();
		return result;
	}

	@Override
	public List<ArticleFeature> getArticleFeatureByArticle(Long articleId) {
		Query query = entityManager.createNamedQuery(GET_ARTICLE_FEATURES_BY_ARTICLE_QUERY);
		query.setParameter("articleId", articleId);
		@SuppressWarnings("unchecked")
		List<ArticleFeature> result = query.getResultList();
		return result;
	}

	@Override
	public List<ArticleFeatureInstance> getArticleFeatureInstanceByArticle(Long articleFeatureId) {
		Query query = 
			entityManager.createNamedQuery(GET_ARTICLE_FEATURE_INSTANCE_BY_ARTICLE_QUERY);
		query.setParameter("articleFeatureId", articleFeatureId);
		@SuppressWarnings("unchecked")
		List<ArticleFeatureInstance> result = query.getResultList();
		return result;
	}

}
