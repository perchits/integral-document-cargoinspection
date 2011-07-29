package com.docum.domain.po.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.docum.domain.po.IdentifiedEntity;

@Entity
@Table(name = "inspection")
public class Inspection extends IdentifiedEntity {
	private static final long serialVersionUID = -6382871159976843122L;

	@OneToOne(optional = false)
	private Container container;
	private String actualSeal;
	private Date surveyRequestDate;
	private Date surveyDate;
	@ManyToOne(optional = false)
	private SurveyPlace surveyPlace;
	private String unloadingPlace;
	private String unloadingPlaceEng;
	@Column(length = 512)
	private String packageType;
	@Column(length = 512)
	private String packageTypeEng;
	@Column(length = 512)
	private String packageForming;
	@Column(length = 512)
	private String packageFormingEng;
	@Column(length = 512)
	private String packageSate;
	@Column(length = 512)
	private String packageSateEng;
	private String a4StickerUrl;
	private String shippingMarkUrl;
	@ManyToOne
	private NormativeDocument normativeDocument;
	private Double temperature;
	private String packageToSurvey;
	@Column(length = 512)
	private String surveyorConclusion;
	private String surveyorConclusionEng;
	@ManyToOne
	private Surveyor surveyor;

	public Inspection() {

	}

	public Inspection(Container container, SurveyPlace surveyPlace) {
		this.container = container;
		this.surveyPlace = surveyPlace;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public String getActualSeal() {
		return actualSeal;
	}

	public void setActualSeal(String actualSeal) {
		this.actualSeal = actualSeal;
	}

	public Date getSurveyRequestDate() {
		return surveyRequestDate;
	}

	public void setSurveyRequestDate(Date surveyRequestDate) {
		this.surveyRequestDate = surveyRequestDate;
	}

	public Date getSurveyDate() {
		return surveyDate;
	}

	public void setSurveyDate(Date surveyDate) {
		this.surveyDate = surveyDate;
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

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getPackageForming() {
		return packageForming;
	}

	public void setPackageForming(String packageForming) {
		this.packageForming = packageForming;
	}

	public String getPackageSate() {
		return packageSate;
	}

	public void setPackageSate(String packageSate) {
		this.packageSate = packageSate;
	}

	public String getA4StickerUrl() {
		return a4StickerUrl;
	}

	public void setA4StickerUrl(String a4StickerUrl) {
		this.a4StickerUrl = a4StickerUrl;
	}

	public String getShippingMarkUrl() {
		return shippingMarkUrl;
	}

	public void setShippingMarkUrl(String shippingMarkUrl) {
		this.shippingMarkUrl = shippingMarkUrl;
	}

	public NormativeDocument getNormativeDocument() {
		return normativeDocument;
	}

	public void setNormativeDocument(NormativeDocument normativeDocument) {
		this.normativeDocument = normativeDocument;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public String getPackageToSurvey() {
		return packageToSurvey;
	}

	public void setPackageToSurvey(String packageToSurvey) {
		this.packageToSurvey = packageToSurvey;
	}

	public String getSurveyorConclusion() {
		return surveyorConclusion;
	}

	public void setSurveyorConclusion(String surveyorConclusion) {
		this.surveyorConclusion = surveyorConclusion;
	}

	public Surveyor getSurveyor() {
		return surveyor;
	}

	public void setSurveyor(Surveyor surveyor) {
		this.surveyor = surveyor;
	}

	public String getPackageTypeEng() {
		return packageTypeEng;
	}

	public void setPackageTypeEng(String packageTypeEng) {
		this.packageTypeEng = packageTypeEng;
	}

	public String getPackageFormingEng() {
		return packageFormingEng;
	}

	public void setPackageFormingEng(String packageFormingEng) {
		this.packageFormingEng = packageFormingEng;
	}

	public String getPackageSateEng() {
		return packageSateEng;
	}

	public void setPackageSateEng(String packageSateEng) {
		this.packageSateEng = packageSateEng;
	}

	public String getSurveyorConclusionEng() {
		return surveyorConclusionEng;
	}

	public void setSurveyorConclusionEng(String surveyorConclusionEng) {
		this.surveyorConclusionEng = surveyorConclusionEng;
	}

}
