package com.docum.view.container;

import java.io.Serializable;

import com.docum.domain.po.common.CargoDefect;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.wrapper.CargoDefectPresentation;

public class DefectDlgView extends AbstractDlgView implements Serializable {
	private static final long serialVersionUID = -3938610126468987994L;
	private CargoDefect cargoDefect;

	public DefectDlgView(CargoDefect cargoDefect) {
		this.cargoDefect = cargoDefect;
	}

	public CargoDefect getCargoDefect() {
		return cargoDefect;
	}

	public void setCargoDefect(CargoDefect cargoDefect) {
		this.cargoDefect = cargoDefect;
	}

	public Boolean getCustomDefect() {
		return new CargoDefectPresentation(cargoDefect).getCustomDefect(); 
	}

	public void save() {
		fireAction(this, DialogActionEnum.ACCEPT);
	}

	public String getDefectName() {
		return new CargoDefectPresentation(cargoDefect).getTitle();
	}

	public String getTitle() {
		String action = cargoDefect != null ? cargoDefect.getId() == null ? "Добавление"
				: "Правка"
				: "";
		return String.format("%1$s дефекта для категории «%2$s»", action,
				new CargoDefectPresentation(cargoDefect).getGroupTitle());
	}

}
