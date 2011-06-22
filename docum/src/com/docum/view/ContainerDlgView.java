package com.docum.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.docum.domain.ContainerStateEnum;
import com.docum.domain.po.common.BillOfLading;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Invoice;
import com.docum.domain.po.common.PurchaseOrder;
import com.docum.service.BillOfLadingService;
import com.docum.service.InvoiceService;
import com.docum.service.PurchaseOrderService;
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

	private PurchaseOrder selectedOrder;
	private PurchaseOrder freeOrder;
	private Set<PurchaseOrder> unsavedOrders = new HashSet<PurchaseOrder>();
	private Set<PurchaseOrder> freeOrders = new HashSet<PurchaseOrder>();
	private PurchaseOrderService orderService;
	
	private BillOfLading selectedBill;
	private BillOfLading freeBill;
	private Set<BillOfLading> unsavedBills = new HashSet<BillOfLading>();
	private Set<BillOfLading> freeBills = new HashSet<BillOfLading>();
	private BillOfLadingService billService;
	
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

	public List<PurchaseOrder> getFreeOrders() {
		return new ArrayList<PurchaseOrder>(freeOrders);
	}
	
	public List<BillOfLading> getFreeBills() {
		return new ArrayList<BillOfLading>(freeBills);
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
	
	public void bindOrders() {
		if (freeOrders != null) {
			container.getOrders().add(freeOrder);
			freeOrder = loadOrder(freeOrder);
			freeOrder.getContainers().add(container);
			freeOrders.remove(freeOrder);
			unsavedOrders.add(freeOrder);
		}
	}
	
	public void bindBills() {
		if (freeBill != null) {
			container.getBillOfLadings().add(freeBill);
			freeBill = loadBill(freeBill);
			freeBill.getContainers().add(container);
			freeBills.remove(freeBill);
			unsavedBills.add(freeBill);
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
	
	public void unBindOrder() {
		if (selectedOrder != null) {
			container.getOrders().remove(selectedOrder);
			selectedOrder = loadOrder(selectedOrder);
			selectedOrder.getContainers().remove(container);
			freeOrders.add(selectedOrder);
			unsavedOrders.add(selectedOrder);
		}
	}
	
	public void unBindBill() {
		if (selectedBill != null) {
			container.getBillOfLadings().remove(selectedBill);
			selectedBill = loadBill(selectedBill);
			selectedBill.getContainers().remove(container);
			freeBills.add(selectedBill);
			unsavedBills.add(selectedBill);
		}
	}
	
	public Invoice getFreeInvoice() {
		return freeInvoice;
	}
		
	public void setFreeInvoice(Invoice freeInvoice) {
		this.freeInvoice = freeInvoice;
	}

	public void setFreeOrder(PurchaseOrder freeOrder) {
		this.freeOrder = freeOrder;
	}

	public void setFreeBill(BillOfLading freeBill) {
		this.freeBill = freeBill;
	}

	public PurchaseOrder getFreeOrder() {
		return freeOrder;
	}
	
	public BillOfLading getFreeBill() {
		return freeBill;
	}
	
	private Invoice loadInvoce(Invoice invoice) {
		return invoice != null ? invoiceService.getObject(Invoice.class,
				invoice.getId()) : null;
	}
	
	private PurchaseOrder loadOrder(PurchaseOrder order) {
		return order != null ? orderService.getObject(PurchaseOrder.class,
				order.getId()) : null;
	}
	
	private BillOfLading loadBill(BillOfLading bill) {
		return bill != null ? billService.getObject(BillOfLading.class,
				bill.getId()) : null;
	}

	public ContainerDlgView(Container container,
			VoyagePresentation selectedVoyage, InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
		this.container = container;
		freeInvoices = new HashSet<Invoice>(
				this.invoiceService.getInvoicesByVoyage(selectedVoyage
						.getVoyage().getId()));
		List<Invoice> sub = this.container != null ? this.container
				.getInvoices() : null;
		ListHandler.sublist(freeInvoices, sub);
	}

	public void saveDocuments() {
		invoiceService.save(unsavedInvoices);
		unsavedInvoices.clear();
		orderService.save(unsavedOrders);
		unsavedOrders.clear();
		billService.save(unsavedBills);
		unsavedBills.clear();
	}

	public String getTitle() {
		return container.getId() != null ? "Редактирование контейнера "
				+ container.toString() : "Новый контейнер";
	}
	
	
	public ContainerStateEnum[] getContainerStates() {
		return ContainerStateEnum.values();
	}

	public void setSelectedOrder(PurchaseOrder selectedOrder) {
		this.selectedOrder = selectedOrder;
	}

	public PurchaseOrder getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedBill(BillOfLading selectedBill) {
		this.selectedBill = selectedBill;
	}

	public BillOfLading getSelectedBill() {
		return selectedBill;
	}

}
