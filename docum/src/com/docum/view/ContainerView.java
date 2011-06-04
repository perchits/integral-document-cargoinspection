package com.docum.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Voyage;
import com.docum.service.ContainerService;
import com.docum.util.AlgoUtil;
import com.docum.view.dict.BaseView;
import com.docum.view.wrapper.ContainerPresentation;
import com.docum.view.wrapper.ContainerTransformer;
import com.docum.view.wrapper.VoyagePresentation;
import com.docum.view.wrapper.VoyageTransformer;

@Controller("containerBean")
@Scope("session")
public class ContainerView extends BaseView implements Serializable {

	private static final long serialVersionUID = 3476513399265370923L;
	private static final String sign = "Контейнер";
	private static final int MAX_LIST_SIZE = 10;
	private Container container = new Container();
	private Container lazyContainer = new Container();
	private VoyagePresentation selectedVoyage;
	@Autowired
	private ContainerService containerService;
	private ArrayList<ContainerPresentation> containers;

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return container.getNumber();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return container != null ? container : new Container();
	}

	@Override
	public void refreshObjects() {
		super.refreshObjects();		
		Collection<Container> c = containerService.getContainersByVoyage(selectedVoyage != null ? selectedVoyage.getVoyage().getId() : null);		
		containers = new ArrayList<ContainerPresentation>(c.size());
		AlgoUtil.transform(containers, c, new ContainerTransformer());
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public Container getContainer() {
		return container;
	}

	public ArrayList<ContainerPresentation> getContainers() {
		if (containers == null) {
			refreshObjects();
		}
		return containers;
	}

	public ContainerPresentation getContainerPresentation() {
		return container != null ? new ContainerPresentation(container) : null;
	}

	public void setContainerPresentation(
			ContainerPresentation containerPresentation) {
		container = containerPresentation != null ? containerPresentation
				.getContainer() : null;
		setLazyContainer(container);
	}

	public void setLazyContainer(Container lazyContainer) {
		this.lazyContainer = lazyContainer != null ? getBaseService()
				.getObject(Container.class, lazyContainer.getId()) : null;
	}

	public Container getLazyContainer() {
		return lazyContainer;
	}

	public List<VoyagePresentation> getVoyages() {	
		HashMap<String, SortOrderEnum> sortFields = new HashMap<String, SortOrderEnum>();
		sortFields.put("arrivalDate", SortOrderEnum.DESC);		
		List<Voyage> voyages = (List<Voyage>) getBaseService().getAll(Voyage.class, sortFields);
		List<VoyagePresentation> result = new ArrayList<VoyagePresentation>(
				voyages.size());
		AlgoUtil.transform(result, voyages, new VoyageTransformer());
		return result;
	}

	public List<VoyagePresentation> voyageAutocomplete(Object suggest)
			throws Exception {
		String pref = (String) suggest;
		ArrayList<VoyagePresentation> result = new ArrayList<VoyagePresentation>();

		for (VoyagePresentation voyage : getVoyages()) {
			if ((voyage.getVoyageInfo() != null && voyage.getVoyageInfo()
					.toLowerCase().indexOf(pref.toLowerCase()) >= 0)
					|| "".equals(pref)) {
				result.add(voyage);
				if (result.size() >= MAX_LIST_SIZE)
					break;
			}
		}
		return result;
	}

	public void setSelectedVoyage(VoyagePresentation selectedVoyage) {
		this.selectedVoyage = selectedVoyage;
	}

	public VoyagePresentation getSelectedVoyage() {
		return selectedVoyage;
	}
	
	public void voyageSelect(SelectEvent event) {
		refreshObjects();
	}

}
