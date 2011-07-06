package com.docum.view.container;

import java.io.Serializable;
import java.util.List;

import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.Cargo;
import com.docum.service.ArticleService;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;

public class CargoDlgView extends AbstractDlgView implements Serializable {
	private static final long serialVersionUID = -4923604397701684618L;

	private Cargo cargo;
	private ArticleService articleService;

	public CargoDlgView(Cargo cargo, ArticleService articleService) {
		this.cargo = cargo;
		this.articleService = articleService;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void articleChange() {
		if (cargo != null && cargo.getArticle() != null) {
			cargo.setArticleCategory(null);
		}
	}

	public List<ArticleCategory> getArticleCategories() {
		if (cargo != null && cargo.getArticle() != null) {
			return articleService.getArticleCategoryByArticle(cargo
					.getArticle().getId());
		} else
			return null;
	}
	
	public void save() {		
		fireAction(this, DialogActionEnum.ACCEPT);
	}

}
