package com.docum.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Customer;
import com.docum.domain.po.common.Report;
import com.docum.domain.po.common.Voyage;
import com.docum.service.ContainerService;
import com.docum.service.CustomerService;
import com.docum.service.ReportingService;
import com.docum.test.TestUtil;
import com.docum.util.AlgoUtil;
import com.docum.util.DocumLogger;
import com.docum.util.FacesUtil;
import com.docum.view.dict.BaseView;
import com.docum.view.navigation.ViewNavigation;
import com.docum.view.param.FlashParamKeys;
import com.docum.view.wrapper.ContainerPresentation;
import com.docum.view.wrapper.ContainerTransformer;
import com.docum.view.wrapper.ReportPresentation;
import com.docum.view.wrapper.ReportTransformer;
import com.docum.view.wrapper.VoyagePresentation;
import com.docum.view.wrapper.VoyageTransformer;

@Controller("reportBean")
@Scope("session")
public class ReportView extends BaseView {
	private static final long serialVersionUID = -6034143364888144075L;
	private static final String sign = "Отчет";
	private static final int MAX_LIST_SIZE = 10;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	@Autowired
	private ContainerService containerService;
	@Autowired
	private ReportingService reportingService;
	@Autowired
	private CustomerService customerService;
	
	private Report report = new Report();
	private List<ReportPresentation> reports;
	private Integer containersAmount;
	private Container selectedContainer;
	private Container container;
	private VoyagePresentation selectedVoyage;
	private ContainerPresentation[] selectedContainers;
	private String reportUrl;
	private Customer customer;
	private DualListModel<ContainerPresentation> reportContainers = 
		new DualListModel<ContainerPresentation>();

	@Override
	public void saveObject() {
		getBaseService().save(this.report);
		refreshObjects();
	}

	@Override
	public void newObject() {
		if (this.selectedVoyage == null) {
			String message = "Добавление отчета невозможно пока не выбран судозаход!";
			showMessage(message);
			addCallbackParam("dontShow", true);
		} else if (containerService.getContainersWithoutReportByVoyage(
			this.selectedVoyage.getVoyage().getId()).size() == 0) {
			String message = "Добавление отчета невозможно, т.к. для всех контейнеров в " +
				"выбранном судозаходе отчеты созданы!";
			showMessage(message);
			addCallbackParam("dontShow", true);
			
		} else {
			super.newObject();
			this.report = new Report();
			StringBuffer sb = new StringBuffer();
			Date now = new Date();
			if (this.reports != null) {
				sb.append(dateFormat.format(now)).append("/").append(this.reports.size() + 1);
			} else {
				sb.append(dateFormat.format(now)).append("/1");
			}
			this.report.setNumber(sb.toString());
			this.report.setDate(new Date());
			this.report.setCustomer(customerService.getDefaultCustomer());
			this.reportContainers.setSource(getContainersWithoutReport());
			this.reportContainers.setTarget(getContainersForReport());
		}
	}
	
