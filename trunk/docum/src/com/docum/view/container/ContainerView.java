package com.docum.view.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoArticleFeature;
import com.docum.domain.po.common.CargoPackage;
import com.docum.domain.po.common.CargoPackageCalibre;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Voyage;
import com.docum.service.ArticleService;
import com.docum.service.BillOfLadingService;
import com.docum.service.ContainerService;
import com.docum.service.InvoiceService;
import com.docum.service.PurchaseOrderService;
import com.docum.service.ReportingService;
import com.docum.util.AlgoUtil;
import com.docum.util.FacesUtil;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;
import com.docum.view.dict.BaseView;
import com.docum.view.param.FlashParamKeys;
import com.docum.view.wrapper.CargoPackagePresentation;
import com.docum.view.wrapper.CargoPresentation;
import com.docum.view.wrapper.CargoTransformer;
import com.docum.view.wrapper.ContainerPresentation;
import com.docum.view.wrapper.ContainerTransformer;
import com.docum.view.wrapper.VoyagePresentation;
import com.docum.view.wrapper.VoyageTransformer;

@Controller("containerBean")
@Scope("session")
public class ContainerView extends BaseView implements Serializable,
		DialogActionHandler {

	private static final long serialVersionUID = 3476513399265370923L;
	private static final String sign = "Контейнер";
	private static final int MAX_LIST_SIZE = 10;
	private Container container;
	private ContainerDlgView containerDlg;
	private CargoDlgView cargoDlg;
	private FeatureDlgView featureDlg;
	private CargoPackageDlgView cargoPackageDlg;
	private CargoPackage cargoPackage;	
	private CalibreDlgView calibreDlg;
	private CargoPackageCalibre calibre;
	
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
	private Cargo cargo;
	private CargoArticleFeature feature;

	@Autowired
	ReportingService reportingService;

	private ArrayList<ContainerPresentation> containers;

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
		return selectedVoyage != null ? String.format(
				"Контейнеры (судозаход: %1$s)", selectedVoyage.getVoyageInfo())
				: "Судозаход не выбран...";

	}

	public Boolean getIsSelected() {
		return container == null ? false : true;
	}

	public List<CargoPresentation> getContainerCargoes() {
		if (container != null) {
			Collection<Cargo> c = container.getDeclaredCondition().getCargoes();
			List<CargoPresentation> result = new ArrayList<CargoPresentation>(
					c.size());
			AlgoUtil.transform(result, c, new CargoTransformer());
			return result;
		} else {
			return null;
		}
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

	public void setCargo(CargoPresentation cargo) {
		this.cargo = cargo.getCargo();
	}

	public void setEditCargo(CargoPresentation cargo) {
		setCargo(cargo);
		prepareCargoDialog(new Cargo(this.cargo));
	}

	public ContainerDlgView getContainerDlg() {
		return containerDlg;
	}

	public CargoDlgView getCargoDlg() {
		return cargoDlg;
	}

	public void setContainerDlg(ContainerDlgView containerDlg) {
		this.containerDlg = containerDlg;
	}

	private void prepareContainerDialog(Container container) {
		containerDlg = new ContainerDlgView(container, invoiceService,
				orderService, billService, getBaseService());
		containerDlg.addHandler(this);
	}

	private void prepareCargoDialog(Cargo cargo) {
		cargoDlg = new CargoDlgView(cargo, articleService, getBaseService());
		cargoDlg.addHandler(this);
	}

	public void addCargo() {
		Cargo cargo = new Cargo();
		prepareCargoDialog(cargo);
	}

	public void deleteCargo() {
		container.getDeclaredCondition().getCargoes().remove(cargo);
		containerService.save(container);
		cargo = null;
		resreshContainers();
	}

	public String getCargoName() {
		return new CargoPresentation(cargo).getArticle();
	}

	public String getFeatureName() {
		return feature != null ? feature.toString() : "";
	}

	public FeatureDlgView getFeatureDlg() {
		return featureDlg;
	}

	public void addFeature() {	
		prepareFeatureDialog(cargo.getFeatures());
	}

	public void setFeature(CargoArticleFeature feature) {
		this.feature = feature;
	}

	public void removeFeature() {
		feature.getCargo().removeFeature(feature);
		containerService.save(container);
		feature = null;
	}

	private void prepareFeatureDialog(Set<CargoArticleFeature> features) {
		featureDlg = new FeatureDlgView(features);
		featureDlg.addHandler(this);
	}
	
	public CargoPackage getCargoPackage() {
		return cargoPackage;
	}
	
	public void setWrappedCargoPackage(CargoPackagePresentation cargoPackage) {
		this.cargoPackage = cargoPackage.getCargoPackage();
	}
	
	public CargoPackageDlgView getCargoPackageDlg() {
		return cargoPackageDlg;
	}
	
	public void addPackage() {
		CargoPackage cargoPackage = new CargoPackage();
		cargo.addPackage(cargoPackage);
		prepareCargoPackageDlg(cargoPackage);
	}
	
	public void editPackage() {		
		prepareCargoPackageDlg(cargoPackage);
	}
	
	public void removePackage(){				
		cargoPackage.getCargo().removePackage(cargoPackage);		
		containerService.save(container);		
		cargoPackage = null;
	}
	
	private void prepareCargoPackageDlg(CargoPackage cargoPackage){
		cargoPackageDlg = new CargoPackageDlgView(cargoPackage, getBaseService());
		cargoPackageDlg.addHandler(this);
	}
	
	public String getPackageName(){
		return cargoPackage != null && cargoPackage.getMeasure() != null ?
				cargoPackage.getMeasure().getName() : "";
	}
	
	public CalibreDlgView getCalibreDlg() {
		return calibreDlg;
	}
	
	public CargoPackageCalibre getCalibre() {
		return calibre;
	}

	public void setCalibre(CargoPackageCalibre calibre) {
		this.calibre = calibre;
	}
	
	public void prepareCalibreDlg(CargoPackageCalibre calibre){
		calibreDlg = new CalibreDlgView(calibre);
		calibreDlg.addHandler(this);
	}
	
	public void addCalibre(){
		CargoPackageCalibre calibre = new CargoPackageCalibre();
		calibre.setCargoPackage(cargoPackage);
		prepareCalibreDlg(calibre);
	}

	public void editCalibre(){		
		prepareCalibreDlg(calibre);
	}
	
	public void removeCalibre(){		
		calibre.getCargoPackage().removeCalibre(calibre);		
		containerService.save(container);		
		calibre = null;
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
			}
		} else if (dialog instanceof CargoDlgView) {
			CargoDlgView d = (CargoDlgView) dialog;
			if (DialogActionEnum.ACCEPT.equals(action)) {
				Cargo c = d.getCargo();
				if (c.getId() == null) {
					c = d.getCargo();
					c.getCondition().setContainer(container);
					container.getActualCondition().getCargoes().add(c);
				} else {
					cargo.copy(d.getCargo());
				}
				container = containerService.save(container);
			}
		} else if (dialog instanceof FeatureDlgView) {
			FeatureDlgView d = (FeatureDlgView) dialog;
			if (DialogActionEnum.ACCEPT.equals(action)) {
				getBaseService().save(d.getCargoFeatures());
			}
		} else if (dialog instanceof CargoPackageDlgView) {
			CargoPackageDlgView d = (CargoPackageDlgView) dialog;
			if (DialogActionEnum.ACCEPT.equals(action)) {
				CargoPackage cargoPackage = d.getCargoPackage(); 
				getBaseService().save(cargoPackage);				
				container = containerService.save(container);
			}
		}  else if (dialog instanceof CalibreDlgView) {
			CalibreDlgView d = (CalibreDlgView) dialog;
			if (DialogActionEnum.ACCEPT.equals(action)) {
				CargoPackageCalibre calibre = d.getCalibre();
				if (calibre.getId() == null) {
					cargoPackage.addCalibre(calibre);
				}
				getBaseService().save(calibre);
				container = containerService.save(container);
			}
		}

		if (DialogActionEnum.ACCEPT.equals(action)) {
			resreshContainers();
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
}
