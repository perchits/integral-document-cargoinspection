package com.docum.view.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.docum.domain.po.common.CargoArticleFeature;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;

public class FeatureDlgView extends AbstractDlgView implements Serializable {
	private static final long serialVersionUID = -3474736648929694055L;
	
	private Set<CargoArticleFeature> cargoFeatures;
	
	public void setCargoFeature(Set<CargoArticleFeature> features) {
		this.cargoFeatures = features;
	}	
	
	public FeatureDlgView(Set<CargoArticleFeature> features) {		
		this.cargoFeatures = features;		
	}

	public List<CargoArticleFeature> getCargoFeatures() {
		List<CargoArticleFeature> result = new ArrayList<CargoArticleFeature>(cargoFeatures);
		Collections.sort(result, new Comparator<CargoArticleFeature>() {
			@Override
			public int compare(CargoArticleFeature o1, CargoArticleFeature o2) {
				return o1.getFeature().getName().compareTo(o2.getFeature().getName());
			}
		});
		return result;
	}

	public String getTitle() {						
		return String.format("Редактирование свойств для груза «%1$s»" , 
			getCargoFeatures().get(0).getCargo().toString());
	}

	public void save() {
		fireAction(this, DialogActionEnum.ACCEPT);
	}
}
