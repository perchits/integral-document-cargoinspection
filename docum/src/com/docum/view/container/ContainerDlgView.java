package com.docum.view.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.primefaces.model.DualListModel;

import com.docum.domain.ContainerStateEnum;
import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.BillOfLading;
import com.docum.domain.po.common.City;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Invoice;
import com.docum.domain.po.common.Port;
import com.docum.domain.po.common.PurchaseOrder;
import com.docum.service.BaseService;
import com.docum.service.BillOfLadingService;
import com.docum.service.InvoiceService;
import com.docum.service.PurchaseOrderService;
import com.docum.util.ListHandler;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;

public class ContainerDlgView extends AbstractDlgView implements Serializable {
	private static final long serialVersionUID = 8476485603392082763L;
	private Container container;

	private InvoiceBinder invoiceBinder;
	private OrderBinder orderBinder;
	private BillBinder billBinder;
	private BaseService baseService;

	private List<City> cities;
	private List<Port> ports;
	
	private Invoice invoice;
	private PurchaseOrder order;
	private BillOfLading billOfLading; 
		
	public BillOfLading getBillOfLading() {
		return billOfLading;
	}

	public void setBillOfLading(BillOfLading bill) {
		this.billOfLading = bill;
	}

	public PurchaseOrder getOrder() {
		return order;
	}

	public void setOrder(PurchaseOrder order) {
		this.order = order;
	}

	public Invoice getInvoice() {
		return invoice;
	}	

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public void addInvoice(){
		invoice = new Invoice();
		invoice.setVoyage(container.getVoyage());	
	}	
	
	public void addOrder() {
		order = new PurchaseOrder();
		order.setVoyage(container.getVoyage());		
	}
	
	public void addBill() {
		billOfLading = new BillOfLading();
		billOfLading.setVoyage(container.getVoyage());		
	}
	
	public void removeInvoice(){				
		invoice = removeDocument(invoice,invoiceBinder.getModel().getSource());
	}
	
	public void removeOrder(){				
		order = removeDocument(order,orderBinder.getModel().getSource());
	}
	
	public void removeBill(){				
		billOfLading = removeDocument(billOfLading,billBinder.getModel().getSource());
	}
	
	public <T extends IdentifiedEntity> T removeDocument(T object, List<T> list){
		if (object.getId() != null) {
			baseService.deleteObject(object.getClass(),object.getId());
		}		
		list.remove(object);		
		return null;
	}
	
	public void saveInvoice() {				
		invoice = saveDocument(invoice, invoiceBinder.getModel().getSource());
	}
	
	public void saveOrder() {		
		order = saveDocument(order, orderBinder.getModel().getSource());		
	}	
	
	public void saveBill() {		
		billOfLading = saveDocument(billOfLading, billBinder.getModel().getSource());		
	}
	
	public <T extends IdentifiedEntity> T saveDocument(T object, List<T> list){
		object = baseService.save(object);
		if (object != null) {
			int index = list.indexOf(object); 
			if (index != -1) {
				list.set(index, object);
			} else {
				list.add(object);
			}
		}
		return object;
	}
	
	public String getInvoiceTitle() {
		return invoice != null ? invoice.getId() == null ? 
				"Новый инвойс" : "Правка инвойса" : null;
	}
	
	public String getOrderTitle() {
		return order != null ? order.getId() == null ? 
				"Новый заказ" : "Правка заказа" : null;
	}
	
	public String getBillTitle() {
		return billOfLading != null ? billOfLading.getId() == null ? 
				"Новый коносамент" : "Правка коносамента" : null;
	}
	
	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}
	
	public List<City> getCities() {
		return cities;
	}

	public List<Port> getPorts() {
		return ports;
	}
	
	public ContainerDlgView(Container container, InvoiceService invoiceService,
			PurchaseOrderService orderService, BillOfLadingService billService,
			BaseService baseService) {
		this.baseService = baseService;
		this.container = container;
			
		HashMap<String, SortOrderEnum> sortFields = new HashMap<String, SortOrderEnum>();
		sortFields.put("name", SortOrderEnum.ASC);
		cities = baseService.getAll(City.class, sortFields);
		ports = baseService.getAll(Port.class, sortFields);			

		Set<Invoice> freeInvoices = new HashSet<Invoice>(
				invoiceService.getInvoicesByVoyage(container.getVoyage()
						.getId()));
		Set<Invoice> i = this.container != null ? this.container.getInvoices()
				: null;
		ListHandler.sublist(freeInvoices, i);
		invoiceBinder = new InvoiceBinder(this.container, freeInvoices,
				invoiceService);

		Set<PurchaseOrder> freeOrders = new HashSet<PurchaseOrder>(
				orderService.getOrdersByVoyage(container.getVoyage().getId()));
		Set<PurchaseOrder> p = this.container != null ? this.container
				.getOrders() : null;
		ListHandler.sublist(freeOrders, p);
		orderBinder = new OrderBinder(this.container, freeOrders, orderService);

		Set<BillOfLading> freeBills = new HashSet<BillOfLading>(
				billService.getBillsByVoyage(container.getVoyage().getId()));
		Set<BillOfLading> b = this.container != null ? this.container
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

	public List<ContainerStateEnum> getContainerStates() {
		return Arrays.asList(ContainerStateEnum.values());
	}

	public DualListModel<Invoice> getInvoiceModel() {
		return invoiceBinder.getModel();
	}

	public void setInvoiceModel(DualListModel<Invoice> model) {
		invoiceBinder.setModel(model);
	}

	public DualListModel<PurchaseOrder> getOrderModel() {
		return orderBinder.getModel();
	}

	public void setOrderModel(DualListModel<PurchaseOrder> model) {
		orderBinder.setModel(model);
	}

	public DualListModel<BillOfLading> getBillModel() {
		return billBinder.getModel();
	}

	public void setBillModel(DualListModel<BillOfLading> model) {
		billBinder.setModel(model);
	}
	
	private static abstract class DocumentBinder<T extends IdentifiedEntity>
			implements Serializable {
		private static final long serialVersionUID = 1421810586471900554L;
		private DualListModel<T> model;
		private Class<T> clazz;
		private BaseService service;
		public DocumentBinder(Container container, Collection<T> freeDocuments,
				BaseService service, Class<T> clazz) {
			model = new DualListModel<T>(new ArrayList<T>(freeDocuments),
					new ArrayList<T>(getContainerDocuments(container)));
			this.service = service;
			this.clazz = clazz;
		}
		public Set<T> prepareDocumentsToSave(Container container) {
			Set<T> result = new HashSet<T>(model.getSource().size()
					+ model.getTarget().size());
			for (T document : model.getTarget()) {
				document = loadDocument(document, clazz);
				bindContainer(document, container);
				result.add(document);
			}
			for (T document : model.getSource()) {
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

		public DualListModel<T> getModel() {
			return model;
		}

		public void setModel(DualListModel<T> model) {
			this.model = model;
		}

		protected abstract Collection<T> getContainerDocuments(
				Container container);

		protected abstract void bindContainer(T document, Container container);

		protected abstract void unbindContainer(T document, Container container);

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
