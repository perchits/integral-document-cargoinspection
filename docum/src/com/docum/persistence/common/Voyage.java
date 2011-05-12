package com.docum.persistence.common;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.persistence.IdentifiedEntity;

@Entity
public class Voyage extends IdentifiedEntity{
	private static final long serialVersionUID = 8878667599057177012L;
	
	@ManyToOne
	private Vessel vessel;
	private String number;
	private Date arrivalDate;
	
	public Voyage(){
		super();
	}

	public Voyage(Vessel vessel, String number, Date arrivalDate) {
		super();
		this.vessel = vessel;
		this.number = number;
		this.arrivalDate = arrivalDate;
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
