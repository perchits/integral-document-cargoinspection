package com.docum.view.container.unit;

import java.io.Serializable;
import java.util.Set;

import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoArticleFeature;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;
import com.docum.view.container.ContainerHolder;
import com.docum.view.container.FeatureDlgView;
import com.docum.view.wrapper.CargoPresentation;

public class CargoFeatureUnit implements Serializable, DialogActionHandler {
	private static final long serialVersionUID = -8975590625480426959L;
	private FeatureDlgView featureDlg;
	private CargoArticleFeature feature;
	private Cargo cargo;	
	private ContainerHolder containerHolder;

	public CargoFeatureUnit(ContainerHolder containerHolder) {
		this.containerHolder = containerHolder;
	}

	public CargoPresentation getCargo() {
		return new CargoPresentation(cargo);
	}

	public void setCargo(CargoPresentation cargo) {
		if (cargo != null && cargo.getCargo() != null) {
			this.cargo = cargo.getCargo();
		}
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
		containerHolder.setDlgFeatureUnit(this);
	}

	public void removeFeature() {
		feature.getCargo().removeFeature(feature);
		containerHolder.saveContainer();
		feature = null;
	}

	private void prepareFeatureDialog(Set<CargoArticleFeature> features) {
		featureDlg = new FeatureDlgView(features);
		featureDlg.addHandler(this);
		containerHolder.setDlgFeatureUnit(this);
	}

	@Override
	public void handleAction(AbstractDlgView dialog, DialogActionEnum action) {
		if (dialog instanceof FeatureDlgView) {
			if (DialogActionEnum.ACCEPT.equals(action)) {
				containerHolder.saveContainer();
			}
		}

	}

}
