package com.docum.view.dict;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Surveyor;

@Controller("surveyorBean")
@Scope("session")
public class SurveyorView extends BaseView {
	
	private static final long serialVersionUID = -891135652492703845L;
	private static final String sign = "Инспектор";
	
	private Surveyor s = new Surveyor();
	
	public Surveyor getS() {
		return s;
	}
	
	public void setS(Surveyor s) {
		this.s = s;
	}
	
	@Override
	public void newObject() {
		super.newObject();
		this.s = new Surveyor();
	}
	
	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBriefInfo() {
		return s.getName();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return s != null ? this.s : new Surveyor();
	}

}
