package com.docum.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Report;
import com.docum.domain.po.common.Voyage;
import com.docum.service.ContainerService;
import com.docum.service.ReportingService;
import com.docum.util.AlgoUtil;
import com.docum.util.DocumLogger;
import com.docum.util.FacesUtil;
import com.docum.view.dict.BaseView;
import com.docum.view.navigation.ViewNavigation;
import com.docum.view.param.FlashParamKeys;
import com.docum.view.wrapper.ContainerPresentation;
import com.docum.view.wrapper.ContainerTransformer;
import com.docum.view.wrapper.VoyagePresentation;
import com.docum.view.wrapper.VoyageTransformer;

@Controller("reportBean")
@Scope("session")
public class ReportView extends BaseView {
	private static final long serialVersionUID = -6034143364888144075L;
	private static final String sign = "Отчет";
	private static final int MAX_LIST_SIZE = 10;

	@Autowired
	private ContainerService containerService;
	@Autowired
	private ReportingService reportingService;
	
	private Report report = new Report();
	private List<Report> reports;
	private Integer containersAmount;
	private Container selectedContainer;
	private Container container;
	private VoyagePresentation selectedVoyage;

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
	public void refreshObjects() {
		if (this.selectedVoyage != null) {
			this.reports = 
				reportingService.getReportsByVoyage(this.selectedVoyage.getVoyage().getId());
		}
	}

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBriefInfo() {
		return report.getNumber();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return this.report != null ? this.report : new Report();
	}
	
	public ArrayList<ContainerPresentation> getContainersWithoutReport() {
		if (selectedVoyage != null) {
			Collection<Container> c = containerService.getContainersWithoutReportByVoyage(
					this.selectedVoyage.getVoyage().getId());
			ArrayList<ContainerPresentation> result = new ArrayList<ContainerPresentation>(c.size());
			AlgoUtil.transform(result, c, new ContainerTransformer());
			return result;
		} else {
			return null;
		}
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
			//reportingService.createReport(this.container, this.report.getId());
		}
	}
	
	public String goToContainer() {
		FacesUtil.putFlashParam(FlashParamKeys.CONTAINER, selectedContainer);
		return ViewNavigation.CONTAINERS_VIEW;
	}

	public List<Container> getContainers() {
		return containerService.getContainersByReport(this.report.getId());
	}
	
	public String getReportsVoyage() {
		return selectedVoyage != null ? selectedVoyage.getVoyageInfo()
				: "Выберите судозаход...";
	}
	
	public List<VoyagePresentation> voyageAutocomplete(Object suggest) throws Exception {
		String pref = (String) suggest;
		ArrayList<VoyagePresentation> result = new ArrayList<VoyagePresentation>();
		for (VoyagePresentation voyage : getVoyages()) {
			if ((voyage.getVoyageInfo() != null && voyage.getVoyageInfo()
					.toLowerCase().indexOf(pref.toLowerCase()) >= 0)
					|| "".equals(pref)) {
				result.add(voyage);
				if (result.size() >= MAX_LIST_SIZE)
					break;
			}
		}
		return result;
	}
	
	public List<VoyagePresentation> getVoyages() {
		HashMap<String, SortOrderEnum> sortFields = new HashMap<String, SortOrderEnum>();
		sortFields.put("arrivalDate", SortOrderEnum.DESC);
		List<Voyage> voyages = (List<Voyage>) getBaseService().getAll(
				Voyage.class, sortFields);
		List<VoyagePresentation> result = new ArrayList<VoyagePresentation>(
				voyages.size());
		AlgoUtil.transform(result, voyages, new VoyageTransformer(false));
		return result;
	}
	
	public void voyageSelect(SelectEvent event) {
		setSelectedVoyage((VoyagePresentation)event.getObject());
		refreshObjects();
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

	public VoyagePresentation getSelectedVoyage() {
		return selectedVoyage;
	}

	public void setSelectedVoyage(VoyagePresentation selectedVoyage) {
		this.selectedVoyage = selectedVoyage;
	}

	public List<Report> getReports() {
		if (this.reports == null) {
			refreshObjects();
		}
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}
}
