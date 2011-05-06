package com.docum.persistence.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.docum.persistence.IdentifiedEntity;

@Entity
public class Vessel extends IdentifiedEntity implements Serializable {

	private static final long serialVersionUID = -8435521372184842224L;

	@Column(nullable = false)
	private String vesselName;

	@Column(nullable = false)
	private Date vesselTripDate;

	public Vessel() {
	}

	public String getVesselName() {
		return vesselName;
	}
	
	public Vessel(String vesselName, Date vesselTripDate) {
		this.vesselName = vesselName;
		this.vesselTripDate = vesselTripDate;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public Date getVesselTripDate() {
		return vesselTripDate;
	}

	public void setVesselTripDate(Date vesselTripDate) {
		this.vesselTripDate = vesselTripDate;
	}

}
