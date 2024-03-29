package com.docum.view.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ActionEvent;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Voyage;
import com.docum.exception.SQLException;
import com.docum.service.ArticleService;
import com.docum.service.BillOfLadingService;
import com.docum.service.CargoService;
import com.docum.service.ContainerService;
import com.docum.service.FileProcessingService;
import com.docum.service.InvoiceService;
import com.docum.service.PurchaseOrderService;
import com.docum.util.AlgoUtil;
import com.docum.util.FacesUtil;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;
import com.docum.view.container.unit.CalibreUnit;
import com.docum.view.container.unit.CargoDefectUnit;
import com.docum.view.container.unit.CargoFeatureUnit;
import com.docum.view.container.unit.CargoPackageUnit;
import com.docum.view.container.unit.CargoUnit;
import com.docum.view.container.unit.InspectionUnit;
import com.docum.view.container.unit.OptionUnit;
import com.docum.view.dict.BaseView;
import com.docum.view.param.FlashParamKeys;
import com.docum.view.wrapper.ContainerPresentation;
import com.docum.view.wrapper.ContainerTransformer;
import com.docum.view.wrapper.VoyagePresentation;
import com.docum.view.wrapper.VoyageTransformer;

@Controller("containerBean")
@Scope("session")
public class ContainerView extends BaseView implements Serializable, DialogActionHandler,
		ContainerHolder {
	private static final long serialVersionUID = 3476513399265370923L;
	private static final String sign = "Контейнер";
	private static final int MAX_LIST_SIZE = 10;
	private Container container;
	private ArrayList<ContainerPresentation> containers;
	private ContainerDlgView containerDlg;
	private boolean reloadContainer = true;
	private VoyagePresentation selectedVoyage;

	private Set<ContainerChangeListener> containerChangeListeners = new HashSet<ContainerChangeListener>();

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
	@Autowired
	private CargoService cargoService;
	@Autowired
	private FileProcessingService fileService;

	private CargoUnit actualCargoUnit = new CargoUnit(this);
	private CargoUnit declaredCargoUnit = new CargoUnit(this);

	private CargoUnit dlgCargoUnit;
	private CargoFeatureUnit dlgFeatureUnit;
	private CargoPackageUnit dlgPackageUnit;
	private CalibreUnit dlgCalibreUnit;
	private CargoDefectUnit dlgDefectUnit;
	private OptionUnit dlgOptionUnit;

	private InspectionUnit inspectionUnit = new InspectionUnit(this);

	public CalibreUnit getDlgCalibreUnit() {
		return dlgCalibreUnit;
	}

	public void setDlgCalibreUnit(CalibreUnit dlgCalibreUnit) {
		this.dlgCalibreUnit = dlgCalibreUnit;
	}

	public CargoPackageUnit getDlgPackageUnit() {
		return dlgPackageUnit;
	}

	public void setDlgPackageUnit(CargoPackageUnit dlgPackageUnit) {
		this.dlgPackageUnit = dlgPackageUnit;
	}

	public CargoFeatureUnit getDlgFeatureUnit() {
		return dlgFeatureUnit;
	}

	public void setDlgFeatureUnit(CargoFeatureUnit dlgFeatureUnit) {
		this.dlgFeatureUnit = dlgFeatureUnit;
	}

	public CargoUnit getDlgCargoUnit() {
		return dlgCargoUnit;
	}

	public void setDlgCargoUnit(CargoUnit dlgCargoUnit) {
		this.dlgCargoUnit = dlgCargoUnit;
	}

	public CargoDefectUnit getDlgDefectUnit() {
		return dlgDefectUnit;
	}

	public void setDlgDefectUnit(CargoDefectUnit dlgDefectUnit) {
		this.dlgDefectUnit = dlgDefectUnit;
	}

	public OptionUnit getDlgOptionUnit() {
		return dlgOptionUnit;
	}

	public void setDlgOptionUnit(OptionUnit dlgOptionUnit) {
		this.dlgOptionUnit = dlgOptionUnit;
	}

	public CargoUnit getActualCargoUnit() {
		return getCargoUnit(true, actualCargoUnit);
	}

	public CargoUnit getDeclaredCargoUnit() {
		return getCargoUnit(false, declaredCargoUnit);
	}

	public CargoUnit getCargoUnit(Boolean isActual, CargoUnit cargoUnit) {
		if (container != null) {
			ContainerContext context = new ContainerContext();
			context.setArticleService(articleService);
			context.setCargoService(cargoService);
			context.setBaseService(getBaseService());
			context.setFileService(fileService);
			cargoUnit.setContext(context);
			if (isActual) {
				cargoUnit.setCargoCondition(container.getActualCondition());
			} else {
				cargoUnit.setCargoCondition(container.getDeclaredCondition());
			}
		}
		return cargoUnit;
	}

	public InspectionUnit getInspectionUnit() {
		if (container != null) {
			ContainerContext context = new ContainerContext();
			context.setContainer(container);
			context.setBaseService(getBaseService());
			context.setFileService(fileService);
			inspectionUnit.setContext(context);
		}
		return inspectionUnit;
	}

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBriefInfo() {
		return container != null ? container.getNumber() : null;
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return container != null ? container : new Container();
	}

	@Override
	public void refreshObjects() {
		Collection<Container> c = containerService
				.getContainersByVoyage(selectedVoyage != null ? selectedVoyage.getVoyage().getId()
						: null);
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
		if (containerPresentation == null || containerPresentation.getContainer() == null) {
			this.container = null;
			return;
		}
		// reloadContainer = !containerPresentation.getContainer().equals(
		// this.container);
		// if (reloadContainer) {
		// this.container = containerPresentation.getContainer();
		// }
		loadContainer(containerPresentation.getContainer());
	}

	private void loadContainer(Container container) {
		this.container = (container != null && container.getId() != null) ? containerService
				.getContainer(container.getId()) : container;
	}

	public List<VoyagePresentation> getVoyages() {
		HashMap<String, SortOrderEnum> sortFields = new HashMap<String, SortOrderEnum>();
		sortFields.put("arrivalDate", SortOrderEnum.DESC);
		List<Voyage> voyages = (List<Voyage>) getBaseService().getAll(Voyage.class, sortFields);
		List<VoyagePresentation> result = new ArrayList<VoyagePresentation>(voyages.size());
		AlgoUtil.transform(result, voyages, new VoyageTransformer(false));
		return result;
	}

	public List<VoyagePresentation> voyageAutocomplete(Object suggest) throws Exception {
		// TODO Вынести в презентейшн
		String pref = (String) suggest;
		ArrayList<VoyagePresentation> result = new ArrayList<VoyagePresentation>();

		for (VoyagePresentation voyage : getVoyages()) {
			if ((voyage.getVoyageInfo() != null && voyage.getVoyageInfo().toLowerCase()
					.indexOf(pref.toLowerCase()) >= 0)
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
		setSelectedVoyage((VoyagePresentation) event.getObject());
		refreshObjects();
		setContainer(new ContainerPresentation(null));
	}

	public String getContainersTitle() {
		return selectedVoyage != null ? selectedVoyage.getVoyageInfo() : "Выберите судозаход...";

	}

	public Boolean getIsNullContainer() {
		return container == null ? true : false;
	}

	public void loadPage() {
		Container container = (Container) FacesUtil.getFlashParam(FlashParamKeys.CONTAINER);
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
		containerDlg = new ContainerDlgView(container, invoiceService, orderService, billService,
				getBaseService());
		containerDlg.addHandler(this);
	}

	@Override
	public void handleAction(AbstractDlgView dialog, DialogActionEnum action) {
		if (dialog instanceof ContainerDlgView) {
			ContainerDlgView d = (ContainerDlgView) dialog;
			if (DialogActionEnum.ACCEPT.equals(action)) {
				container = containerService.saveContainer(d.getContainer());
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
		try {
			containerService.saveContainer(container);
			resreshContainers();
			for (ContainerChangeListener listener : containerChangeListeners) {
				listener.containerChanged(container);
			}
		} catch (ConstraintViolationException e) {
			SQLException.integrityViolation(e);
		} catch (Exception e) {
			SQLException.commonException(e);
		}
	}
	
	
	@Override
	public void addContainerChangeListener(ContainerChangeListener listener) {
		containerChangeListeners.add(listener);
	}

	@Override
	public void removeContainerChangeListener(ContainerChangeListener listener) {
		containerChangeListeners.remove(listener);
	}

	public String getContainerNumber() {
		return container != null ? container.getNumber() : null;
	}

	public String getCargoName() {
		return dlgCargoUnit != null ? dlgCargoUnit.getCargoName() : null;
	}

}
