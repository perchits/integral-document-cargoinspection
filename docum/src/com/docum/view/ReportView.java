package com.docum.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Report;
import com.docum.service.ContainerService;
import com.docum.service.ReportingService;
import com.docum.util.DocumLogger;
import com.docum.util.FacesUtil;
import com.docum.view.dict.BaseView;
import com.docum.view.navigation.ViewNavigation;
import com.docum.view.param.FlashParamKeys;

@Controller("reportBean")
@Scope("session")
public class ReportView extends BaseView {
	private static final long serialVersionUID = -6034143364888144075L;
	private static final String sign = "Отчет";

	@Autowired
	private ContainerService containerService;
	@Autowired
	private ReportingService reportingService;
	private Report report = new Report(); 
	private Integer containersAmount;
	private Container selectedContainer;
	private Container container;

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
	
	public List<Container> getContainersWithoutReport() {
		return containerService.getContainersWithoutReport();
	}
	
	@Override
	public void deleteObject() {
		List<Container> containers = containerService.getContainersByReport(this.report.getId()); 
		for (Container container: containers) {
			container.setReportDone(false);
		}
		super.getBaseService().save(containers);
		super.getBaseService().deleteObject(getBeanObject().getClass(), getBeanObject().getId());
		refreshObjects();
	}
	
	public void createReport() {
		DocumLogger.log("Создание отчета для контейнера: " + this.container.getNumber());
		List<Container> containers = new ArrayList<Container>();
		if (this.container != null) {
			containers.add(this.container);
			this.report.setContainers(containers);
			saveObject();
			this.container.setReportDone(true);
			super.getBaseService().save(this.container);
			reportingService.createReport(this.container, this.report.getId());
		}
	}
	
	public String goToContainer() {
		FacesUtil.putFlashParam(FlashParamKeys.CONTAINER, selectedContainer);
		return ViewNavigation.CONTAINERS_VIEW;
	}

	public List<Container> getContainers() {
		return containerService.getContainersByReport(this.report.getId());
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

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}
}
