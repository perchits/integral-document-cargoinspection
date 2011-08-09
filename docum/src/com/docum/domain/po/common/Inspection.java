package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.OrderedEntityUtil;

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
	private String packageState;
	
	@Column(length = 512)
	private String packageStateEng;
	
	private Double temperature;
	
	private String packageToSurvey;
	
	@Column(length = 512)
	private String surveyorConclusion;
	
	private String surveyorConclusionEng;
	
	@ManyToOne
	private Surveyor surveyor;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderColumn(name="ord")
	private List<FileUrl> images = new ArrayList<FileUrl>();

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

	public String getPackageState() {
		return packageState;
	}

	public void setPackageState(String packageSate) {
		this.packageState = packageSate;
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

	public String getPackageStateEng() {
		return packageStateEng;
	}

	public void setPackageStateEng(String packageSateEng) {
		this.packageStateEng = packageSateEng;
	}

	public String getSurveyorConclusionEng() {
		return surveyorConclusionEng;
	}

	public void setSurveyorConclusionEng(String surveyorConclusionEng) {
		this.surveyorConclusionEng = surveyorConclusionEng;
	}

	public List<FileUrl> getImages() {
		return images;
	}

	public void setImages(List<FileUrl> images) {
		this.images = images;
	}
	
	public void addImage(FileUrl url) {
		images.add(url);
	}
	
	public void removeImage(FileUrl url) {
		images.remove(url);
	}
	
	public void moveImageUp(FileUrl url) {
		OrderedEntityUtil.moveUp(url, images);
	}

	public void moveImageDown(FileUrl url) {
		OrderedEntityUtil.moveDown(url, images);
	}
}
