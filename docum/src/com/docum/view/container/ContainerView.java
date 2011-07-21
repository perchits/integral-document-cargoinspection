package com.docum.view.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Voyage;
import com.docum.service.ArticleService;
import com.docum.service.BillOfLadingService;
import com.docum.service.ContainerService;
import com.docum.service.InvoiceService;
import com.docum.service.PurchaseOrderService;
import com.docum.util.AlgoUtil;
import com.docum.util.FacesUtil;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;
import com.docum.view.container.unit.CalibreUnit;
import com.docum.view.container.unit.CargoFeatureUnit;
import com.docum.view.container.unit.CargoPackageUnit;
import com.docum.view.container.unit.CargoUnit;
import com.docum.view.dict.BaseView;
import com.docum.view.param.FlashParamKeys;
import com.docum.view.wrapper.ContainerPresentation;
import com.docum.view.wrapper.ContainerTransformer;
import com.docum.view.wrapper.VoyagePresentation;
import com.docum.view.wrapper.VoyageTransformer;

@Controller("containerBean")
@Scope("session")
public class ContainerView extends BaseView implements Serializable,
		DialogActionHandler, ContainerHolder {
	private static final long serialVersionUID = 3476513399265370923L;
	private static final String sign = "Контейнер";
	private static final int MAX_LIST_SIZE = 10;
	private Container container;
	private ArrayList<ContainerPresentation> containers;
	private ContainerDlgView containerDlg;
	private boolean reloadContainer = true;
	private VoyagePresentation selectedVoyage;

	@Autowired
	private ContainerService containerService;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private PurchaseOrderService orderService;
	@Autowired
	private BillOfLadingService billService;
	@Autowired
	private ArticleService articleService;

	private CargoUnit cargoUnit = new CargoUnit(this);

	public CargoUnit getCargoUnit() {
		ContainerContext context = new ContainerContext();
		context.setContainer(container);
		context.setArticleService(articleService);
		context.setBaseService(getBaseService());
		cargoUnit.setContext(context);
		return cargoUnit;
	}

	public CargoFeatureUnit getCargoFeatureUnit() {
		return getCargoUnit().getCargoFeatureUnit();
	}

	public CargoPackageUnit getCargoPackageUnit() {
		return getCargoUnit().getCargoPackageUnit();
	}

	public CalibreUnit getCalibreUnit() {
		return getCargoPackageUnit().getCalibreUnit();
	}

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return container != null ? container.getNumber() : null;
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return container != null ? container : new Container();
	}

	@Override
	public void refreshObjects() {
		Collection<Container> c = containerService
				.getContainersByVoyage(selectedVoyage != null ? selectedVoyage
						.getVoyage().getId() : null);
		containers = new ArrayList<ContainerPresentation>(c.size());
		AlgoUtil.transform(containers, c, new ContainerTransformer());
	}

	public ArrayList<ContainerPresentation> getContainers() {
		if (containers == null) {
			refreshObjects();
		}
		return containers;
	}

	public ContainerPresentation getContainer() {
		if (container == null) {
			return null;
		} else if (reloadContainer) {
			loadContainer(container);
			reloadContainer = false;
		}
		return new ContainerPresentation(container);
	}

	public void setContainer(ContainerPresentation containerPresentation) {
		if (containerPresentation == null
				|| containerPresentation.getContainer() == null) {
			this.container = null;
			return;
		}
		reloadContainer = !containerPresentation.getContainer().equals(
				this.container);
		if (reloadContainer) {
			this.container = containerPresentation.getContainer();
		}
	}

	private void loadContainer(Container container) {
		this.container = (container != null && container.getId() != null) ? containerService
				.getObject(Container.class, container.getId()) : container;
	}

	public List<VoyagePresentation> getVoyages() {
		HashMap<String, SortOrderEnum> sortFields = new HashMap<String, SortOrderEnum>();
		sortFields.put("arrivalDate", SortOrderEnum.DESC);
		List<Voyage> voyages = (List<Voyage>) getBaseService().getAll(
				Voyage.class, sortFields);
		List<VoyagePresentation> result = new ArrayList<VoyagePresentation>(
				voyages.size());
		AlgoUtil.transform(result, voyages, new VoyageTransformer(false));
		return result;
	}

	public List<VoyagePresentation> voyageAutocomplete(Object suggest)
			throws Exception {
		// TODO Вынести в презентейшн
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
		setContainer(new ContainerPresentation(null));
	}

	public String getContainersTitle() {
		return selectedVoyage != null ? 
				selectedVoyage.getVoyageInfo()
				: "Выберите не судозаход...";

	}

	public void loadPage() {
		Container container = (Container) FacesUtil
				.getFlashParam(FlashParamKeys.CONTAINER);
		if (container != null) {
			this.container = container;
			loadContainer(this.container);
			selectedVoyage = new VoyagePresentation(this.container.getVoyage());
			refreshObjects();
		}
	}

	@Override
	public void newObject() {
		super.newObject();
		if (selectedVoyage == null) {
			String message = "Добавление контейнера невозможно пока не выбран судозаход!";
			showErrorMessage(message);
			addCallbackParam("dontShow", true);
		} else {
			Container container = new Container();
			container.setVoyage(selectedVoyage.getVoyage());
			prepareContainerDialog(container);
		}
	}

	@Override
	public void editObject(ActionEvent actionEvent) {
		super.editObject(actionEvent);
		if (selectedVoyage != null) {
			prepareContainerDialog(container);
		}
	}

	public void deleteObject() {
		super.deleteObject();
		setContainer(null);
	}

	public ContainerDlgView getContainerDlg() {
		return containerDlg;
	}

	public void setContainerDlg(ContainerDlgView containerDlg) {
		this.containerDlg = containerDlg;
	}

	private void prepareContainerDialog(Container container) {
		containerDlg = new ContainerDlgView(container, invoiceService,
				orderService, billService, getBaseService());
		containerDlg.addHandler(this);
	}

	@Override
	public void handleAction(AbstractDlgView dialog, DialogActionEnum action) {
		if (dialog instanceof ContainerDlgView) {
			ContainerDlgView d = (ContainerDlgView) dialog;
			if (DialogActionEnum.ACCEPT.equals(action)) {
				container = containerService.save(d.getContainer());
				invoiceService.save(d.getInvoices(container));
				orderService.save(d.getOrders(container));
				billService.save(d.getBills(container));
				resreshContainers();
			}
		}
		
	}

	private void resreshContainers() {
		loadContainer(container);
		ContainerPresentation cp = new ContainerPresentation(container);
		int index = containers.indexOf(cp);
		if (index != -1) {
			containers.remove(index);
			containers.add(index, cp);
		} else {
			containers.add(cp);
		}
	}

	public void updateInspectionBrief() {

	}

	@Override
	public void saveContainer() {
		containerService.save(container);
	}

	@Override
	public void resreshContainerList() {
		resreshContainers();		
	}
}