	@Override
	public void refreshObjects() {
		if (this.selectedVoyage != null) {
			Collection<Report> r = 
				reportingService.getReportsByVoyage(this.selectedVoyage.getVoyage().getId());
			this.reports = new ArrayList<ReportPresentation>(r.size()); 
			AlgoUtil.transform(this.reports, r, new ReportTransformer());
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
	
	public ArrayList<ContainerPresentation> getContainersForReport() {
		ArrayList<ContainerPresentation> result = new ArrayList<ContainerPresentation>();
		if (selectedVoyage != null) {
			if (this.report != null && this.report.getId() != null) {
				Collection<Container> c = 
					containerService.getContainersByReport(this.report.getId());
				result = new ArrayList<ContainerPresentation>(c.size());
				AlgoUtil.transform(result, c, new ContainerTransformer());
				return result;
			} else {
				return result;
				
			}
		} else {
			return result;
		}
	}
	
	public ArrayList<ContainerPresentation> getContainersWithoutReport() {
		ArrayList<ContainerPresentation> result = null;
		if (selectedVoyage != null) {
			Collection<Container> c = containerService.getContainersWithoutReportByVoyage(
					this.selectedVoyage.getVoyage().getId());
			result = new ArrayList<ContainerPresentation>(c.size());
			AlgoUtil.transform(result, c, new ContainerTransformer());
		}
		return result;
	}
	
	@Override
	public void editObject(ActionEvent actionEvent) {
		super.editObject(actionEvent);
		this.reportContainers.setSource(getContainersWithoutReport());
		this.reportContainers.setTarget(getContainersForReport());
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
		if (this.reportContainers.getTarget().size() == 0) {
			String message = "Для сохранения отчета необходимо выбрать контейнер(ы).";
			showMessage(message);
			addCallbackParam("dontClose", true);
			return;
		}
		DocumLogger.log("Создание отчета для контейнеров.");
		List<Container> reportDoneContainers = new ArrayList<Container>();
		for (ContainerPresentation containerPresentation: this.reportContainers.getTarget()) {
			containerPresentation.getContainer().setReportDone(true);
			reportDoneContainers.add(containerPresentation.getContainer());
		}
		this.report.setCustomer(this.customer);
		this.report.setContainers(reportDoneContainers);
		saveObject();
		super.getBaseService().save(reportDoneContainers);
		List<Container> noReportContainers = new ArrayList<Container>();
		for (ContainerPresentation containerPresentation: this.reportContainers.getSource()) {
			containerPresentation.getContainer().setReportDone(false);
			noReportContainers.add(containerPresentation.getContainer());
		}
		super.getBaseService().save(noReportContainers);
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

	public List<ReportPresentation> getReports() {
		if (this.reports == null) {
			refreshObjects();
		}
		return reports;
	}
	
	public void setRemoveContainerFromReport(ContainerPresentation containerPresentation) {
		this.report.getContainers().remove(containerPresentation.getContainer());
		containerPresentation.getContainer().setReportDone(false);
		saveObject();
		super.getBaseService().save(containerPresentation.getContainer());
	}
	
	public void setAddContainerToReport(ContainerPresentation containerPresentation) {
		this.report.getContainers().add(containerPresentation.getContainer());
		containerPresentation.getContainer().setReportDone(true);
		saveObject();
		super.getBaseService().save(containerPresentation.getContainer());
	}
	
	public void renderReport() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (!reportingService.checkStarOfficeConnection()) {
			showErrorMessage(
				"Отсутствует связь со службой StarOffice. Генерация отчетов невозможна.");
			addCallbackParam("dontShow", true);
		} else if (this.report == null) {
			showErrorMessage("Отчета для редактирования не выбран");
			addCallbackParam("dontShow", true);
		} else {
			try {
				reportingService.createReport(this.report);
				this.reportUrl = "/docum/resources/reporting/resultReport_" + this.report.getId() + ".pdf";
			} catch(Exception e) {
				fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
					"Исключение при создании отчета!", e.getMessage()));
				DocumLogger.log(e);
			}
		}
	}
	
	public String getReportUrl() {
		StringBuffer sb = new StringBuffer("?").append("No cache").
			append(TestUtil.getRandomLong());
		return reportUrl + sb.toString();
	}

	public ContainerPresentation[] getSelectedContainers() {
		return selectedContainers;
	}

	public void setSelectedContainers(ContainerPresentation[] selectedContainers) {
		this.selectedContainers = selectedContainers;
	}

	public void setWrappedReport(ReportPresentation wrappedReport) {
		if (wrappedReport != null) {
			this.report = wrappedReport.getReport();
		}
	}

	public List<Customer> getCustomers() {				
		return getBaseService().getAll(Customer.class, DEFAULT_SORT_FIELDS);
	}
	
	public Customer getSelectedCustomer() {
		return this.report == null ? null : this.report.getCustomer();
	}

	public void setSelectedCustomer(Customer selectedCustomer) {
		this.customer = selectedCustomer;
		this.report.setCustomer(selectedCustomer);
	}

	public DualListModel<ContainerPresentation> getReportContainers() {
		return reportContainers;
	}

	public void setReportContainers(DualListModel<ContainerPresentation> reportContainers) {
		this.reportContainers = reportContainers;
	}
}
