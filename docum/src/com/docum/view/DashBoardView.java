package com.docum.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.common.Voyage;
import com.docum.service.VoyageService;
import com.docum.util.AlgoUtil;
import com.docum.util.FacesUtil;
import com.docum.view.navigation.ViewNavigation;
import com.docum.view.param.FlashParamKeys;
import com.docum.view.wrapper.ContainerPresentation;
import com.docum.view.wrapper.VoyagePresentation;
import com.docum.view.wrapper.VoyageTransformer;

@Controller("dashboardView")
@Scope("request")
public class DashBoardView implements Serializable {
	private static final long serialVersionUID = 8925725427524960747L;

	@Autowired
	private VoyageService voyageService;

	private ArrayList<VoyagePresentation> finishedVoyages;
	private ArrayList<VoyagePresentation> unfinishedVoyages;
	private ContainerPresentation selectedContainer;

	public Collection<VoyagePresentation> getFinishedVoyages() {
		if (finishedVoyages == null) {
			Collection<Voyage> voyages = voyageService.getFinishedVoyages();
			finishedVoyages = new ArrayList<VoyagePresentation>(voyages.size());
			AlgoUtil.transform(finishedVoyages, voyages,
					new VoyageTransformer());
		}
		return finishedVoyages;
	}

	public Collection<VoyagePresentation> getUnfinishedVoyages() {
		if (unfinishedVoyages == null) {
			Collection<Voyage> voyages = voyageService.getUnfinishedVoyages();
			unfinishedVoyages = new ArrayList<VoyagePresentation>(
					voyages.size());
			AlgoUtil.transform(unfinishedVoyages, voyages,
					new VoyageTransformer());
		}
		return unfinishedVoyages;
	}

	public void sortByOrderNo() {
		Collections.sort(finishedVoyages, new Comparator<VoyagePresentation>() {
			@Override
			public int compare(VoyagePresentation o1, VoyagePresentation o2) {
				return o1.getVessel().compareTo(o2.getVessel());
			}
		});
	}

	public ContainerPresentation getSelectedContainer() {
		return selectedContainer;
	}

	public void setSelectedContainer(ContainerPresentation selectedContainer) {
		this.selectedContainer = selectedContainer;
	}
	
	public String goToContainer() {
		FacesUtil.putFlashParam(FlashParamKeys.CONTAINER, selectedContainer.getContainer());
		return ViewNavigation.CONTAINERS_VIEW;
	}
}
