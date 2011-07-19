package com.docum.view.container;

import java.io.Serializable;

import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.InspectionBrief;
import com.docum.service.BaseService;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;

public class InspectionBriefDlgView extends AbstractDlgView implements
		Serializable {
	private static final long serialVersionUID = -6995707358505279412L;

	private Container container;
	private InspectionBrief inspectionBrief;

	public InspectionBriefDlgView(Container container, BaseService baseService) {
		this.container = container;
		// this.inspectionBrief = baseService.getAll(InspectionBrief.class);
	}

	public String getTitle() {
		return "Результат инспекции";
	}

	public void save() {
		fireAction(this, DialogActionEnum.ACCEPT);
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public InspectionBrief getInspectionBrief() {
		return inspectionBrief;
	}

	public void setInspectionBrief(InspectionBrief inspectionBrief) {
		this.inspectionBrief = inspectionBrief;
	}

}
