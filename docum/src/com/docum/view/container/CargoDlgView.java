package com.docum.view.container;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.docum.domain.SortOrderEnum;
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
	private boolean hasNormativeDocument;
	private NormativeDocument normativeDocument;
	private List<Supplier> suppliers;
	private List<Article> articles;

	public CargoDlgView(Cargo cargo, boolean hasNormativeDocument,
			NormativeDocument normativeDocument,
			ArticleService articleService, BaseService baseService) {
		this.cargo = cargo;
		this.hasNormativeDocument = hasNormativeDocument;
		this.normativeDocument = normativeDocument;
		this.articleService = articleService;
		HashMap<String, SortOrderEnum> sortFields = new HashMap<String, SortOrderEnum>();
		sortFields.put("company.name", SortOrderEnum.ASC);
		suppliers = baseService.getAll(Supplier.class, sortFields);
		sortFields.clear();
		sortFields.put("name", SortOrderEnum.ASC);
		articles = baseService.getAll(Article.class, sortFields);			
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public boolean isHasNormativeDocument() {
		return hasNormativeDocument;
	}

	public void setHasNormativeDocument(boolean hasNormativeDocument) {
		this.hasNormativeDocument = hasNormativeDocument;
	}

	public NormativeDocument getNormativeDocument() {
		return normativeDocument;
	}

	public void setNormativeDocument(NormativeDocument normativeDocument) {
		this.normativeDocument = normativeDocument;
	}

	public void articleChange() {
		if (cargo != null && cargo.getArticle() != null) {
			cargo.setArticleCategory(null);
			normativeDocument = null;
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
			return articleService.getDocumentByArticle(cargo
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
