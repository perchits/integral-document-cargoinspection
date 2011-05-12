package com.docum.view.handbook;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.docum.persistence.common.Vessel;
import com.docum.service.VesselService;

@ManagedBean(name = "vessel")
@ViewScoped
public class VesselView implements Serializable {
	private static final long serialVersionUID = -7018249724051865904L;

	@ManagedProperty(value = "#{vesselService}")
	private VesselService vesselService;

	private List<Vessel> vessels;
	
	public List<Vessel> getAllVessels() {
		if(vessels == null) {
			vessels = vesselService.getAllVessels();
		}
		return vessels;
	}

	public void setVesselService(VesselService vesselService) {
		this.vesselService = vesselService;
	}
}
