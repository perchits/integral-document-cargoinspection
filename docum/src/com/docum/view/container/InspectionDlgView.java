package com.docum.view.container;

import java.io.Serializable;
import java.util.List;

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
