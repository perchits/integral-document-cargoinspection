package com.docum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docum.dao.ArticleDao;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.service.ArticleService;

@Service(ArticleService.SERVICE_NAME)
public class ArticleServiceImpl extends BaseServiceImpl implements ArticleService{
	private static final long serialVersionUID = -1335481843288234626L;
	
	@Autowired
	ArticleDao articleDao;

	@Override
	public List<ArticleCategory> getArticleCategoryByArticle(Long articleId) {
		return articleDao.getArticleCategoryByArticle(articleId);
	}

}
