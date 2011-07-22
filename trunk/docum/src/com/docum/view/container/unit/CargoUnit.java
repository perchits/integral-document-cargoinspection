package com.docum.view.container.unit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoCondition;
import com.docum.service.ArticleService;
import com.docum.service.BaseService;
import com.docum.util.AlgoUtil;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;
import com.docum.view.container.CargoDlgView;
import com.docum.view.container.ContainerContext;
import com.docum.view.container.ContainerHolder;
import com.docum.view.wrapper.CargoPresentation;
import com.docum.view.wrapper.CargoTransformer;

public class CargoUnit implements Serializable, DialogActionHandler {
	private static final long serialVersionUID = 4121556886204075852L;
	private CargoDlgView cargoDlg;
	private Cargo cargo;
	
	private BaseService baseService;
	private ArticleService articleService;
	private ContainerHolder containerHolder;
	private CargoFeatureUnit cargoFeatureUnit;
	private CargoPackageUnit cargoPackageUnit;
	private CargoCondition cargoCondition;

	public CargoUnit(ContainerHolder containerHolder) {
		this.containerHolder = containerHolder;
		cargoFeatureUnit = new CargoFeatureUnit(containerHolder);
		cargoPackageUnit = new CargoPackageUnit(containerHolder);
	}

	public void setContext(ContainerContext context) {		
		baseService = context.getBaseService();
		articleService = context.getArticleService();
	}
	
	public ContainerContext populateContext(){
		ContainerContext context = new ContainerContext();		
		context.setBaseService(baseService);
		return context;
	}

	public void setCargoCondition(CargoCondition cargoCondition) {
		this.cargoCondition = cargoCondition;
	}
	
	public CargoFeatureUnit getCargoFeatureUnit() {	
		return cargoFeatureUnit;
	}

	public CargoPackageUnit getCargoPackageUnit() {		
		cargoPackageUnit.setContext(populateContext());
		return cargoPackageUnit;
	}
	
	public void setCargo(CargoPresentation cargo) {
		this.cargo = cargo.getCargo();
		containerHolder.setDlgCargoUnit(this);
	}

	public void setEditCargo(CargoPresentation cargo) {
		setCargo(cargo);
		prepareCargoDialog(new Cargo(this.cargo));
	}

	public CargoDlgView getCargoDlg() {
		return cargoDlg;
	}

	private void prepareCargoDialog(Cargo cargo) {		
		cargoDlg = new CargoDlgView(cargo, articleService, baseService);
		cargoDlg.addHandler(this);
		containerHolder.setDlgCargoUnit(this);
	}

	public void addCargo() {
		Cargo cargo = new Cargo();
		prepareCargoDialog(cargo);
	}

	public void deleteCargo() {
		cargoCondition.getCargoes().remove(cargo);
		containerHolder.saveContainer();
		cargo = null;		
	}

	public String getCargoName() {
		return new CargoPresentation(cargo).getArticle();
	}
	
	public List<CargoPresentation> getContainerCargoes() {
		if (cargoCondition != null) {
			Collection<Cargo> c = cargoCondition.getCargoes();
			List<CargoPresentation> result = new ArrayList<CargoPresentation>(
					c.size());
			AlgoUtil.transform(result, c, new CargoTransformer());
			return result;
		} else {
			return null;
		}
	}

	@Override
	public void handleAction(AbstractDlgView dialog, DialogActionEnum action) {
		if (dialog instanceof CargoDlgView) {
			CargoDlgView d = (CargoDlgView) dialog;
			if (DialogActionEnum.ACCEPT.equals(action)) {
				Cargo c = d.getCargo();
				if (c.getId() == null) {
					c = d.getCargo();
					cargoCondition.addCargo(c);
				} else {
					cargo.copy(d.getCargo());
				}
				containerHolder.saveContainer();				
			}
		}
	}

}
