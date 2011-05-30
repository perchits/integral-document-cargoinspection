package com.docum.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.PurchaseOrder;
import com.docum.domain.po.common.Voyage;
import com.docum.service.ContainerService;
import com.docum.service.VoyageService;
import com.docum.view.dict.BaseView;

@Controller("orderBean")
@Scope("session")
public class OrderView extends BaseView {
	private static final long serialVersionUID = 4894206468649180355L;

	private static final String sign = "Заказ";

	@Autowired
	private ContainerService containerService;
	@Autowired
	private VoyageService voyageService;

	private PurchaseOrder order = new PurchaseOrder(); 

	@Override
	public void saveObject() {
		if (this.order.getId() != null) {
			getBaseService().updateObject(this.order);
		} else {
			getBaseService().saveObject(this.order);
		}
		refreshObjects();
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
	public String getBase() {
		return order.getNumber();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return this.order != null ? this.order : new PurchaseOrder();
	}


	public List<Container> getContainers() {
		if (this.order == null || this.order.getId() == null) {
			return null;
		} else {
			return containerService.getContainersByPurchaseOrder(this.order.getId());
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
}
