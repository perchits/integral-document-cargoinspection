package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class CargoInspectionInfo extends IdentifiedEntity {
	private static final long serialVersionUID = -109254110803956456L;

	@OneToOne(optional = false)
	private Cargo cargo;

	@OneToMany(mappedBy = "inspectionInfo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderColumn(name="ord")
	private List<CargoDefectGroup> defectGroups = new ArrayList<CargoDefectGroup>();

	@ManyToOne
	private NormativeDocument normativeDocument;

	private String stickerUrl;
	private String stickerUrlEng;
	private String shippingMarkUrl;
	private String shippingMarkUrlEng;
	
	public CargoInspectionInfo() {
	}

	public CargoInspectionInfo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public List<CargoDefectGroup> getDefectGroups() {
		return defectGroups;
	}

	public void setDefectGroups(List<CargoDefectGroup> defectGroups) {
		this.defectGroups = defectGroups;
	}

	public void addDefectGroup(CargoDefectGroup defectGroup){
		if(defectGroup != null) {
			defectGroups.add(defectGroup);
			defectGroup.setInspectionInfo(this);
		}
	}

	public void removeDefectGroup(CargoDefectGroup defectGroup){
		if(defectGroup != null && defectGroups.remove(defectGroup)) {
			defectGroup.setInspectionInfo(null);
		}
	}
	
	public void resetArticleCategory(ArticleCategory articleCategory) {
		defectGroups.clear();
		//Получаем все категории, начиная с этой и ниже, и генерим по ним дефекты.
		ListIterator<ArticleCategory> it =
			articleCategory.getArticle().getCategories().listIterator(articleCategory.getOrd());
		while(it.hasNext()) {
			defectGroups.add(new CargoDefectGroup(this, it.next(), defectGroups.size()));
		}
	}

	public NormativeDocument getNormativeDocument() {
		return normativeDocument;
	}

	public void setNormativeDocument(NormativeDocument normativeDocument) {
		this.normativeDocument = normativeDocument;
	}

	public String getStickerUrl() {
		return stickerUrl;
	}

	public void setStickerUrl(String stickerUrl) {
		this.stickerUrl = stickerUrl;
	}

	public String getStickerUrlEng() {
		return stickerUrlEng;
	}

	public void setStickerUrlEng(String stickerUrlEng) {
		this.stickerUrlEng = stickerUrlEng;
	}

	public String getShippingMarkUrl() {
		return shippingMarkUrl;
	}

	public void setShippingMarkUrl(String shippingMarkUrl) {
		this.shippingMarkUrl = shippingMarkUrl;
	}
	
	public String getShippingMarkUrlEng() {
		return shippingMarkUrlEng;
	}

	public void setShippingMarkUrlEng(String shippingMarkUrlEng) {
		this.shippingMarkUrlEng = shippingMarkUrlEng;
	}

}
