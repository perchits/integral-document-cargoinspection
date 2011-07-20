package com.docum.view.container.unit;

import java.io.Serializable;
import java.util.Set;

import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoArticleFeature;
import com.docum.service.BaseService;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;
import com.docum.view.container.ContainerContext;
import com.docum.view.container.ContainerHolder;
import com.docum.view.container.FeatureDlgView;
import com.docum.view.wrapper.CargoPresentation;

public class CargoFeatureUnit implements Serializable, DialogActionHandler {
	private static final long serialVersionUID = -8975590625480426959L;
	private FeatureDlgView featureDlg;
	private CargoArticleFeature feature;
	private Cargo cargo;
	private BaseService baseService;
	private ContainerHolder containerHolder;
	
	public CargoFeatureUnit(ContainerHolder containerHolder){
		this.containerHolder = containerHolder;		
	}
	
	public CargoPresentation getCargo(){
		return new CargoPresentation(cargo); 
	}
	
	public void setCargo(CargoPresentation cargo){	
		if (cargo != null && cargo.getCargo() != null) {
			this.cargo = cargo.getCargo();
		}		 
	}
	
	public void setContext(ContainerContext context) {
		baseService = context.getBaseService();
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
	}

	public void removeFeature() {
		feature.getCargo().removeFeature(feature);
		containerHolder.saveContainer();
		feature = null;
	}

	private void prepareFeatureDialog(Set<CargoArticleFeature> features) {
		featureDlg = new FeatureDlgView(features);
		featureDlg.addHandler(this);
	}

	@Override
	public void handleAction(AbstractDlgView dialog, DialogActionEnum action) {
		if (dialog instanceof FeatureDlgView) {
			FeatureDlgView d = (FeatureDlgView) dialog;
			if (DialogActionEnum.ACCEPT.equals(action)) {
				baseService.save(d.getCargoFeatures());
			}
		}
		
	}

}
