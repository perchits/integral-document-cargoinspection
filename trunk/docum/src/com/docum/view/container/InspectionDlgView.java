package com.docum.view.container;

import java.io.Serializable;
import java.util.List;

import com.docum.domain.po.common.Inspection;
import com.docum.domain.po.common.SurveyPlace;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;

public class InspectionDlgView extends AbstractDlgView implements
		Serializable {
	private static final long serialVersionUID = -6995707358505279412L;	
	private Inspection inspection;
	private List<SurveyPlace> surveyPlaces;

	public List<SurveyPlace> getSurveyPlaces() {
		return surveyPlaces;
	}

	public InspectionDlgView(Inspection inspection, List<SurveyPlace> surveyPlaces) {		
		this.inspection = inspection;
		this.surveyPlaces = surveyPlaces;
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
