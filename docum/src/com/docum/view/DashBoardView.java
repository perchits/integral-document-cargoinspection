package com.docum.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.docum.persistence.common.Voyage;
import com.docum.service.VoyageService;

@ManagedBean(name = "dashboardView")
@ViewScoped
public class DashBoardView implements Serializable {
	private static final long serialVersionUID = 8925725427524960747L;
	
	@ManagedProperty(value="#{voyageService}") 
	private VoyageService voyageService;
	public void setVoyageService(VoyageService voyageService) {
		this.voyageService = voyageService;
	}
	
	private ArrayList<Voyage> finishedVoyages;
	private ArrayList<Voyage> unfinishedVoyages;

	public Collection<Voyage> getFinishedVoyages() {
		if(finishedVoyages == null){
			finishedVoyages = new ArrayList<Voyage>(voyageService.getFinishedVoyages());
		}
		return finishedVoyages;
	}

	public Collection<Voyage> getUnfinishedVoyages() {
		if(unfinishedVoyages == null){
			unfinishedVoyages = new ArrayList<Voyage>(voyageService.getUnfinishedVoyages());
		}
		return unfinishedVoyages;
	}
	
	/* Тестовые функции */

	public void showError(ActionEvent actionEvent) throws Exception {
		throw new Exception("Не верно!");
	}

	public void showErrorDiv(ActionEvent actionEvent){
		int i = 10 / 0;
	}

	public void goWithError() {
		Integer n = 10 / 0;
		sortByOrderNo();	
	}
	
	public void goAction() {
		sortByOrderNo();
		System.out.println("Sorted...");
	}
	
	/* конец тестовые функции */

	public void sortByOrderNo() {

		// ascending order
		Collections.sort(finishedVoyages, new Comparator<Voyage>() {

			@Override
			public int compare(Voyage o1, Voyage o2) {

				return o1.getVessel().getName().compareTo(o2.getVessel().getName());

			}

		});

	}

}