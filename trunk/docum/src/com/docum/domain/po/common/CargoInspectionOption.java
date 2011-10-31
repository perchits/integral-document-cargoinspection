package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class CargoInspectionOption extends IdentifiedEntity implements Comparable<CargoInspectionOption> {
	private static final long serialVersionUID = 6093648676864917112L;

	@ManyToOne(optional=false)
	private CargoInspectionInfo inspectionInfo;
	
	@ManyToOne(optional=false)
	private ArticleInspectionOption articleInspectionOption;
	
	private Double value;
	
	public CargoInspectionOption() {
		
	}
	
	public CargoInspectionOption(ArticleInspectionOption articleInspectionOption,
			CargoInspectionInfo inspectionInfo) {
		this.articleInspectionOption = articleInspectionOption;
		this.inspectionInfo = inspectionInfo;
	}
	
	public CargoInspectionOption(CargoInspectionOption other) {
		copy(other);
	}
	
	public void copy(CargoInspectionOption other){
		this.setId(other.getId());
		this.articleInspectionOption = other.articleInspectionOption;
		this.inspectionInfo = other.inspectionInfo;
		this.value = other.value;
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

	@Override
	public int compareTo(CargoInspectionOption o) {
		return -Double.compare(o.value, this.value); 
	}

	
}
