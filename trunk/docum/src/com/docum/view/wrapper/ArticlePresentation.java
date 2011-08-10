package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.ArticleFeature;
import com.docum.domain.po.common.NormativeDocument;
import com.docum.util.AlgoUtil;


public class ArticlePresentation implements Serializable {
	private static final long serialVersionUID = -5515979893597102874L;
	private Article article;

	public ArticlePresentation(Article article) {
		this.article = article;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public List<ArticleFeaturePresentation> getFeatures() {
		if (article == null) {
			return null;
		}
		Collection<ArticleFeature> c = article.getFeatures();
		List<ArticleFeaturePresentation> result = new ArrayList<ArticleFeaturePresentation>(
				c.size());
		AlgoUtil.transform(result, c, new ArticleFeatureTransformer());
		Collections.sort(result, new Comparator<ArticleFeaturePresentation>() {
			@Override
			public int compare(ArticleFeaturePresentation o1,
					ArticleFeaturePresentation o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});
		return result;
	}

	public List<ArticleCategory> getCategories() {
		if (this.article == null || this.article.getId() == null) {
			return null;
		} else {
			return article.getCategories();
		}
	}
	
	public List<NormativeDocumentPresentation> getDocuments() {
		if (article == null) {
			return null;
		}
		Collection<NormativeDocument> c = article.getDocuments();
		List<NormativeDocumentPresentation> result = new ArrayList<NormativeDocumentPresentation>(c.size());
		AlgoUtil.transform(result, c, new NormativeDocumentTransformer());
		Collections.sort(result, new Comparator<NormativeDocumentPresentation>() {
			@Override
			public int compare(NormativeDocumentPresentation o1, NormativeDocumentPresentation o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});
		return result;
	}
	
	public String getName() {		
		return article != null ? article.getName() : null; 
	}
	
	public String getEnglishName() {		
		return article != null ? article.getEnglishName() : null; 
	}
	
}
