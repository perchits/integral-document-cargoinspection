package com.docum.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Invoice;
import com.docum.domain.po.common.Voyage;
import com.docum.service.ContainerService;
import com.docum.service.InvoiceService;
import com.docum.util.AlgoUtil;
import com.docum.util.FacesUtil;
import com.docum.util.ListHandler;
import com.docum.view.dict.BaseView;
import com.docum.view.param.FlashParamKeys;
import com.docum.view.wrapper.CargoPresentation;
import com.docum.view.wrapper.CargoTransformer;
import com.docum.view.wrapper.ContainerPresentation;
import com.docum.view.wrapper.ContainerTransformer;
import com.docum.view.wrapper.VoyagePresentation;
import com.docum.view.wrapper.VoyageTransformer;

@Controller("containerBean")
@Scope("session")
public class ContainerView extends BaseView implements Serializable {

	private static final long serialVersionUID = 3476513399265370923L;
	private static final String sign = "Контейнер";
	private static final int MAX_LIST_SIZE = 10;
	private Container container;
	private boolean reloadContainer = true;
	private VoyagePresentation selectedVoyage;
	@Autowired
	private ContainerService containerService;
	@Autowired
	private InvoiceService invoiceService;
	private ArrayList<ContainerPresentation> containers;
	private Invoice selectedInvoice;
	private Invoice freeInvoice;
	private Set<Invoice> unsavedInvoices = new HashSet<Invoice>();
	private Set<Invoice> freeInvoices  = new HashSet<Invoice>();
	

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return container != null ? container.getNumber() : null;
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return container != null ? container : new Container();
	}

	@Override
	public void refreshObjects() {
		//super.refreshObjects();
		Collection<Container> c = containerService
				.getContainersByVoyage(selectedVoyage != null ? selectedVoyage
						.getVoyage().getId() : null);
		containers = new ArrayList<ContainerPresentation>(c.size());
		AlgoUtil.transform(containers, c, new ContainerTransformer());
	}

	@Override
	public void saveObject(){
//		super.saveObject();
		container = containerService.save(container);
		invoiceService.save(unsavedInvoices);
		unsavedInvoices.clear();
		refreshObjects();
	}
	
	public ArrayList<ContainerPresentation> getContainers() {
		if (containers == null) {
			refreshObjects();
		}
		return containers;
	}

	public ContainerPresentation getContainer() {
		if (container == null) {
			return null;
		} else if (reloadContainer) {
			loadContainer(container);
			reloadContainer = false;
		}
		return new ContainerPresentation(container);
	}

	public void setContainer(ContainerPresentation containerPresentation) {
		if (containerPresentation == null
				|| containerPresentation.getContainer() == null) {
			this.container = null;
			return;
		}
		reloadContainer = !containerPresentation.getContainer().equals(
				this.container);
		if (reloadContainer) {
			this.container = containerPresentation.getContainer();
		}
	}

	private void loadContainer(Container container) {
		this.container = (container != null && container.getId() != null) ? containerService
				.getObject(Container.class, container.getId()) : container;
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

	public List<VoyagePresentation> voyageAutocomplete(Object suggest)
			throws Exception {
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

	public void setSelectedVoyage(VoyagePresentation selectedVoyage) {
		this.selectedVoyage = selectedVoyage;
	}

	public VoyagePresentation getSelectedVoyage() {
		return selectedVoyage;
	}

	public void voyageSelect(SelectEvent event) {
		refreshObjects();
		setContainer(new ContainerPresentation(null));
	}

	public String getContainersTitle() {
		return selectedVoyage != null ? String.format(
				"Контейнеры (судозаход: %1$s)", selectedVoyage.getVoyageInfo())
				: "Судозаход не выбран...";

	}

	public Boolean getIsSelected() {
		return container == null ? false : true;
	}

	public List<CargoPresentation> getContainerCargoes() {
		if (container != null) {
			Collection<Cargo> c = container.getCargoes();
			List<CargoPresentation> result = new ArrayList<CargoPresentation>(
					c.size());
			AlgoUtil.transform(result, c, new CargoTransformer());
			return result;
		} else {
			return null;
		}
	}

	public void loadPage() {
		Container container = (Container) FacesUtil
				.getFlashParam(FlashParamKeys.CONTAINER);
		if (container != null) {
			this.container = container;
			loadContainer(this.container);
			selectedVoyage = new VoyagePresentation(this.container.getVoyage());
			refreshObjects();
		}
	}

	@Override
	public void newObject() {
		super.newObject();
		if (selectedVoyage == null) {
			String message = "Добавление контейнера невозможно пока не выбран судозаход!";
			showErrorMessage(message);
			addCallbackParam("dontShow", true);
		} else {
			container = new Container();
			container.setVoyage(selectedVoyage.getVoyage());
			beforeDialogShow();
		}
	}

	@Override
	public void editObject(ActionEvent actionEvent) {
		super.editObject(actionEvent);
		if (selectedVoyage != null) {
			beforeDialogShow();
		}
	}

	public void setSelectedInvoice(Invoice selectedInvoice) {
		this.selectedInvoice = selectedInvoice;
	}

	public Invoice getSelectedInvoice() {
		return selectedInvoice;
	}

	public List<Invoice> getFreeInvoices() {	
		return new ArrayList<Invoice>(freeInvoices);
	}

	public void bindInvoice() {
		if (freeInvoice != null) {
			container.getInvoices().add(freeInvoice);
			freeInvoice = loadInvoce(freeInvoice);
			freeInvoice.getContainers().add(container);
			freeInvoices.remove(freeInvoice);
			unsavedInvoices.add(freeInvoice);
		}
	}

	public void unBindInvoice() {
		if (selectedInvoice != null) {
			container.getInvoices().remove(selectedInvoice);
			selectedInvoice = loadInvoce(selectedInvoice);
			selectedInvoice.getContainers().remove(container);
			freeInvoices.add(selectedInvoice);
			unsavedInvoices.add(selectedInvoice);
		}
	}

	public void setFreeInvoice(Invoice freeInvoice) {
		this.freeInvoice = freeInvoice;
	}

	public Invoice getFreeInvoice() {
		return freeInvoice;		
	}
	
	private Invoice loadInvoce(Invoice invoice) {
		return invoice != null ? invoiceService.getObject(
					Invoice.class, invoice.getId()) : null;
	}

	private void beforeDialogShow() {
		freeInvoices = new HashSet<Invoice>(invoiceService.getInvoicesByVoyage(selectedVoyage
				.getVoyage().getId()));
		List<Invoice> sub = container != null ? container.getInvoices() : null;
		ListHandler.sublist(freeInvoices, sub);		
	}
}
