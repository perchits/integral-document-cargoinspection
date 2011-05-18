package com.docum.view.handbook;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.docum.persistence.IdentifiedEntity;
import com.docum.persistence.common.Vessel;
import com.docum.service.VesselService;
import com.docum.view.handbook.dialog.BaseDialog;

@ManagedBean(name = "vesselBean")
@SessionScoped
public class VesselView extends BaseDialog implements Serializable {
	private static final long serialVersionUID = -7018249724051865904L;

	@ManagedProperty(value = "#{vesselService}")
	private VesselService vesselService;

	private static final String sing = "Судно";
	private List<Vessel> vessels;
	private Vessel vessel = new Vessel();
	
	public List<Vessel> getAllVessels() {
		if(vessels == null) {
			vessels = vesselService.getAllVessels();
		}
		return vessels;
	}

	public void setVesselService(VesselService vesselService) {
		this.vesselService = vesselService;
	}

	@Override
	public String getSing() {
		return sing;
	}

	@Override
	public String getBase() {
		return this.vessel.getName();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return this.vessel;
	}

	public Vessel getVessel() {
		return vessel;
	}

	public void setVessel(Vessel vessel) {
		this.vessel = vessel;
	}
}
