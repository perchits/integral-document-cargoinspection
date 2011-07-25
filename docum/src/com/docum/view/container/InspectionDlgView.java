package com.docum.view.container;

import java.io.Serializable;

import com.docum.domain.po.common.Inspection;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;

public class InspectionDlgView extends AbstractDlgView implements
		Serializable {
	private static final long serialVersionUID = -6995707358505279412L;
	
	private Inspection inspection;

	public InspectionDlgView(Inspection inspection) {		
		this.inspection = inspection;			
	}

	public String getTitle() {
		return "Результаты инспекции";
	}

	public void save() {
		fireAction(this, DialogActionEnum.ACCEPT);
	}	

	public Inspection getInspectionBrief() {
		return inspection;
	}

	public void setInspectionBrief(Inspection inspection) {
		this.inspection = inspection;
	}

}
