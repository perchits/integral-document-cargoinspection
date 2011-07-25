package com.docum.view.container.unit;

import java.io.Serializable;

import com.docum.domain.po.common.Inspection;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;
import com.docum.view.container.ContainerHolder;

public class InspectionUnit implements Serializable, DialogActionHandler {
	private static final long serialVersionUID = -3715245363081562382L;
	private ContainerHolder containerHolder;
	private Inspection inspection; 
	
	public Inspection getInspection() {
		return inspection;
	}
	
	public String getSurveyPlace() {
		return inspection != null && inspection.getSurveyPlace() != null ? 
				inspection.getSurveyPlace().getRussianName() : "Место инспекции не указано";  
	}
	
	public void setInspection(Inspection inspection) {
		this.inspection = inspection;
	}

	public InspectionUnit(ContainerHolder containerHolder) {
		this.containerHolder = containerHolder;		
	}

	@Override
	public void handleAction(AbstractDlgView dialog, DialogActionEnum action) {
		// TODO Auto-generated method stub
		
	}

}
