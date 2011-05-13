package com.docum.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.docum.domain.ContainerStateEnum;
import com.docum.persistence.common.Container;
import com.docum.persistence.common.Voyage;
import com.docum.service.VoyageService;
import com.docum.util.AlgoUtil;

@ManagedBean(name = "dashboardView")
@ViewScoped
public class DashBoardView implements Serializable {
	private static final long serialVersionUID = 8925725427524960747L;
	
	@ManagedProperty(value="#{voyageService}") 
	private VoyageService voyageService;
	public void setVoyageService(VoyageService voyageService) {
		this.voyageService = voyageService;
	}
	
	private ArrayList<VoyagePresentation> finishedVoyages;
	private ArrayList<VoyagePresentation> unfinishedVoyages;

	public Collection<VoyagePresentation> getFinishedVoyages() {
		if(finishedVoyages == null){
			Collection<Voyage> voyages = voyageService.getFinishedVoyages();
			finishedVoyages = new ArrayList<VoyagePresentation>(voyages.size());
			AlgoUtil.transform(finishedVoyages, voyages, new VoyageTransformer());
		}
		return finishedVoyages;
	}

	public Collection<VoyagePresentation> getUnfinishedVoyages() {
		if(unfinishedVoyages == null){
			Collection<Voyage> voyages = voyageService.getFinishedVoyages();
			unfinishedVoyages = new ArrayList<VoyagePresentation>(voyages.size());
			AlgoUtil.transform(unfinishedVoyages, voyages, new VoyageTransformer());
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
		Collections.sort(finishedVoyages, new Comparator<VoyagePresentation>() {

			@Override
			public int compare(VoyagePresentation o1, VoyagePresentation o2) {

				return o1.getVessel().compareTo(o2.getVessel());

			}

		});

	}

	public static class VoyagePresentation implements Serializable {
		private static final long serialVersionUID = -3545320838886096320L;
		private Voyage voyage;
		private Map<ContainerStateEnum, Integer> containerStateCounts =
			new HashMap<ContainerStateEnum, Integer>();
		
		VoyagePresentation() {
			containerStateCounts.put(ContainerStateEnum.BEFORE_CUSTOMS, 0);
			containerStateCounts.put(ContainerStateEnum.AFTER_CUSTOMS, 0);
			containerStateCounts.put(ContainerStateEnum.CHECKED, 0);
			containerStateCounts.put(ContainerStateEnum.FINISHED, 0);
		}
		
		public VoyagePresentation(Voyage voyage) {
			this();
			this.voyage = voyage;
			for(Container container : voyage.getContainers()){
				Integer count = containerStateCounts.get(container.getState());
				count++;
				containerStateCounts.put(container.getState(), count);
			}
		}
		
		public String getVessel() {
			return voyage.getVessel().getName();
		}
		
		public String getNumber() {
			return voyage.getNumber();
		}
		
		public Date getArrivalDate() {
			return voyage.getArrivalDate();
		}

		public List<Container> getContainers() {
			return voyage.getContainers();
		}
		
		public Integer getTotalContainerCount(){
			return voyage.getContainers().size();
		}
		
		public Integer getBeforeCustomsContainerCount() {
			return containerStateCounts.get(ContainerStateEnum.BEFORE_CUSTOMS);
		}

		public Integer getAfterCustomsContainerCount() {
			return containerStateCounts.get(ContainerStateEnum.AFTER_CUSTOMS);
		}

		public Integer getCheckedContainerCount() {
			return containerStateCounts.get(ContainerStateEnum.CHECKED);
		}
		
		public Integer getFinishedContainerCount() {
			return containerStateCounts.get(ContainerStateEnum.FINISHED);
		}
	}
	
	private static class VoyageTransformer
		implements AlgoUtil.TransformFunctor<VoyagePresentation, Voyage> {
		@Override
		public VoyagePresentation transform(Voyage voyage) {
			return new VoyagePresentation(voyage);
		}
	}
}