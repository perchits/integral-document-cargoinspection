package com.docum.view.container.unit;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleFeature;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoArticleFeature;
import com.docum.util.AlgoUtil;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;
import com.docum.view.container.CargoDlgView;
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

	/**
	 * Этот метод вызывается всегда, когда пользователь вызывает диалог с характеристиками груза.
	 * Метод проверяет, появились ли новые характеристики у груза и если да, добавляет их в
	 * список.
	 */
	public void addFeature() {
		Article article = cargo.getArticle();
		Set<CargoArticleFeature> features = new HashSet<CargoArticleFeature>(cargo.getFeatures());
		for (final ArticleFeature feature : article.getFeatures()) {
			CargoArticleFeature res = AlgoUtil.find(cargo.getFeatures(),
					new AlgoUtil.FindPredicate<CargoArticleFeature>() {
						public boolean isIt(CargoArticleFeature tmp) {
							return tmp.getFeature().equals(feature);
						}
					});
			if (res == null) {
				features.add(new CargoArticleFeature(cargo, feature));
			}
		}		
		prepareFeatureDialog(features);
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
			FeatureDlgView d = (FeatureDlgView) dialog;
			if (DialogActionEnum.ACCEPT.equals(action)) {
				cargo.setFeatures(new HashSet<CargoArticleFeature>(d.getCargoFeatures()));
				containerHolder.saveContainer();
			}
		}

	}

}
