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
import com.docum.domain.po.common.City;
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
	private InvoiceBinder invoiceBinder;

	private PurchaseOrder selectedOrder;
	private PurchaseOrder freeOrder;
	private OrderBinder orderBinder;

	private BillOfLading selectedBill;
	private BillOfLading freeBill;
	private BillBinder billBinder;
	private List<City> cities;

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
		return new ArrayList<PurchaseOrder>(orderBinder.getFreeDocuments());
	}

	public List<PurchaseOrder> getSelectedOrders() {
		return new ArrayList<PurchaseOrder>(orderBinder.getSelectedDocuments());
	}

	public List<BillOfLading> getFreeBills() {
		return new ArrayList<BillOfLading>(billBinder.getFreeDocuments());
	}

	public List<BillOfLading> getSelectedBills() {
		return new ArrayList<BillOfLading>(billBinder.getSelectedDocuments());
	}

	public void bindInvoice() {
		invoiceBinder.bind(freeInvoice);
	}

	public void bindOrder() {
		orderBinder.bind(freeOrder);
	}

	public void bindBill() {
		billBinder.bind(freeBill);
	}

	public void unBindInvoice() {
		invoiceBinder.unbind(selectedInvoice);
	}

	public void unBindOrder() {
		orderBinder.unbind(selectedOrder);
	}

	public void unBindBill() {
		billBinder.unbind(selectedBill);
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

	public List<City> getCities() {
		return cities;
	}

	public ContainerDlgView(Container container, InvoiceService invoiceService,
			PurchaseOrderService orderService, BillOfLadingService billService,
			BaseService baseService) {
		this.container = container;		

		cities = baseService.getAll(City.class, null);
		
		Set<Invoice> freeInvoices = new HashSet<Invoice>(
				invoiceService.getInvoicesByVoyage(container.getVoyage()
						.getId()));
		List<Invoice> i = this.container != null ? this.container.getInvoices()
				: null;
		ListHandler.sublist(freeInvoices, i);
		invoiceBinder = new InvoiceBinder(this.container, freeInvoices,
				invoiceService);

		Set<PurchaseOrder> freeOrders = new HashSet<PurchaseOrder>(
				orderService.getOrdersByVoyage(container.getVoyage().getId()));
		List<PurchaseOrder> p = this.container != null ? this.container
				.getOrders() : null;
		ListHandler.sublist(freeOrders, p);
		orderBinder = new OrderBinder(this.container, freeOrders, orderService);

		Set<BillOfLading> freeBills = new HashSet<BillOfLading>(
				billService.getBillsByVoyage(container.getVoyage().getId()));
		List<BillOfLading> b = this.container != null ? this.container
				.getBillOfLadings() : null;
		ListHandler.sublist(freeBills, b);
		billBinder = new BillBinder(this.container, freeBills, billService);
	}

	public Set<Invoice> getInvoices(Container container) {
		return invoiceBinder.prepareDocumentsToSave(container);
	}

	public Set<PurchaseOrder> getOrders(Container container) {
		return orderBinder.prepareDocumentsToSave(container);
	}

	public Set<BillOfLading> getBills(Container container) {
		return billBinder.prepareDocumentsToSave(container);
	}

	public void save() {
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
			initialFreeDocuments = new HashSet<T>(freeDocuments);
			initialContainerDocuments = new HashSet<T>(
					getContainerDocuments(container));
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
			if (document == null) {
				return;
			}
			if (!initialContainerDocuments.contains(document)) {
				bindedDocuments.add(document);
			}
			selectedDocuments.add(document);
			unbindedDocuments.remove(document);
			freeDocuments.remove(document);
		}

		public void unbind(T document) {
			if (document == null) {
				return;
			}
			if (!initialFreeDocuments.contains(document)) {
				unbindedDocuments.add(document);
			}
			freeDocuments.add(document);
			bindedDocuments.remove(document);
			selectedDocuments.remove(document);
		}

		public Set<T> prepareDocumentsToSave(Container container) {
			Set<T> result = new HashSet<T>(bindedDocuments.size()
					+ unbindedDocuments.size());
			for (T document : bindedDocuments) {
				document = loadDocument(document, clazz);
				bindContainer(document, container);
				result.add(document);
			}
			for (T document : unbindedDocuments) {
				document = loadDocument(document, clazz);
				unbindContainer(document, container);
				result.add(document);
			}
			return result;
		}

		private T loadDocument(T document, Class<T> clazz) {
			return document != null ? service
					.getObject(clazz, document.getId()) : null;
		}

		protected abstract void bindContainer(T document, Container container);

		protected abstract void unbindContainer(T document, Container container);

		protected abstract Collection<T> getContainerDocuments(
				Container container);
	}

	private static class InvoiceBinder extends DocumentBinder<Invoice> {
		private static final long serialVersionUID = 4860074165553906205L;

		public InvoiceBinder(Container container,
				Collection<Invoice> freeDocuments, BaseService service) {
			super(container, freeDocuments, service, Invoice.class);
		}

		@Override
		protected void bindContainer(Invoice document, Container container) {
			document.getContainers().add(container);
			container.getInvoices().add(document);
		}

		@Override
		protected void unbindContainer(Invoice document, Container container) {
			document.getContainers().remove(container);
			container.getInvoices().remove(document);
		}

		@Override
		protected Collection<Invoice> getContainerDocuments(Container container) {
			return container.getInvoices();
		}
	}

	private static class OrderBinder extends DocumentBinder<PurchaseOrder> {
		private static final long serialVersionUID = 8508554541469701405L;

		public OrderBinder(Container container,
				Collection<PurchaseOrder> freeDocuments, BaseService service) {
			super(container, freeDocuments, service, PurchaseOrder.class);

		}

		@Override
		protected void bindContainer(PurchaseOrder document, Container container) {
			document.getContainers().add(container);
			container.getOrders().add(document);
		}

		@Override
		protected void unbindContainer(PurchaseOrder document,
				Container container) {
			document.getContainers().remove(container);
			container.getOrders().remove(document);

		}

		@Override
		protected Collection<PurchaseOrder> getContainerDocuments(
				Container container) {
			return container.getOrders();
		}

	}

	private static class BillBinder extends DocumentBinder<BillOfLading> {
		private static final long serialVersionUID = 6808352342434979877L;

		public BillBinder(Container container,
				Collection<BillOfLading> freeDocuments, BaseService service) {
			super(container, freeDocuments, service, BillOfLading.class);

		}

		@Override
		protected void bindContainer(BillOfLading document, Container container) {
			document.getContainers().add(container);
			container.getBillOfLadings().add(document);
		}

		@Override
		protected void unbindContainer(BillOfLading document,
				Container container) {
			document.getContainers().remove(container);
			container.getBillOfLadings().remove(document);

		}

		@Override
		protected Collection<BillOfLading> getContainerDocuments(
				Container container) {
			return container.getBillOfLadings();
		}

	}

}
