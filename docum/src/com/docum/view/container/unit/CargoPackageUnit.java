package com.docum.view.container.unit;

import java.io.Serializable;
import java.util.List;

import com.docum.domain.po.common.CargoPackage;
import com.docum.domain.po.common.Measure;
import com.docum.service.BaseService;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;
import com.docum.view.container.CargoPackageDlgView;
import com.docum.view.container.ContainerContext;
import com.docum.view.container.ContainerHolder;
import com.docum.view.wrapper.CargoPackagePresentation;
import com.docum.view.wrapper.CargoPresentation;

public class CargoPackageUnit implements Serializable, DialogActionHandler {
	private static final long serialVersionUID = 7333408655523733042L;
	private CargoPresentation cargo;
	private CargoPackage cargoPackage;
	private CargoPackageDlgView cargoPackageDlg;
	private BaseService baseService;
	private ContainerHolder containerHolder;
	private CalibreUnit calibreUnit;

	public CargoPackageUnit(ContainerHolder containerHolder) {
		this.containerHolder = containerHolder;
		calibreUnit = new CalibreUnit(containerHolder);
	}

	public void setContext(ContainerContext context) {
		baseService = context.getBaseService();		
	}
	
	public CargoPresentation getCargo(){
		return cargo; 
	}
	
	public void setCargo(CargoPresentation cargo){	
		this.cargo = cargo;
	}
	
	public CalibreUnit getCalibreUnit() {		
		return calibreUnit;
	}

	public CargoPackage getCargoPackage() {
		return cargoPackage;
	}

	public void setWrappedCargoPackage(CargoPackagePresentation cargoPackage) {
		this.cargoPackage = cargoPackage.getCargoPackage();
		containerHolder.setDlgPackageUnit(this);
	}

	public CargoPackageDlgView getCargoPackageDlg() {
		return cargoPackageDlg;
	}

	public void addPackage() {
		CargoPackage cargoPackage = new CargoPackage(cargo.getCargo());
		List<Measure> measures = baseService.getAll(Measure.class);
		for (CargoPackage cp : cargo.getCargo().getCargoPackages()) {
			measures.remove(cp.getMeasure());
		}
		prepareCargoPackageDlg(cargoPackage, measures);
	}

	public void editPackage() {
		List<Measure> measures = baseService.getAll(Measure.class);
		for (CargoPackage cp : cargoPackage.getCargo().getCargoPackages()) {
			if (!cp.equals(cargoPackage)) {
				measures.remove(cp.getMeasure());
			}
		}		
		CargoPackage editPackage = new CargoPackage();
		editPackage.copy(cargoPackage);
		prepareCargoPackageDlg(editPackage, measures);
	}

	public void removePackage() {
		cargoPackage.getCargo().removePackage(cargoPackage);
		containerHolder.saveContainer();
		cargoPackage = null;
	}

	private void prepareCargoPackageDlg(CargoPackage cargoPackage,
			List<Measure> measures) {
		cargoPackageDlg = new CargoPackageDlgView(cargoPackage, measures);
		cargoPackageDlg.addHandler(this);
		containerHolder.setDlgPackageUnit(this);
	}

	public String getPackageName() {
		return cargoPackage != null && cargoPackage.getMeasure() != null ? cargoPackage
				.getMeasure().getName() : "";
	}

	@Override
	public void handleAction(AbstractDlgView dialog, DialogActionEnum action) {
		if (dialog instanceof CargoPackageDlgView) {
			CargoPackageDlgView d = (CargoPackageDlgView) dialog;
			if (DialogActionEnum.ACCEPT.equals(action)) {
				CargoPackage cargoPackage = d.getCargoPackage();
				if (cargoPackage.getId() == null) {					
					cargo.getCargo().addPackage(cargoPackage);
				} else {
					this.cargoPackage.copy(cargoPackage);
				}
				containerHolder.saveContainer();
			}
		}
	}

}
