package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.ArticleDefect;

public class ArticleCategoryPresentation implements Serializable {
	private static final long serialVersionUID = 3632984150844405817L;
	
	private ArticleCategory articleCategory;
	
	public ArticleCategoryPresentation(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}
	
	public List<ArticleDefect> getDefects() {
		if (articleCategory == null) {
			return null;
		}
		List<ArticleDefect> result = new ArrayList<ArticleDefect>(articleCategory.getDefects());
		Collections.sort(result, new Comparator<ArticleDefect>() {
			@Override
			public int compare(ArticleDefect o1, ArticleDefect o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return result;
	}
	
	public String toString() {
		return getName();
	}
	
	public String getName() {		
		return articleCategory != null ? articleCategory.getName() : null; 
	}
	
	public String getEnglishName() {		
		return articleCategory != null ? articleCategory.getEnglishName() : null; 
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}
	
}
