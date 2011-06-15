package com.docum.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Voyage;
import com.docum.service.ContainerService;
import com.docum.service.VoyageService;
import com.docum.util.FacesUtil;
import com.docum.view.dict.BaseView;
import com.docum.view.navigation.ViewNavigation;
import com.docum.view.param.FlashParamKeys;

public abstract class AbstractDocumentView extends BaseView {
	private static final long serialVersionUID = -4302208585725432912L;
	
	@Autowired
	protected ContainerService containerService;
	@Autowired
	protected VoyageService voyageService;
	
	private Container selectedContainer;
	private Voyage selectedVoyage;
	
	public String goToContainer() {
		FacesUtil.putFlashParam(FlashParamKeys.CONTAINER, selectedContainer);
		return ViewNavigation.CONTAINERS_VIEW;
	}
	
	@Override
	public List<? extends IdentifiedEntity> getAllObjects() {
		if (selectedVoyage == null) {
			return null;
		}
		
		if (super.getObjects() == null) {
			refreshObjects();
		}
		return super.getObjects();
	}
	
	@Override
	public void newObject() {
		super.newObject();
		if (selectedVoyage == null) {
			String message = "Добавление документа невозможно пока не выбран судозаход!";
			showErrorMessage(message);
			addCallbackParam("dontShow", true);
		}
	}
	
	public Container getSelectedContainer() {
		return selectedContainer;
	}

	public void setSelectedContainer(Container selectedContainer) {
		this.selectedContainer = selectedContainer;
	}

	public Voyage getSelectedVoyage() {
		return selectedVoyage;
	}

	public void setSelectedVoyage(Voyage selectedVoyage) {
		this.selectedVoyage = selectedVoyage;
	}
}
