package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumMap;

import com.docum.domain.ContainerStateEnum;
import com.docum.domain.po.common.Container;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;
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
		return container != null ? container.getNumber() : null;
	}

	public String getCity() {
		return (container != null && container.getCity() != null) ? container.getCity().getName() : null;
	}

	public String getState() {
		return container != null ? container.getState().getName() : null;
	}

	public String getLoadingPort() {
		return (container != null && container.getLoadingPort() != null) ? container.getLoadingPort().getName() : null;		
	}

	public Date getLoadingDate() {
		return container != null ? container.getLoadingDate() : null;
	}

	public String getDischargePort() {
		return (container != null && container.getDischargePort() != null) ? container.getDischargePort().getName() : null;		
	}

	public Date getDischargeDate() {
		return container != null ? container.getDischargeDate() : null;
	}
	
	public String getColor() {
		return container != null ? containerStateMap.get(container.getState()) : "";
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

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof ContainerPresentation)) {
			return false;
		}

		return EqualsHelper.equals(container, ((ContainerPresentation) obj).container);
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(container);
	}
}
