package com.docum.view.container;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.docum.domain.TemperatureSpyStateEnum;
import com.docum.domain.po.common.ActualCargoCondition;
import com.docum.domain.po.common.DeclaredCargoCondition;
import com.docum.domain.po.common.Inspection;
import com.docum.domain.po.common.NormativeDocument;
import com.docum.domain.po.common.SurveyPlace;
import com.docum.domain.po.common.Surveyor;
import com.docum.service.BaseService;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;

public class InspectionDlgView extends AbstractDlgView implements Serializable {
	private static final long serialVersionUID = -6995707358505279412L;
	private Inspection inspection;
		
	private List<SurveyPlace> surveyPlaces;
	private List<Surveyor> surveyors;
	private List<NormativeDocument> documents;
	
	public Double getMinTemperature() {		 
		return getDeclaredCondition().getMinTemperature();
	}

	public void setMinTemperature(Double temperature) {		  
			getDeclaredCondition().setMinTemperature(temperature);				
	}
	
	public Double getMaxTemperature() {		 
		return getDeclaredCondition().getMaxTemperature();
	}

	public void setMaxTemperature(Double temperature) {		  
			getDeclaredCondition().setMaxTemperature(temperature);				
	}	
	
	public Double getTemperature(){
		return getActualCondition().getTemperature();
	}
	
	public void setTemperature(Double temperature){
		getActualCondition().setTemperature(temperature);
	}
	
	public Boolean getHasTemperatureTestDeviation(){ 
		return getActualCondition().getHasTemperatureTestDeviation();
	}
	
	public void setHasTemperatureTestDeviation(Boolean hasTemperatureTestDeviation) {
		getActualCondition().setHasTemperatureTestDeviation(hasTemperatureTestDeviation);
	}

	public List<TemperatureSpyStateEnum> getTemperatureSpyStates(){
		return Arrays.asList(TemperatureSpyStateEnum.values());
	}

	public TemperatureSpyStateEnum getTemperatureSpyState() {
		return getActualCondition().getTemperatureSpyState();
	}

	public void setTemperatureSpyState(TemperatureSpyStateEnum temperatureSpyState) {
		getActualCondition().setTemperatureSpyState(temperatureSpyState);
	}

	public String getTemperatureSpyNumber() {
		return getActualCondition().getTemperatureSpyNumber();
	}

	public void setTemperatureSpyNumber(String temperatureSpyNumber) {
		getActualCondition().setTemperatureSpyNumber(temperatureSpyNumber);
	}
	
	public DeclaredCargoCondition getDeclaredCondition(){
		return inspection != null?
				inspection.getContainer().getDeclaredCondition() : null;
	}
	
	public ActualCargoCondition getActualCondition(){
		return inspection != null?
				inspection.getContainer().getActualCondition() : null;
	}
	
	public InspectionDlgView(Inspection inspection, BaseService baseService) {
		this.inspection = inspection;		
		surveyPlaces = baseService.getAll(SurveyPlace.class);
		surveyors = baseService.getAll(Surveyor.class);
		documents = baseService.getAll(NormativeDocument.class);
	}

	public List<Surveyor> getSurveyors() {
		return surveyors;
	}

	public List<NormativeDocument> getDocuments() {
		return documents;
	}

	public List<SurveyPlace> getSurveyPlaces() {
		return surveyPlaces;
	}

	public String getTitle() {
		return "Результаты инспекции";
	}

	public void save() {
		fireAction(this, DialogActionEnum.ACCEPT);
	}

	public Inspection getInspection() {
		return inspection;
	}

	public void setInspection(Inspection inspection) {
		this.inspection = inspection;
	}

}
