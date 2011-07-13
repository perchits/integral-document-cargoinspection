package com.docum.view;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Report;
import com.docum.view.dict.BaseView;

@Controller("reportBean")
@Scope("session")
public class ReportView extends BaseView {
	private static final long serialVersionUID = -6034143364888144075L;

	private static final String sign = "Отчет";

	private Report report = new Report(); 
	private Integer containersAmount;
	private Container selectedContainer;

	@Override
	public void saveObject() {
		getBaseService().save(this.report);
		refreshObjects();
	}

	@Override
	public void newObject() {
		super.newObject();
		this.report = new Report();
	}

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return report.getNumber();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return this.report != null ? this.report : new Report();
	}


	public List<Container> getContainers() {
		return null;
	}

	public Integer getContainersAmount() {
		return containersAmount;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public Container getSelectedContainer() {
		return selectedContainer;
	}

	public void setSelectedContainer(Container selectedContainer) {
		this.selectedContainer = selectedContainer;
	}
}
