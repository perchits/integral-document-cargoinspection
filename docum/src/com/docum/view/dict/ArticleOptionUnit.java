package com.docum.view.dict;

import java.io.Serializable;

import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleInspectionOption;
import com.docum.service.BaseService;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;

public class ArticleOptionUnit implements Serializable, DialogActionHandler {
	private static final long serialVersionUID = -11767896758726787L;
	private ArticleOptionDlgView optionDlg;
	private Article article;
	private BaseService baseService;
	private ArticleInspectionOption option;
		
	public ArticleOptionUnit(Article article, BaseService baseService, 
			ArticleInspectionOption option) {
		this.article = article;
		this.baseService = baseService;
		this.option = option;
	}

	public ArticleInspectionOption getOption() {
		return option;
	}

	public void setOption(ArticleInspectionOption option) {
		this.option = option;
	}

	public ArticleOptionDlgView getOptionDlg() {
		return optionDlg;
	}

	public void setOptionDlg(ArticleOptionDlgView optionDlg) {
		this.optionDlg = optionDlg;
	}

	public void prepareOptionDlg(ArticleInspectionOption option) {
		optionDlg = new ArticleOptionDlgView(option);
		optionDlg.addHandler(this);
	}

	public void editOption() {
		prepareOptionDlg(option);
	}

	public void addOption() {
		option = new ArticleInspectionOption();
		editOption();
	}

	public void removeOption() {
		article.getInspectionOptions().remove(option);
		baseService.save(this.article);
	}

	@Override
	public void handleAction(AbstractDlgView dialog, DialogActionEnum action) {
		if (dialog instanceof ArticleOptionDlgView) {
			ArticleOptionDlgView d = (ArticleOptionDlgView) dialog;
			if (DialogActionEnum.ACCEPT.equals(action)) {
				ArticleInspectionOption option = d.getOption();
				if (option.getId() == null) {
					article.getInspectionOptions().add(option);
				} else {
					article.getInspectionOptions().set(
							article.getInspectionOptions().indexOf(option), option);
				}
				baseService.save(this.article);
			}
		}
	}
}
