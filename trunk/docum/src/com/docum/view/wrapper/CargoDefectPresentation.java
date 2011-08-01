package com.docum.view.wrapper;

import java.io.Serializable;

import com.docum.domain.po.common.CargoDefect;

public class CargoDefectPresentation implements Serializable {
	private static final long serialVersionUID = 2568146058868607686L;
	private CargoDefect cargoDefect;

	public CargoDefectPresentation(CargoDefect cargoDefect) {
		this.cargoDefect = cargoDefect;
	}

	public CargoDefect getCargoDefect() {
		return cargoDefect;
	}

	public void setCargoDefect(CargoDefect cargoDefect) {
		this.cargoDefect = cargoDefect;
	}

	public Boolean getCustomDefect() {
		return cargoDefect != null ? cargoDefect.getArticleDefect() != null ? false
				: true
				: null;
	}
	
	public String getTitle() {
		return toString();
	}

	public String getGroupTitle() {
		return cargoDefect != null ? new CargoDefectGroupPresentation(
				cargoDefect.getDefectGroup()).getTitle() : "";
	}

	public String toString() {
		if (cargoDefect != null) {
			return cargoDefect.getArticleDefect() != null ? cargoDefect
					.getArticleDefect().getName() : cargoDefect.getName();
		} else {
			return null;
		}
	}

}
