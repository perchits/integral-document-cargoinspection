package com.docum.view.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleFeature;
import com.docum.domain.po.common.ArticleFeatureInstance;
import com.docum.domain.po.common.CargoArticleFeature;
import com.docum.service.ArticleService;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;

public class FeatureDlgView extends AbstractDlgView implements Serializable {
	private static final long serialVersionUID = -3474736648929694055L;
	private ArticleService articleService;
	private Set<CargoArticleFeature> cargoFeatures;
	private List<ArticleFeatureInstance> featureInstances;
	private List<ArticleFeature> articleFeatures;

	public List<ArticleFeature> getArticleFeatures() {
		return articleFeatures;
	}

	public List<ArticleFeatureInstance> getFeatureInstances() {
		return featureInstances;
	}

	public void setCargoFeature(Set<CargoArticleFeature> features) {
		this.cargoFeatures = features;
	}

	public List<ArticleFeatureInstance> getFeatureInstancesById(Long id){
		return articleService.getArticleFeatureInstanceByArticle(id);
	}
	
	public FeatureDlgView(Set<CargoArticleFeature> features,
			ArticleService articleService, Article article) {
		this.articleService = articleService;
		this.cargoFeatures = features;
		featureInstances = articleService
				.getArticleFeatureInstanceByArticle(article.getId());
		articleFeatures = articleService.getArticleFeatureByArticle(article
				.getId());
	}

	public List<CargoArticleFeature> getCargoFeatures() {
		return new ArrayList<CargoArticleFeature>(cargoFeatures);
	}

	public String getTitle() {						
		return "Редактирование свойств для груза " + 
			getCargoFeatures().get(0).getCargo().toString();
	}

	public void save() {
		fireAction(this, DialogActionEnum.ACCEPT);
	}
}
