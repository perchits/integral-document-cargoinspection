package com.docum.view.container.unit;

import java.io.Serializable;

import com.docum.domain.po.common.CargoPackageCalibre;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;
import com.docum.view.container.CalibreDlgView;
import com.docum.view.container.ContainerHolder;
import com.docum.view.wrapper.CalibrePresentation;
import com.docum.view.wrapper.CargoPackagePresentation;

public class CalibreUnit implements Serializable, DialogActionHandler {
	private static final long serialVersionUID = -8081015094463275260L;

	private CalibreDlgView calibreDlg;
	private CargoPackageCalibre calibre;
	
	private CargoPackagePresentation cargoPackage;
	private ContainerHolder containerHolder;

	public CalibreUnit(ContainerHolder containerHolder) {
		this.containerHolder = containerHolder;
	}
	
	public void setCargoPackage(CargoPackagePresentation cargoPackage){
		this.cargoPackage = cargoPackage;
	}
	
	public CargoPackagePresentation getCargoPackage() {		
		return cargoPackage;
	}
	
	public CalibreDlgView getCalibreDlg() {
		return calibreDlg;
	}

	public CargoPackageCalibre getCalibre() {
		return calibre;
	}

	public void setCalibre(CargoPackageCalibre calibre) {
		this.calibre = calibre;
		containerHolder.setDlgCalibreUnit(this);
	}

	public void prepareCalibreDlg(CargoPackageCalibre calibre) {
		calibreDlg = new CalibreDlgView(calibre);
		calibreDlg.addHandler(this);
		containerHolder.setDlgCalibreUnit(this);
	}

	public void addCalibre() {
		CargoPackageCalibre calibre = new CargoPackageCalibre(cargoPackage.getCargoPackage());
		prepareCalibreDlg(calibre);
	}

	public void editCalibre() {
		prepareCalibreDlg(calibre);
	}

	public void removeCalibre() {
		calibre.getCargoPackage().removeCalibre(calibre);
		containerHolder.saveContainer();
		calibre = null;
	}
	
	public void setCalibreWrapped(CalibrePresentation calibre){
		this.calibre = calibre != null ? calibre.getCalibre() : null;
	}

	@Override
	public void handleAction(AbstractDlgView dialog, DialogActionEnum action) {
		if (dialog instanceof CalibreDlgView) {
			CalibreDlgView d = (CalibreDlgView) dialog;
			if (DialogActionEnum.ACCEPT.equals(action)) {
				CargoPackageCalibre calibre = d.getCalibre();
				if (calibre.getId() == null) {
					cargoPackage.getCargoPackage().addCalibre(calibre);
				}
				containerHolder.saveContainer();
			}
		}

	}

}
