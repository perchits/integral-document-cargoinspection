package com.docum.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Invoice;
import com.docum.service.ContainerService;
import com.docum.service.VoyageService;
import com.docum.view.dict.BaseView;

@Controller("invoiceBean")
@Scope("session")
public class InvoiceView extends BaseView {
	private static final long serialVersionUID = 7608376610068348483L;

	private static final String sign = "Инвойс";

	@Autowired
	private ContainerService containerService;
	@Autowired
	private VoyageService voyageService;

	private Invoice invoice = new Invoice(); 

	@Override
	public void saveObject() {
		if (this.invoice.getId() != null) {
			getBaseService().updateObject(this.invoice);
		} else {
			getBaseService().saveObject(this.invoice);
		}
		refreshObjects();
	}

	@Override
	public void newObject() {
		super.newObject();
		this.invoice = new Invoice();
	}

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return invoice.getNumber();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return this.invoice != null ? this.invoice : new Invoice();
	}


	public List<Container> getContainers() {
		return null;
		/*if (this.invoice == null) {
			return Collections.emptyList();
		} else {
			return containerService.getContainersByVoyage(this.invoice.getId());
		}*/
	}
	
	public List<Container> getVoyages() {
		return null;
		/*if (this.invoice == null) {
			return Collections.emptyList();
		} else {
			return containerService.getContainersByVoyage(this.invoice.getId());
		}*/
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

}
