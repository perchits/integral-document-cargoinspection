package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class Voyage extends IdentifiedEntity{
	private static final long serialVersionUID = 8878667599057177012L;
	
	@ManyToOne
	private Vessel vessel;
	
	private String number;
	
	private Date arrivalDate;
	
	private boolean finished = false;
	
	@OneToMany(mappedBy = "voyage")
	private List<Container> containers = new ArrayList<Container>();
	
	public Voyage(){
		super();
	}

	public Voyage(Vessel vessel, String number, Date arrivalDate, boolean finished) {
		super();
		this.vessel = vessel;
		this.number = number;
		this.arrivalDate = arrivalDate;
		this.finished = finished;
	}

	public Vessel getVessel() {
		return vessel;
	}

	public void setVessel(Vessel vessel) {
		this.vessel = vessel;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date date) {
		this.arrivalDate = date;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public boolean isFinished() {
		return finished;
	}

	public List<Container> getContainers() {
		return containers;
	}

	public void setContainers(List<Container> containers) {
		this.containers = containers;
	}
}
