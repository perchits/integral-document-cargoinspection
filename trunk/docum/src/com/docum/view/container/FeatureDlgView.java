package com.docum.view.container;

import java.io.Serializable;
import java.util.List;

import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleFeature;
import com.docum.domain.po.common.ArticleFeatureInstance;
import com.docum.domain.po.common.CargoArticleFeature;
import com.docum.service.ArticleService;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;

public class FeatureDlgView extends AbstractDlgView implements Serializable {
	private static final long serialVersionUID = -3474736648929694055L;

	private CargoArticleFeature cargoFeature;
	private List<ArticleFeatureInstance> featureInstances;
	private List<ArticleFeature> articleFeatures;

	public List<ArticleFeature> getArticleFeatures() {
		return articleFeatures;
	}

	public List<ArticleFeatureInstance> getFeatureInstances() {
		return featureInstances;
	}

	public void setCargoFeature(CargoArticleFeature feature) {
		this.cargoFeature = feature;
	}

	public FeatureDlgView(CargoArticleFeature feature,
			ArticleService articleService, Article article) {
		this.cargoFeature = feature;
		featureInstances = articleService
				.getArticleFeatureInstanceByArticle(article.getId());
		articleFeatures = articleService.getArticleFeatureByArticle(article
				.getId());
	}

	public CargoArticleFeature getCargoFeature() {
		return cargoFeature;
	}

	public String getTitle() {
		String result = cargoFeature.getId() != null ? "Редактирование свойства "
				+ cargoFeature.toString()
				: "Новое свойство";
		return result + " для груза " + cargoFeature.getCargo().toString();
	}

	public void save() {
		fireAction(this, DialogActionEnum.ACCEPT);
	}
}
