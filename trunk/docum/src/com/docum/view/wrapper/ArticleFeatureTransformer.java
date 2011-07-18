package com.docum.view.wrapper;

import com.docum.domain.po.common.ArticleFeature;
import com.docum.util.AlgoUtil;

public class ArticleFeatureTransformer implements
AlgoUtil.TransformFunctor<ArticleFeaturePresentation, ArticleFeature> {

	@Override
	public ArticleFeaturePresentation transform(ArticleFeature from) {		
		return new ArticleFeaturePresentation(from);
	}

}