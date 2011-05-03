package com.docum.persistence.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.docum.persistence.DocumEntity;


@Entity
public class Vessel extends DocumEntity implements Serializable {

	private static final long serialVersionUID = -8435521372184842224L;
	
	private String vesselName;
	private Date vesselTripDate;

	public Vessel() {
	}

	@Column(nullable = false)
	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	@Column(nullable = false)
	public Date getVesselTrip() {
		return vesselTripDate;
	}

	public void setVesselTrip(Date vesselTrip) {
		this.vesselTripDate = vesselTrip;
	}

	
}
