package com.docum.view.dict;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.BillOfLading;
import com.docum.domain.po.common.Invoice;
import com.docum.domain.po.common.PurchaseOrder;
import com.docum.domain.po.common.Vessel;
import com.docum.domain.po.common.Voyage;
import com.docum.service.BillOfLadingService;
import com.docum.service.InvoiceService;
import com.docum.service.PurchaseOrderService;
import com.docum.util.AlgoUtil;
import com.docum.view.wrapper.ContainerPresentation;
import com.docum.view.wrapper.VoyagePresentation;
import com.docum.view.wrapper.VoyageTransformer;

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
	private BillOfLadingService billOfLadingService;

	private ArrayList<VoyagePresentation> voyages;

	private List<Vessel> vessels;
	private Voyage voyage = new Voyage();

	public Collection<VoyagePresentation> getVoyages() {
		if (voyages == null) {
			refreshObjects();
		}
		return voyages;
	}

	@Override
	public void newObject() {
		super.newObject();
		this.voyage = new Voyage();
	}

	public VoyagePresentation getVoyagePresentation() {
		return this.voyage != null ? new VoyagePresentation(voyage) : null;
	}

	public void setVoyagePresentation(VoyagePresentation voyagePresentation) {
		this.voyage = voyagePresentation != null ? voyagePresentation
				.getVoyage() : null;
	}

	public void setVoyage(Voyage voyage) {
		this.voyage = voyage;
	}

	public Voyage getVoyage() {
		return voyage;
	}

	public List<Vessel> getVessels() {
		if (vessels == null) {
			vessels = getBaseService().getAll(Vessel.class, null);
		}
		return vessels;
	}

	public String getVoyageInfo() {
		return VoyagePresentation.joinVoyageInfo(voyage);
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

	@Override
	public void deleteObject() {
		super.deleteObject();
		this.voyage = null;
	}

	public Vessel getSelectedVessel() {
		return voyage == null ? null : voyage.getVessel();
	}

	public void setSelectedVessel(Vessel selectedVessel) {
		if (voyage != null) {
			voyage.setVessel(selectedVessel);
		}
	}

	@Override
	public void refreshObjects() {
		super.refreshObjects();
		// TODO Посмотреть преобразование типов
		@SuppressWarnings("unchecked")
		Collection<Voyage> v = (Collection<Voyage>) getAllObjects();
		voyages = new ArrayList<VoyagePresentation>(v.size());
		AlgoUtil.transform(voyages, v, new VoyageTransformer());
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

	public List<ContainerPresentation> getContainers() {		
		return getVoyagePresentation() != null ? getVoyagePresentation().getContainers(): null;
	}

	public List<BillOfLading> getBillOfLadings() {
		if (voyage == null) {
			return Collections.emptyList();
		} else {
			return billOfLadingService.getBillsByVoyage(voyage.getId());
		}
	}

}
