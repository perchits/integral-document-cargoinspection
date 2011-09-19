package com.docum.view.dict;

import java.io.Serializable;
import java.util.List;

import com.docum.domain.po.common.ArticleInspectionOption;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;

public class ArticleOptionDlgView extends AbstractDlgView implements Serializable {
	private static final long serialVersionUID = -9133929882769545470L;
	
	private ArticleInspectionOption option;
	
	public ArticleOptionDlgView(ArticleInspectionOption option) {
		this.option = option;
	}
	
	public ArticleInspectionOption getOption() {
		return option;
	}

	public void setOption(ArticleInspectionOption option) {
		this.option = option;
	}

	public List<ArticleInspectionOption> getChildren(){
		return option != null ? option.getChildren() : null;
	}
	
	public void save() {
		fireAction(this, DialogActionEnum.ACCEPT);		
	}
	
	public String getTitle() {		
		return option != null ? option.getId() == null ? 
				"Добавление свойства" :  String.format("Правка %1$s",option.getName()) : "";		
	}

}
