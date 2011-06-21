package com.docum.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Invoice;
import com.docum.service.InvoiceService;
import com.docum.util.ListHandler;
import com.docum.view.wrapper.VoyagePresentation;

public class ContainerDlgView implements Serializable {
	private static final long serialVersionUID = 8476485603392082763L;
	private Container container;
	private Invoice selectedInvoice;
	private Invoice freeInvoice;
	private Set<Invoice> unsavedInvoices = new HashSet<Invoice>();
	private Set<Invoice> freeInvoices = new HashSet<Invoice>();
	private InvoiceService invoiceService;

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
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
		return invoice != null ? invoiceService.getObject(Invoice.class,
				invoice.getId()) : null;
	}

	public ContainerDlgView(Container container,
			VoyagePresentation selectedVoyage, InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
		this.container = container;
		freeInvoices = new HashSet<Invoice>(
				this.invoiceService.getInvoicesByVoyage(selectedVoyage.getVoyage()
						.getId()));
		List<Invoice> sub = this.container != null ? this.container.getInvoices() : null;
		ListHandler.sublist(freeInvoices, sub);
	}
	
	public void saveInvoives(){
		invoiceService.save(unsavedInvoices);
		unsavedInvoices.clear();
	}

}
