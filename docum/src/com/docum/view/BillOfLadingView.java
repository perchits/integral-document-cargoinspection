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
import com.docum.service.ContainerService;
import com.docum.service.InvoiceService;
import com.docum.service.PurchaseOrderService;
import com.docum.service.VoyageService;
import com.docum.view.dict.BaseView;

@Controller("billOfLadingBean")
@Scope("session")
public class BillOfLadingView extends BaseView {
	private static final long serialVersionUID = 3982196636131340370L;

	private static final String sign = "Коносамент";

	@Autowired
	private ContainerService containerService;
	@Autowired
	private VoyageService voyageService;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private PurchaseOrderService purchaseOrderService;

	private BillOfLading billOfLading = new BillOfLading(); 

	@Override
	public void saveObject() {
		if (this.billOfLading.getId() != null) {
			getBaseService().updateObject(this.billOfLading);
		} else {
			getBaseService().saveObject(this.billOfLading);
		}
		refreshObjects();
	}

	@Override
	public void newObject() {
		super.newObject();
		this.billOfLading = new BillOfLading();
	}

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return billOfLading.getNumber();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return this.billOfLading != null ? this.billOfLading : new BillOfLading();
	}


	public List<Container> getContainers() {
		if (this.billOfLading == null || this.billOfLading.getId() == null) {
			return null;
		} else {
			return containerService.getContainersByBillOfLading(this.billOfLading.getId());
		}
	}
	
	public List<Invoice> getInvoices() {
		if (this.billOfLading == null || this.billOfLading.getId() == null) {
			return null;
		} else {
			return invoiceService.getInvoicesByBillOfLading(this.billOfLading.getId());
		}
	}
	
	public List<PurchaseOrder> getOrders() {
		if (this.billOfLading == null || this.billOfLading.getId() == null) {
			return null;
		} else {
			return purchaseOrderService.getOrdersByBillOfLading(this.billOfLading.getId());
		}
	}
	
	public List<Voyage> getVoyages() {
		if (this.billOfLading == null || this.billOfLading.getId() == null) {
			return null;
		} else {
			return voyageService.getVoyagesByBillOfLading(this.billOfLading.getId());
		}
	}

	public BillOfLading getBillOfLading() {
		return billOfLading;
	}

	public void setBillOfLading(BillOfLading billOfLading) {
		this.billOfLading = billOfLading;
	}
}
