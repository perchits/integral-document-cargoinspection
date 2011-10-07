package com.docum.view.container.unit;

import java.io.Serializable;

import com.docum.domain.po.common.ArticleInspectionOption;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;
import com.docum.view.container.ContainerHolder;
import com.docum.view.container.OptionDlgView;
import com.docum.view.wrapper.CargoPresentation;

public class OptionUnit implements Serializable, DialogActionHandler {
	private static final long serialVersionUID = -1441056969879962912L;
	private ContainerHolder containerHolder;
	private OptionDlgView optionDlg;
	private ArticleInspectionOption articleInspectionOption;
	private CargoPresentation cargo;

	public OptionUnit(ContainerHolder containerHolder) {
		this.containerHolder = containerHolder;
	}
	
	public void setCargo(CargoPresentation cargo){
		this.cargo = cargo;
	}
	
	public OptionDlgView getOptionDlg() {
		return optionDlg;
	}

	public void setOptionDlg(OptionDlgView optionDialog) {
		this.optionDlg = optionDialog;
	}
	
	public void prepareOptionDlg(){
		optionDlg = new OptionDlgView(articleInspectionOption, cargo.getCargo());
		optionDlg.addHandler(this);		
		containerHolder.setDlgOptionUnit(this);
	}

	public ArticleInspectionOption getArticleInspectionOption() {
		return articleInspectionOption;
	}

	public void setArticleInspectionOption(ArticleInspectionOption articleInspectionOption) {
		this.articleInspectionOption = articleInspectionOption;
	}

	@Override
	public void handleAction(AbstractDlgView dialog, DialogActionEnum action) {
		if (dialog instanceof OptionDlgView) {
			if (DialogActionEnum.ACCEPT.equals(action)) {
				containerHolder.saveContainer();
			}
		}		
	}

}
