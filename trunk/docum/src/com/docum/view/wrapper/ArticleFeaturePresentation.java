package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.docum.domain.po.common.ArticleFeature;
import com.docum.domain.po.common.ArticleFeatureInstance;

public class ArticleFeaturePresentation implements Serializable {
	private static final long serialVersionUID = -2638220280461751971L;
	private ArticleFeature articleFeature;
	
	public void setArticleFeature(ArticleFeature articleFeature) {
		this.articleFeature = articleFeature;
	}
	
	public ArticleFeaturePresentation(ArticleFeature articleFeature) {
		this.articleFeature = articleFeature;
	}
	public ArticleFeature getArticleFeature() {
		return articleFeature;
	}
	
	public List<ArticleFeatureInstance> getInstances() {
		if (articleFeature == null) {
			return null;
		}
		List<ArticleFeatureInstance> result = new ArrayList<ArticleFeatureInstance>(
				articleFeature.getInstances());
		Collections.sort(result, new Comparator<ArticleFeatureInstance>() {
			@Override
			public int compare(ArticleFeatureInstance o1, ArticleFeatureInstance o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return result;
	}
	
	public String toString() {
		return getName();
	}
	
	public String getName() {		
		return articleFeature != null ? articleFeature.getName() : null; 
	}
	
	public String getEnglishName() {		
		return articleFeature != null ? articleFeature.getEnglishName() : null; 
	}
	
}
