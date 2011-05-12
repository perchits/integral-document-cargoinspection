package com.docum.view.handbook;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.docum.persistence.common.Voyage;
import com.docum.service.VoyageService;

@ManagedBean(name = "voyage")
@ViewScoped
public class VoyageView implements Serializable {
	private static final long serialVersionUID = 5855731783922631397L;

	@ManagedProperty(value = "#{voyageService}")
	private VoyageService voyageService;

	private List<Voyage> voyages;
	
	public List<Voyage> getAllVoyages() {
		if(voyages == null) {
			voyages = voyageService.getAllVoyages();
		}
		return voyages;
	}

	public void setVoyageService(VoyageService voyageService) {
		this.voyageService = voyageService;
	}
}
