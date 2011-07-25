package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.docum.domain.po.IdentifiedEntity;

@Entity
@Table(name="inspection")
public class Inspection extends IdentifiedEntity {
	private static final long serialVersionUID = -6382871159976843122L;

	@OneToOne(optional = false)
	private Container container;
	@ManyToOne(optional = false)
	private SurveyPlace surveyPlace;

	private String unloadingPlace;
	private String unloadingPlaceEng;
	private String palletsFormation;
	private String palletsFormationEng;
	private String a4Sticker;
	private String a4StickerEng;
	private String shippingMark;
	private String shippingMarkEng;
	private String normativePaper;
	private String normativePaperEng;
	
	public Inspection(){
		
	}

	public Inspection(Container container, SurveyPlace surveyPlace) {
		this.container = container;
		this.surveyPlace = surveyPlace;
	}

	public SurveyPlace getSurveyPlace() {
		return surveyPlace;
	}

	public void setSurveyPlace(SurveyPlace surveyPlace) {
		this.surveyPlace = surveyPlace;
	}
	
	public String getUnloadingPlace() {
		return unloadingPlace;
	}

	public void setUnloadingPlace(String unloadingPlace) {
		this.unloadingPlace = unloadingPlace;
	}

	public String getUnloadingPlaceEng() {
		return unloadingPlaceEng;
	}

	public void setUnloadingPlaceEng(String unloadingPlaceEng) {
		this.unloadingPlaceEng = unloadingPlaceEng;
	}

	public String getPalletsFormation() {
		return palletsFormation;
	}

	public void setPalletsFormation(String palletsFormation) {
		this.palletsFormation = palletsFormation;
	}

	public String getPalletsFormationEng() {
		return palletsFormationEng;
	}

	public void setPalletsFormationEng(String palletsFormationEng) {
		this.palletsFormationEng = palletsFormationEng;
	}

	public String getA4Sticker() {
		return a4Sticker;
	}

	public void setA4Sticker(String a4Sticker) {
		this.a4Sticker = a4Sticker;
	}

	public String getA4StickerEng() {
		return a4StickerEng;
	}

	public void setA4StickerEng(String a4StickerEng) {
		this.a4StickerEng = a4StickerEng;
	}

	public String getShippingMark() {
		return shippingMark;
	}

	public void setShippingMark(String shippingMark) {
		this.shippingMark = shippingMark;
	}

	public String getShippingMarkEng() {
		return shippingMarkEng;
	}

	public void setShippingMarkEng(String shippingMarkEng) {
		this.shippingMarkEng = shippingMarkEng;
	}

	public String getNormativePaper() {
		return normativePaper;
	}

	public void setNormativePaper(String normativePaper) {
		this.normativePaper = normativePaper;
	}

	public String getNormativePaperEng() {
		return normativePaperEng;
	}

	public void setNormativePaperEng(String normativePaperEng) {
		this.normativePaperEng = normativePaperEng;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}
}
