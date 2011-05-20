package com.docum.view.handbook;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Vessel;

@ManagedBean(name = "vesselBean")
@SessionScoped
public class VesselView extends BaseView implements Serializable {
	private static final long serialVersionUID = -7018249724051865904L;

	private static final String sign = "Судно";
	private Vessel vessel = new Vessel();
	
	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return this.vessel.getName();
	}
	
	@Override
	public void newObject() {
		super.newObject();
		this.vessel = new Vessel();
		setTitle("Новое " + getSign().toLowerCase());
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
