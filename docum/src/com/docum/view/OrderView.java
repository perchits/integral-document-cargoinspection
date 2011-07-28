package com.docum.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.BillOfLading;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Invoice;
import com.docum.domain.po.common.PurchaseOrder;
import com.docum.domain.po.common.Voyage;
import com.docum.service.BillOfLadingService;
import com.docum.service.InvoiceService;
import com.docum.service.PurchaseOrderService;

@Controller("orderBean")
@Scope("session")
public class OrderView extends AbstractDocumentView {
	private static final long serialVersionUID = 4894206468649180355L;

	private static final String sign = "Заказ";

	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private BillOfLadingService billOfLadingService;
	@Autowired
	private PurchaseOrderService purchaseOrderService;

	private PurchaseOrder order = new PurchaseOrder();
	private Integer containersAmount;

	@Override
	public void saveObject() {
		if (this.order.getId() == null) {
			this.order.setVoyage(getSelectedVoyage());
		}
		getBaseService().save(this.order);
		refreshObjects();
	}
	
	@Override
	public void refreshObjects() {
		Voyage voyage = getSelectedVoyage();
		if (voyage != null) {
			List<PurchaseOrder> orders = purchaseOrderService.getOrdersByVoyage(voyage.getId());
			super.setObjects(orders);
			if (orders != null && orders.size() > 0) {
				this.order = orders.get(0);
			}
		}
	}

	@Override
	public void newObject() {
		super.newObject();
		this.order = new PurchaseOrder();
	}

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBriefInfo() {
		return order.getNumber();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return this.order != null ? this.order : new PurchaseOrder();
	}


	public List<Container> getContainers() {
		List<Container> result = null;
		if (this.order == null || this.order.getId() == null) {
			return result;
		} else {
			result = containerService.getContainersByPurchaseOrder(this.order.getId()); 
			this.containersAmount = result.size();
			return result;
		}
	}
	
	public List<Invoice> getInvoices() {
		if (this.order == null || this.order.getId() == null) {
			return null;
		} else {
			return invoiceService.getInvoicesByPurchaseOrder(this.order.getId());
		}
	}
	
	public List<BillOfLading> getBillOfLadings() {
		if (this.order == null || this.order.getId() == null) {
			return null;
		} else {
			return billOfLadingService.getBillsByPurchaseOrder(this.order.getId());
		}
	}
	
	public List<Voyage> getVoyages() {
		if (this.order == null || this.order.getId() == null) {
			return null;
		} else {
			return voyageService.getVoyagesByPurchaseOrder(this.order.getId());
		}
	}

	public PurchaseOrder getOrder() {
		return order;
	}

	public void setOrder(PurchaseOrder order) {
		this.order = order;
	}

	public Integer getContainersAmount() {
		return containersAmount;
	}
}
