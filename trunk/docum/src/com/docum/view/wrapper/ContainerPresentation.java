package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.EnumMap;

import com.docum.domain.ContainerStateEnum;
import com.docum.domain.po.common.Container;
import com.docum.util.ListHandler;

public class ContainerPresentation implements Serializable {
	private static final long serialVersionUID = 3960028459943599183L;
	private Container container;
	private String invoiceInLine;
	private String orderInLine;
	private String billOfLadingInLine;
	private String cargoInLine;
	private static EnumMap<ContainerStateEnum, String> containerStateMap = new EnumMap<ContainerStateEnum, String>(
			ContainerStateEnum.class);

	static {
		containerStateMap.put(ContainerStateEnum.BEFORE_CUSTOMS,
				"before-customs-color");
		containerStateMap.put(ContainerStateEnum.AFTER_CUSTOMS,
				"after-customs-color");
		containerStateMap.put(ContainerStateEnum.HANDLED, "handled-color");
		containerStateMap.put(ContainerStateEnum.REPORTED, "reported-color");
		containerStateMap.put(ContainerStateEnum.ABANDONED, "abandoned-color");
	}

	public ContainerPresentation(Container container) {
		this.container = container;
		setInvoiceInLine(getInvoices());
		setOrderInLine(getOrders());
		setBillOfLadingInLine(getBillOfLaddinds());
		setCargoInLine(getCargoes());
	}

	public Container getContainer() {
		return container;
	}

	public String getNumber() {
		return container.getNumber();
	}

	public String getCity() {
		return container.getCity().getName();
	}

	public String getState() {
		return container.getState().getName();
	}

	public String getColor() {
		return containerStateMap.get(container.getState());
	}

	private String getInvoices() {
		return container != null ? ListHandler.join(container.getInvoices(),
				", ") : null;
	}

	private String getOrders() {
		return container != null ? ListHandler
				.join(container.getOrders(), ", ") : null;
	}

	private String getBillOfLaddinds() {
		return container != null ? ListHandler.join(
				container.getBillOfLadings(), ", ") : null;
	}

	private String getCargoes() {
		return container != null ? ListHandler.join(container.getCargoes(),
				", ") : null;
	}

	public void setInvoiceInLine(String invoiceInLine) {
		this.invoiceInLine = invoiceInLine;
	}

	public String getInvoiceInLine() {
		return invoiceInLine;
	}

	public void setOrderInLine(String orderInLine) {
		this.orderInLine = orderInLine;
	}

	public String getOrderInLine() {
		return orderInLine;
	}

	public void setBillOfLadingInLine(String billOfLadingInLine) {
		this.billOfLadingInLine = billOfLadingInLine;
	}

	public String getBillOfLadingInLine() {
		return billOfLadingInLine;
	}

	public void setCargoInLine(String cargoInLine) {
		this.cargoInLine = cargoInLine;
	}

	public String getCargoInLine() {
		return cargoInLine;
	}

	public String getVoyage() {
		return container != null ? VoyagePresentation.joinVoyageInfo(container
				.getVoyage()) : null;
	}
}
