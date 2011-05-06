package ru.integral.docum.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShipArrival implements Serializable {
	private static final long serialVersionUID = -85152836204987579L;

	public ShipArrival(String shipName, Date shipEta, int totalContainer) {
		setShipName(shipName);
		setShipEta(shipEta);
		containerCount = new ContainerCount();	
		shipContainers = new ArrayList<ShipContainer>();								
		shipContainers.add(new ShipContainer("123748374837","Затаможен","Новороссийск"));
		shipContainers.add(new ShipContainer("423847837438","Проверен","Крымск"));		
		shipContainers.add(new ShipContainer("5545456767","Растаможен","Анапа"));
		shipContainers.add(new ShipContainer("5545456767","Обработан","Харьков"));
	}

	private String shipName;
	private Date shipEta;
	private ContainerCount containerCount;	
	private List<ShipContainer> shipContainers;

	public String getShipName() {
		return shipName;
	}

	public Date getShipEta() {
		return shipEta;
	}

	public void setShipEta(Date shipEta) {
		this.shipEta = shipEta;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public void setContainerCount(ContainerCount containerCount) {
		this.containerCount = containerCount;
	}

	public ContainerCount getContainerCount() {
		return containerCount;
	}

	public void setShipContainers(List<ShipContainer> shipContainers) {
		this.shipContainers = shipContainers;
	}

	public List<ShipContainer> getShipContainers() {
		return shipContainers;
	}
	
	
}