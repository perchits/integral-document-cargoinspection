package com.docum.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.dao.ArticleDao;
import com.docum.persistence.common.Article;
import com.docum.service.ArticleService;

@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService, Serializable {
	private static final long serialVersionUID = -5060019166629755694L;
	@Autowired
	private ArticleDao articleDao; 
	
	@Override
	public Article saveArticle(Article article) {
		return articleDao.saveObject(article);
	}

	@Override
	public Article getArticle(Long articleId) {
		return articleDao.getObject(Article.class, articleId);
	}

	@Override
	public List<Article> getAllArticles() {
		return articleDao.getAll(Article.class, null);
	}

	@Override
	public void deleteArticle(Article article) {
		articleDao.deleteObject(article);

	}

}
