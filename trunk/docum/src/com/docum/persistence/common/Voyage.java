package com.docum.persistence.common;

import java.util.Date;

import javax.persistence.Entity;

import com.docum.persistence.IdentifiedEntity;

@Entity
public class Voyage extends IdentifiedEntity{
	private static final long serialVersionUID = 8878667599057177012L;
	
	private Vessel vessel;
	private String number;
	private Date arrivalDate;
	
	public Voyage(){
		super();
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
	
}
