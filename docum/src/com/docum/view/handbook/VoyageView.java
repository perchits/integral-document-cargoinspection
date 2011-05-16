package com.docum.view.handbook;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.docum.persistence.common.Vessel;
import com.docum.persistence.common.Voyage;
import com.docum.service.VesselService;
import com.docum.service.VoyageService;
import com.docum.view.handbook.dialog.BaseDialog;

@ManagedBean(name = "voyageView")
@SessionScoped
public class VoyageView extends BaseDialog implements Serializable {
	private static final long serialVersionUID = 5855731783922631397L;

	@ManagedProperty(value = "#{voyageService}")
	private VoyageService voyageService;
	@ManagedProperty(value = "#{vesselService}")
	private VesselService vesselService;


	private List<Voyage> voyages;
	private List<Vessel> vessels;
	private Voyage voyage = new Voyage();
	
	public List<Voyage> getAllVoyages() {
		if(voyages == null) {
			refreshVoyages();
		}
		return voyages;
	}
	
	public void editVoyage(ActionEvent actionEvent) {
		if (voyage != null) {
			setTitle("Правка: " + voyage.getNumber());
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ошибочка вышла...",
					"Рейс для редактирования не выбран!"));
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.addCallbackParam("isValid", false);
		}
	}
	
	public void deleteVoyage() {
		//TODO implement
	}
	
	public void refreshVoyages() {
		this.voyages = voyageService.getAllVoyages();
	}


	public void newVoyage() {
		this.voyage = new Voyage();
		setTitle("Новый рейс");
	}

	public void saveVoyageAction() {
		if (this.voyage.getId() != null) {
			Voyage voyage = voyageService.getVoyage(this.voyage.getId());
			voyage.copy(this.voyage);
			this.voyage = voyage;
		}
		this.voyage =  voyageService.saveVoyage(this.voyage);
		refreshVoyages();
	}

	public void setVoyageService(VoyageService voyageService) {
		this.voyageService = voyageService;
	}

	public Voyage getVoyage() {
		return voyage;
	}

	public void setVoyage(Voyage voyage) {
		this.voyage = voyage;
	}
	
	public void setVesselService(VesselService vesselService) {
		this.vesselService = vesselService;
	}

	public List<Vessel> getVessels() {
		this.vessels = vesselService.getAllVessels();
		return this.vessels;
	}

	public void setVessels(List<Vessel> vessels) {
		this.vessels = vessels;
	}
}
