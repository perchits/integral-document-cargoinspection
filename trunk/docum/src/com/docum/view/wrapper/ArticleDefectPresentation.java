package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.docum.domain.po.common.ArticleDefect;

public class ArticleDefectPresentation implements Serializable {
	private static final long serialVersionUID = 4890696906373040869L;
	
	private ArticleDefect articleDefect;
	
	
	public ArticleDefectPresentation(ArticleDefect articleDefect) {
		this.articleDefect = articleDefect;
	}
	
	public List<ArticleDefect> getDefects() {
		if (this.articleDefect == null) {
			return null;
		}
		List<ArticleDefect> result = new ArrayList<ArticleDefect>(
				this.articleDefect.getCategory().getDefects());
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
		return articleDefect != null ? articleDefect.getName() : null; 
	}
	
	public String getEnglishName() {		
		return articleDefect != null ? articleDefect.getEnglishName() : null; 
	}

	public ArticleDefect getArticleDefect() {
		return articleDefect;
	}

	public void setArticleDefect(ArticleDefect articleDefect) {
		this.articleDefect = articleDefect;
	}
	
}
