package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class CargoInspectionOption extends IdentifiedEntity {
	private static final long serialVersionUID = 6093648676864917112L;

	@ManyToOne(optional=false)
	private CargoInspectionInfo inspectionInfo;
	
	@ManyToOne(optional=false)
	private ArticleInspectionOption articleInspectionOption;
	
	private Double value;
	
	public CargoInspectionOption() {
		
	}

	public CargoInspectionOption(CargoInspectionInfo inspectionInfo,
			ArticleInspectionOption articleInspectionOption) {
		this.inspectionInfo = inspectionInfo;
		this.articleInspectionOption = articleInspectionOption;
	}
	
	public CargoInspectionInfo getInspectionInfo() {
		return inspectionInfo;
	}

	public void setInspectionInfo(CargoInspectionInfo cargoInspectionInfo) {
		this.inspectionInfo = cargoInspectionInfo;
	}

	public ArticleInspectionOption getArticleInspectionOption() {
		return articleInspectionOption;
	}

	public void setArticleInspectionOption(ArticleInspectionOption articleInspectionOption) {
		this.articleInspectionOption = articleInspectionOption;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	
}
