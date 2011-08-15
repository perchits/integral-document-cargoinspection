package com.docum.ui.converter;

import javax.faces.convert.FacesConverter;

import com.docum.domain.po.common.ArticleCategory;

@FacesConverter(value = "articleCategoryConverter")
public class ArticleCategoryConverter extends IdentifiedEntityAbstractConverter<ArticleCategory> {
	public ArticleCategoryConverter() {
		super(ArticleCategory.class);
	}
}
