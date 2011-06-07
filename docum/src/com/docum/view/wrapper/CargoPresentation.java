package com.docum.view.wrapper;

import java.io.Serializable;

import com.docum.domain.ContainerStateEnum;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoCondition;

public class CargoPresentation implements Serializable {
	private static final long serialVersionUID = -3161032049705097594L;
	private Cargo cargo;

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public CargoPresentation(Cargo cargo) {
		this.cargo = cargo;
	}

	public CargoCondition getActiveCargoCondition() {
		if (cargo != null) {
			ContainerStateEnum sate = cargo.getContainer().getState();
			if (ContainerStateEnum.HANDLED.equals(sate)
					|| ContainerStateEnum.REPORTED.equals(sate)) {
				return cargo.getActualCondition();
			} else {
				return cargo.getDeclaredCondition();
			}
		} else {
			return null;
		}
	}
}
