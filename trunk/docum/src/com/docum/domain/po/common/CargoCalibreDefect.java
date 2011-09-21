package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class CargoCalibreDefect extends IdentifiedEntity{
	private static final long serialVersionUID = 1020573780060838795L;

	@ManyToOne(optional = false)
	private CargoInspectionInfo inspectionInfo;

	@ManyToOne(optional = false)
	private ArticleCategory articleCategory;

	@ManyToOne(optional = false)
	private CargoPackageCalibre calibre;
	
	private double percentage;

	public CargoCalibreDefect() {
		super();
	}

	public CargoCalibreDefect(CargoInspectionInfo inspectionInfo, ArticleCategory articleCategory,
			CargoPackageCalibre calibre) {
		this();
		this.inspectionInfo = inspectionInfo;
		this.articleCategory = articleCategory;
		this.calibre = calibre;
	}

	public CargoInspectionInfo getInspectionInfo() {
		return inspectionInfo;
	}

	public void setInspectionInfo(CargoInspectionInfo inspectionInfo) {
		this.inspectionInfo = inspectionInfo;
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}

	public CargoPackageCalibre getCalibre() {
		return calibre;
	}

	public void setCalibre(CargoPackageCalibre calibre) {
		this.calibre = calibre;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
}
