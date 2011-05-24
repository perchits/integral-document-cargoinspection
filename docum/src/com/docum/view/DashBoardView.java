package com.docum.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.ContainerStateEnum;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Voyage;
import com.docum.service.VoyageService;
import com.docum.util.AlgoUtil;

@Controller("dashboardView")
@Scope("request")
public class DashBoardView implements Serializable {
	private static final long serialVersionUID = 8925725427524960747L;
	
	@Autowired
	private VoyageService voyageService;
	
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
			Collection<Voyage> voyages = voyageService.getUnfinishedVoyages();
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
		private List<ContainerPresentation> containers = new ArrayList<ContainerPresentation>();
		private EnumMap<ContainerStateEnum, Integer> containerStateMap =
			new EnumMap<ContainerStateEnum, Integer>(ContainerStateEnum.class);

		VoyagePresentation() {
			for(ContainerStateEnum e : ContainerStateEnum.values()) {
				containerStateMap.put(e, 0);
			}
		}
		
		public VoyagePresentation(Voyage voyage) {
			this();
			this.voyage = voyage;
			for(Container container : voyage.getContainers()){
				Integer count = containerStateMap.get(container.getState());
				count++;
				containerStateMap.put(container.getState(), count);
				this.containers.add(new ContainerPresentation(container));
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

		public List<ContainerPresentation> getContainers() {
			return containers;
		}
		
		public Integer getTotalContainerCount(){
			return containers.size();
		}
		
		public Integer getBeforeCustomsContainerCount() {
			return containerStateMap.get(ContainerStateEnum.BEFORE_CUSTOMS);
		}

		public Integer getAfterCustomsContainerCount() {
			return containerStateMap.get(ContainerStateEnum.AFTER_CUSTOMS)
					+ containerStateMap.get(ContainerStateEnum.HANDLED)
					+ containerStateMap.get(ContainerStateEnum.REPORTED);
		}

		public Integer getUnhandledContainerCount() {
			return containerStateMap.get(ContainerStateEnum.BEFORE_CUSTOMS)
					+ containerStateMap.get(ContainerStateEnum.AFTER_CUSTOMS);
		}
		
		public Integer getHandledContainerCount() {
			return containerStateMap.get(ContainerStateEnum.HANDLED)
					+ containerStateMap.get(ContainerStateEnum.REPORTED);
		}
	}
	
	private static class VoyageTransformer
		implements AlgoUtil.TransformFunctor<VoyagePresentation, Voyage> {
		@Override
		public VoyagePresentation transform(Voyage voyage) {
			return new VoyagePresentation(voyage);
		}
	}
	
	public static class ContainerPresentation implements Serializable {
		private static final long serialVersionUID = 3960028459943599183L;
		private Container container;
		private static EnumMap<ContainerStateEnum, String> containerStateMap =
			new EnumMap<ContainerStateEnum, String>(ContainerStateEnum.class);
		
		static {
			containerStateMap.put(ContainerStateEnum.BEFORE_CUSTOMS, "before-customs-color");
			containerStateMap.put(ContainerStateEnum.AFTER_CUSTOMS, "after-customs-color");
			containerStateMap.put(ContainerStateEnum.HANDLED, "handled-color");
			containerStateMap.put(ContainerStateEnum.REPORTED, "reported-color");
			containerStateMap.put(ContainerStateEnum.ABANDONED, "abandoned-color");
		}

		public ContainerPresentation(Container container) {
			this.container = container;
		}
		
		public String getNumber() {
			return container.getNumber();
		}

		public String getCity() {
			return container.getCity().getName();
		}

		public String getState() {
			return container.getState().getName();
		}

		public String getColor() {
			return containerStateMap.get(container.getState());
		}
	}
}
