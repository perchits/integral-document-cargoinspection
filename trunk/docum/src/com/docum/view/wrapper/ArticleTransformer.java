package com.docum.view.wrapper;

import com.docum.domain.po.common.Article;
import com.docum.util.AlgoUtil;

public class ArticleTransformer implements
AlgoUtil.TransformFunctor<ArticlePresentation, Article> {

	@Override
	public ArticlePresentation transform(Article from) {		
		return new ArticlePresentation(from);
	}

}