package com.docum.view.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.docum.domain.po.common.ArticleInspectionOption;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoInspectionOption;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;

public class OptionDlgView extends AbstractDlgView implements Serializable {
	private static final long serialVersionUID = 4031976610041908994L;
	private ArticleInspectionOption articleInspectionOption;
	private List<CargoInspectionOption> cargoOptionsCopy = new ArrayList<CargoInspectionOption>();	
	private Set<CargoInspectionOption> cargoOptions;

	public OptionDlgView(ArticleInspectionOption articleInspectionOption, Cargo cargo) {
		this.articleInspectionOption = articleInspectionOption;
		if (cargo == null)
			return;

		cargoOptions = cargo.getInspectionInfo().getInspectionOptions();
		
		if (cargo.getInspectionInfo() != null) {
			for (CargoInspectionOption op : cargoOptions) {
				cargoOptionsCopy.add(new CargoInspectionOption(op));
			}
		}
		Collections.sort(cargoOptionsCopy, new Comparator<CargoInspectionOption>(){
			@Override
			public int compare(CargoInspectionOption o1, CargoInspectionOption o2) {
				return new Integer(o1.getArticleInspectionOption().getOrd()).compareTo(
						new Integer(o2.getArticleInspectionOption().getOrd()));
			}});

		List<ArticleInspectionOption> children = articleInspectionOption.getChildren();
		if (children.size() > 0) {
			for (ArticleInspectionOption option : children) {
				if (!hasOption(option)) {
					cargoOptionsCopy.add(new CargoInspectionOption(option, cargo.getInspectionInfo()));
				}
			}
		} else {
			if (!hasOption(articleInspectionOption)) {
				cargoOptionsCopy.add(new CargoInspectionOption(articleInspectionOption, cargo.getInspectionInfo()));
			}
		}
	}

	private boolean hasOption(ArticleInspectionOption articleOption) {
		for (CargoInspectionOption cargoOption : cargoOptionsCopy) {
			if (cargoOption.getArticleInspectionOption().equals(articleOption)) {
				return true;
			}
		}
		return false;
	}

	public List<CargoInspectionOption> getCargoOptionsCopy() {
		List<CargoInspectionOption> result = new ArrayList<CargoInspectionOption>();
		for (CargoInspectionOption op : cargoOptionsCopy) {
			if (op.getArticleInspectionOption().getRoot().equals(articleInspectionOption)) {
				result.add(op);
			}
		}
		return result;
	}

	public ArticleInspectionOption getArticleInspectionOption() {
		return articleInspectionOption;
	}

	public void setArticleInspectionOption(ArticleInspectionOption articleInspectionOption) {
		this.articleInspectionOption = articleInspectionOption;
	}

	public String getTitle() {
		return articleInspectionOption != null ? String.format("Значения для свойства «%1$s»",
				articleInspectionOption.getName()) : "";
	}
	
	public void save() {
		cargoOptions.clear();
		cargoOptions.addAll(cargoOptionsCopy);
		fireAction(this, DialogActionEnum.ACCEPT);
	}

}
