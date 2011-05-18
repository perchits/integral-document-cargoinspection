package com.docum.service;

import java.util.List;

import com.docum.persistence.common.Article;

public interface ArticleService {
	
	public Article saveArticle(Article article);
	
	public Article getArticle(Long articleId);	
		
	public List<Article> getAllArticles();
	
	public void deleteArticle(Article article);	
	
}
