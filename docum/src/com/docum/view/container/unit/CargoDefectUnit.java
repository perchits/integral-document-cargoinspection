package com.docum.view.container.unit;

import java.io.Serializable;

import com.docum.domain.po.common.CargoDefect;
import com.docum.domain.po.common.CargoDefectGroup;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;
import com.docum.view.container.ContainerHolder;
import com.docum.view.container.DefectDlgView;
import com.docum.view.wrapper.CargoDefectGroupPresentation;
import com.docum.view.wrapper.CargoDefectPresentation;

public class CargoDefectUnit implements Serializable, DialogActionHandler {
	private static final long serialVersionUID = -7141222194218275903L;
		
	private CargoDefect cargoDefect;
	private DefectDlgView defectDlg;
	private ContainerHolder containerHolder;
	private CargoDefectGroup cargoDefectGroup;
	

	public CargoDefectUnit(ContainerHolder containerHolder) {
		this.containerHolder = containerHolder;
	}

	public CargoDefectGroup getCargoDefectGroup() {
		return cargoDefectGroup;
	}

	public void setCargoDefectGroup(CargoDefectGroup cargoDefectGroup) {
		this.cargoDefectGroup = cargoDefectGroup;
	}	

	public void setWrappedCargoDefectGroup(CargoDefectGroupPresentation cargoDefectGroup) {
		this.cargoDefectGroup = cargoDefectGroup.getCargoDefectGroup();
	}
	
	public String getTitle(){
		return new CargoDefectPresentation(cargoDefect).getTitle();
	}
	
	public DefectDlgView getDefectDlg() {
		return defectDlg;
	}	
	
	public void prepareDefectDialog(){
		defectDlg = new DefectDlgView(cargoDefectGroup);
		defectDlg.addHandler(this);		
		containerHolder.setDlgDefectUnit(this);
	}

	public CargoDefect getCargoDefect() {
		return cargoDefect;
	}

	public void setCargoDefect(CargoDefect cargoDefect) {
		this.cargoDefect = cargoDefect;		
	}
	
	public void setWrappedCargoDefect(CargoDefectPresentation cargoDefect) {
		this.cargoDefect = cargoDefect.getCargoDefect();
		containerHolder.setDlgDefectUnit(this);
	}
	
	@Override
	public void handleAction(AbstractDlgView dialog, DialogActionEnum action) {
		if (dialog instanceof DefectDlgView) {
			if (DialogActionEnum.ACCEPT.equals(action)) {
				containerHolder.saveContainer();
			}
		}

	}

}
