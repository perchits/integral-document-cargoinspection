package ru.integral.docum.domain;

import java.io.Serializable;

public class ShipContainer implements Serializable{
	private static final long serialVersionUID = 4840791245873178072L;
	private String number;
	private String status;
	private String town;
	public ShipContainer(String number, String status, String town){
		this.number = number;
		this.status = status;
		this.town = town;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getNumber() {
		return number;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getTown() {
		return town;
	}
	

}
