package com.docum.view.handbook;

import java.util.Collections;
import java.util.List;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.BillOfLading;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Invoice;
import com.docum.domain.po.common.PurchaseOrder;
import com.docum.domain.po.common.Vessel;
import com.docum.domain.po.common.Voyage;
import com.docum.service.BillOfLadingService;
import com.docum.service.ContainerService;
import com.docum.service.InvoiceService;
import com.docum.service.PurchaseOrderService;

@Controller("voyageBean")
@Scope("session")
public class VoyageView extends BaseView {
	private static final String sign = "Судозаход";
	private static final long serialVersionUID = 5855731783922631397L;

	@Autowired
	private PurchaseOrderService purchaseOrderService;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private ContainerService containerService;
	@Autowired
	private BillOfLadingService billOfLadingService;

	private List<Vessel> vessels;
	private Voyage voyage = new Voyage();

	@Override
	public void saveObject() {
		if (this.voyage.getId() != null) {
			getBaseService().updateObject(this.voyage);
		} else {
			getBaseService().saveObject(this.voyage);
		}
		refreshObjects();
	}

	@Override
	public void newObject() {
		super.newObject();
		this.voyage = new Voyage();
	}

	public Voyage getVoyage() {
		return voyage;
	}

	public void setVoyage(Voyage voyage) {
		this.voyage = voyage;
	}

	public List<Vessel> getVessels() {
		if (vessels == null) {
			vessels = getBaseService().getAll(Vessel.class, null);
		}
		return vessels;
	}

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return voyage.getNumber();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return this.voyage != null ? this.voyage : new Voyage();
	}

	public Vessel getSelectedVessel() {
		return voyage == null ? null : voyage.getVessel();
	}

	public void setSelectedVessel(Vessel selectedVessel) {
		if (voyage != null) {
			voyage.setVessel(selectedVessel);
		}
	}

	public void voyageSelected(SelectEvent event) {
		voyage = (Voyage) event.getObject();
	}

	public List<PurchaseOrder> getOrders() {
		if (voyage == null) {
			return Collections.emptyList();
		} else {
			return purchaseOrderService.getOrdersByVoyage(voyage.getId());
		}
	}

	public List<Invoice> getInvoices() {
		if (voyage == null) {
			return Collections.emptyList();
		} else {
			return invoiceService.getInvoicesByVoyage(voyage.getId());
		}
	}

	public List<Container> getContainers() {
		if (voyage == null) {
			return Collections.emptyList();
		} else {
			return containerService.getContainersByVoyage(voyage.getId());
		}
	}

	public List<BillOfLading> getBillOfLadings() {
		if (voyage == null) {
			return Collections.emptyList();
		} else {
			return billOfLadingService.getBillsByVoyage(voyage.getId());
		}
	}
	
	public String getContainerCount() {
		Integer containers = getContainers().size();
		return containers.toString();
	}

}
