package com.docum.view.container.unit;

import java.io.Serializable;
import java.util.List;

import com.docum.domain.po.common.Cargo;
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

public class CargoPackageUnit implements Serializable, DialogActionHandler {
	private static final long serialVersionUID = 7333408655523733042L;
	private Cargo cargo;
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
		cargo = context.getCargo();
	}
	
	public CalibreUnit getCalibreUnit() {
		ContainerContext context = new ContainerContext();
		context.setCargoPackage(cargoPackage);
		calibreUnit.setContext(context);
		return calibreUnit;
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
		CargoPackage cargoPackage = new CargoPackage(cargo);
		List<Measure> measures = baseService.getAll(Measure.class);
		for (CargoPackage cp : cargo.getCargoPackages()) {
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
		prepareCargoPackageDlg(cargoPackage, measures);
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
					cargo.addPackage(cargoPackage);
				}
				containerHolder.saveContainer();
			}
		}
	}

}
