package com.docum.view.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.docum.domain.po.common.ArticleDefect;
import com.docum.domain.po.common.CargoDefect;
import com.docum.domain.po.common.CargoDefectGroup;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.wrapper.CargoDefectPresentation;

public class DefectDlgView extends AbstractDlgView implements Serializable {
	private static final long serialVersionUID = -3938610126468987994L;
	private CargoDefect cargoDefect;
	private CargoDefectGroup cargoDefectGroup;
	private List<ArticleDefectWrapper> articleDefectsWrapped = new ArrayList<ArticleDefectWrapper>();
	private List<CargoDefect> customDefects = new ArrayList<CargoDefect>();
	
	
	public DefectDlgView(CargoDefectGroup cargoDefectGroup) {
		if (cargoDefectGroup == null)
			return;
		this.cargoDefectGroup = cargoDefectGroup;
		for (ArticleDefect articleDefect : cargoDefectGroup.getArticleCategory().getDefects()) {			
			articleDefectsWrapped.add(
					new ArticleDefectWrapper(articleDefect,hasDefect(articleDefect))
					);
		}
		
		for (CargoDefect cargoDefect : cargoDefectGroup.getDefects()) {
			if (cargoDefect.getArticleDefect() == null) {
				customDefects.add(new CargoDefect(cargoDefect));
			}
		}		
		
	}
	
	public void addCustomDefect() {
		customDefects.add(new CargoDefect(cargoDefectGroup));
	}

	public void removeCustomDefect() {
		customDefects.remove(cargoDefect);
	}
	
	private boolean hasDefect(ArticleDefect articleDefect) {
		for (CargoDefect defect : cargoDefectGroup.getDefects()) {
			if (defect.getArticleDefect() != null && 
					defect.getArticleDefect().equals(articleDefect)) {
				return true;
			}
		}
		return false;
	}

	public List<ArticleDefectWrapper> getArticleDefectsWrapped() {
		return articleDefectsWrapped;
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

	private void processDefects(){		
		cargoDefectGroup.getDefects().clear();
		for (ArticleDefectWrapper defectWrapped : articleDefectsWrapped) {
			if (defectWrapped.isSelected()) {	
				cargoDefectGroup.addDefect(
						new CargoDefect(cargoDefectGroup,defectWrapped.getArticleDefect()));
			}
		}		
		cargoDefectGroup.getDefects().addAll(customDefects);		
	}
	
	public void save() {
		processDefects();
		fireAction(this, DialogActionEnum.ACCEPT);
	}

	public String getDefectName() {
		return new CargoDefectPresentation(cargoDefect).getTitle();
	}

	public String getTitle() {
		return cargoDefectGroup != null ? String.format("Дефекты для категории «%1$s»",
				cargoDefectGroup.getArticleCategory().getName()) : "";
	}

	public CargoDefectGroup getCargoDefectGroup() {
		return cargoDefectGroup;
	}

	public void setCargoDefectGroup(CargoDefectGroup cargoDefectGroup) {
		this.cargoDefectGroup = cargoDefectGroup;
	}

	public List<CargoDefect> getCustomDefects() {
		return customDefects;
	}

	public void setCustomDefects(List<CargoDefect> customDefects) {
		this.customDefects = customDefects;
	}
	
	public static class ArticleDefectWrapper implements Serializable {
		private static final long serialVersionUID = -6491344814973011719L;
		private ArticleDefect articleDefect;
		private boolean selected;

		public ArticleDefectWrapper(ArticleDefect articleDefect, boolean selected) {
			this.articleDefect = articleDefect;
			this.selected = selected;
		}

		public String getName() {
			return articleDefect != null ? articleDefect.getName() : null;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		public ArticleDefect getArticleDefect() {
			return articleDefect;
		}

		public void setArticleDefect(ArticleDefect articleDefect) {
			this.articleDefect = articleDefect;
		}
	}
	

}
