package com.docum.view.container;

import java.io.Serializable;
import java.util.List;

import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.NormativeDocument;
import com.docum.domain.po.common.Supplier;
import com.docum.service.ArticleService;
import com.docum.service.BaseService;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;

public class CargoDlgView extends AbstractDlgView implements Serializable {
	private static final long serialVersionUID = -4923604397701684618L;

	private Cargo cargo;
	private ArticleService articleService;	 
	private List<Supplier> suppliers;
	private List<Article> articles;	 

	public CargoDlgView(Cargo cargo, ArticleService articleService, BaseService baseService) {
		this.cargo = cargo;
		this.articleService = articleService;
		suppliers = baseService.getAll(Supplier.class, null);
		articles = baseService.getAll(Article.class, null);			
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
			if (cargo.getInspectionInfo() != null) {
				cargo.getInspectionInfo().setNormativeDocument(null);
			}
		}
	}

	public List<ArticleCategory> getArticleCategories() {
		if (cargo != null && cargo.getArticle() != null) {
			return articleService.getArticleCategoryByArticle(cargo
					.getArticle().getId());
		} else
			return null;
	}
	
	public List<NormativeDocument> getNormativeDocuments(){
		if (cargo != null && cargo.getArticle() != null) {
			return articleService.getNormativeDocumentByArticle(cargo
					.getArticle().getId());
		} else
			return null;
	}
	
	public List<Supplier> getSuppliers() {
		return suppliers;
	}
	
	public List<Article> getArticles() {
		return articles;
	}
	
	public String getTitle() {
		return cargo.getId() != null ? "Редактирование груза "
				+ cargo.toString() : "Новый груз";
	}
	
	public void save() {		
		fireAction(this, DialogActionEnum.ACCEPT);
	}

}
