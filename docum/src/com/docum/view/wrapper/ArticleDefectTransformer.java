package com.docum.view.wrapper;

import com.docum.domain.po.common.ArticleDefect;
import com.docum.util.AlgoUtil;

public class ArticleDefectTransformer implements
	AlgoUtil.TransformFunctor<ArticleDefectPresentation, ArticleDefect> {

	@Override
	public ArticleDefectPresentation transform(ArticleDefect from) {		
		return new ArticleDefectPresentation(from);
	}

}