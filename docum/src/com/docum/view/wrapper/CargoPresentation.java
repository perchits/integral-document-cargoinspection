package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.List;

import com.docum.domain.ContainerStateEnum;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoArticleFeature;
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
		if (cargo != null && cargo.getContainer() != null) {
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

	public String getArticle() {
		return cargo != null && cargo.getArticle()!= null ? cargo.getArticle().getName() : null;
	}

	public String getArticleCategory() {
		return cargo != null && cargo.getArticleCategory() != null ? cargo.getArticleCategory().getName() : null;
	}

	public String getSupplier() {
		return cargo != null && cargo.getSupplier() != null ? cargo.getSupplier().getCompany().getName() : null;
	}

	public List<CargoArticleFeature> getFeatures() {
		return cargo != null ? cargo.getFeatures() : null;
	}
}
