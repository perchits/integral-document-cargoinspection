package com.docum.view.wrapper;

import com.docum.domain.po.common.ArticleCategory;
import com.docum.util.AlgoUtil;

public class ArticleCategoryTransformer implements
	AlgoUtil.TransformFunctor<ArticleCategoryPresentation, ArticleCategory> {

	@Override
	public ArticleCategoryPresentation transform(ArticleCategory from) {		
		return new ArticleCategoryPresentation(from);
	}

}