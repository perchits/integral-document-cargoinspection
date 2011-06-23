package com.docum.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.docum.domain.ContainerStateEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.BillOfLading;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Invoice;
import com.docum.domain.po.common.PurchaseOrder;
import com.docum.service.BaseService;
import com.docum.service.BillOfLadingService;
import com.docum.service.InvoiceService;
import com.docum.service.PurchaseOrderService;
import com.docum.util.ListHandler;

public class ContainerDlgView extends AbstractDlgView implements Serializable {
	private static final long serialVersionUID = 8476485603392082763L;
	private Container container;

	private Invoice selectedInvoice;
	private Invoice freeInvoice;
	private InvoiceService invoiceService;
	private InvoiceBinder invoiceBinder;

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
		return new ArrayList<Invoice>(invoiceBinder.getFreeDocuments());
	}

	public List<Invoice> getSelectedInvoices() {
		return new ArrayList<Invoice>(invoiceBinder.getSelectedDocuments());
	}
	
	public List<PurchaseOrder> getFreeOrders() {
		return new ArrayList<PurchaseOrder>(freeOrders);
	}

	public List<BillOfLading> getFreeBills() {
		return new ArrayList<BillOfLading>(freeBills);
	}

	public void bindInvoice() {
		invoiceBinder.bind(freeInvoice);
	}

	public void bindOrder() {
		if (freeOrders != null) {
			container.getOrders().add(freeOrder);
			freeOrder = loadOrder(freeOrder);
			freeOrder.getContainers().add(container);
			freeOrders.remove(freeOrder);
			unsavedOrders.add(freeOrder);
		}
	}

	public void bindBill() {
		if (freeBill != null) {
			container.getBillOfLadings().add(freeBill);
			freeBill = loadBill(freeBill);
			freeBill.getContainers().add(container);
			freeBills.remove(freeBill);
			unsavedBills.add(freeBill);
		}
	}

	public void unBindInvoice() {
		invoiceBinder.unbind(selectedInvoice);
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

	private PurchaseOrder loadOrder(PurchaseOrder order) {
		return order != null ? orderService.getObject(PurchaseOrder.class,
				order.getId()) : null;
	}

	private BillOfLading loadBill(BillOfLading bill) {
		return bill != null ? billService.getObject(BillOfLading.class,
				bill.getId()) : null;
	}

	public ContainerDlgView(Container container, InvoiceService invoiceService,
			PurchaseOrderService orderService, BillOfLadingService billService) {
		this.invoiceService = invoiceService;
		this.orderService = orderService;
		this.billService = billService;
		this.container = container;

		Set<Invoice> freeInvoices = new HashSet<Invoice>(
				this.invoiceService.getInvoicesByVoyage(container.getVoyage()
						.getId()));
		List<Invoice> i = this.container != null ? this.container.getInvoices()
				: null;
		ListHandler.sublist(freeInvoices, i);
		invoiceBinder = new InvoiceBinder(this.container, freeInvoices, invoiceService);

		freeOrders = new HashSet<PurchaseOrder>(
				this.orderService.getOrdersByVoyage(container.getVoyage()
						.getId()));
		List<PurchaseOrder> p = this.container != null ? this.container
				.getOrders() : null;
		ListHandler.sublist(freeOrders, p);

		freeBills = new HashSet<BillOfLading>(
				this.billService
						.getBillsByVoyage(container.getVoyage().getId()));
		List<BillOfLading> b = this.container != null ? this.container
				.getBillOfLadings() : null;
		ListHandler.sublist(freeBills, b);
	}

	public void saveDocuments() {
		invoiceService.save(invoiceBinder.getDocumentsToSave());

		orderService.save(unsavedOrders);
		unsavedOrders.clear();
		billService.save(unsavedBills);
		unsavedBills.clear();

		// TODO Тут мы создаем событие.
		fireAction(this, DialogActionEnum.ACCEPT);
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


	private static abstract class DocumentBinder<T extends IdentifiedEntity>
			implements Serializable {
		private static final long serialVersionUID = 5905102091907209582L;
		private Container container;
		private Set<T> initialFreeDocuments;
		private Set<T> initialContainerDocuments;
		private Set<T> selectedDocuments;
		private Set<T> freeDocuments;
		private Set<T> bindedDocuments = new HashSet<T>();
		private Set<T> unbindedDocuments = new HashSet<T>();
		private BaseService service;
		Class<T> clazz;
		
		public DocumentBinder(Container container, Collection<T> freeDocuments,
				BaseService service, Class<T> clazz) {
			this.container = container;
			initialFreeDocuments = new HashSet<T>(freeDocuments);
			initialContainerDocuments = new HashSet<T>(getContainerDocuments(container));
			this.freeDocuments = new HashSet<T>(initialFreeDocuments);
			this.selectedDocuments = new HashSet<T>(initialContainerDocuments);
			this.service = service;
			this.clazz = clazz;
		}
		
		public Collection<T> getFreeDocuments() {
			return freeDocuments;
		}

		public Collection<T> getSelectedDocuments() {
			return selectedDocuments;
		}
		
		public void bind(T document) {
			if(document == null) {
				return;
			}
			if(!initialContainerDocuments.contains(document)) {
				bindedDocuments.add(document);
			}
			selectedDocuments.add(document);
			unbindedDocuments.remove(document);
			freeDocuments.remove(document);
		}
		
		public void unbind(T document) {
			if(document == null) {
				return;
			}
			if(!initialFreeDocuments.contains(document)) {
				unbindedDocuments.add(document);
			}
			freeDocuments.add(document);
			bindedDocuments.remove(document);
			selectedDocuments.remove(document);
		}
		
		public Set<T> getDocumentsToSave() {
			Set<T> result = new HashSet<T>(bindedDocuments.size() + unbindedDocuments.size());
			for(T document : bindedDocuments) {
				document = loadDocument(document, clazz);
				bindContainer(document, container);
				result.add(document);
			}
			for(T document : unbindedDocuments) {
				document = loadDocument(document, clazz);
				unbindContainer(document, container);
				result.add(document);
			}
			return result;
		}

		private T loadDocument(T document, Class<T> clazz) {
			return document != null ? service.getObject(clazz,
					document.getId()) : null;
		}
		
		protected abstract void bindContainer(T document, Container container);
		protected abstract void unbindContainer(T document, Container container);
		protected abstract Collection<T> getContainerDocuments(Container container);
	}

	private static class InvoiceBinder extends DocumentBinder<Invoice> {
		private static final long serialVersionUID = 4860074165553906205L;

		public InvoiceBinder(Container container,
				Collection<Invoice> freeDocuments, InvoiceService service) {
			super(container, freeDocuments, service, Invoice.class);
		}

		@Override
		protected void bindContainer(Invoice document, Container container) {
			document.getContainers().add(container);
		}

		@Override
		protected void unbindContainer(Invoice document, Container container) {
			document.getContainers().remove(container);
		}

		@Override
		protected Collection<Invoice> getContainerDocuments(Container container) {
			return container.getInvoices();
		}
	}
}
