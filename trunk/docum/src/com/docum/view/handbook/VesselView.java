package com.docum.view.handbook;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.docum.persistence.IdentifiedEntity;
import com.docum.persistence.common.Vessel;

@ManagedBean(name = "vesselBean")
@SessionScoped
public class VesselView extends BaseView implements Serializable {
	private static final long serialVersionUID = -7018249724051865904L;

	private static final String sing = "Судно";
	private Vessel vessel = new Vessel();
	
	@Override
	public String getSing() {
		return sing;
	}

	@Override
	public String getBase() {
		return this.vessel.getName();
	}
	
	@Override
	public void newObject() {
		super.newObject();
		this.vessel = new Vessel();
		setTitle("Новое " + getSing().toLowerCase());
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return this.vessel != null ? this.vessel : new Vessel();
	}

	public Vessel getVessel() {
		return vessel;
	}

	public void setVessel(Vessel vessel) {
		this.vessel = vessel;
	}

}
