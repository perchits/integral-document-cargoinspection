package com.docum.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.docum.domain.po.common.Container;
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
	
	public String goToContainer() {
		FacesUtil.putFlashParam(FlashParamKeys.CONTAINER, selectedContainer);
		return ViewNavigation.CONTAINERS_VIEW;
	}

	public Container getSelectedContainer() {
		return selectedContainer;
	}

	public void setSelectedContainer(Container selectedContainer) {
		this.selectedContainer = selectedContainer;
	}
}
