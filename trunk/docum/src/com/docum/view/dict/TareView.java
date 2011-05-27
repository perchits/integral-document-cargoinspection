package com.docum.view.dict;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Tare;

@Controller("tareBean")
@Scope("session")
public class TareView extends BaseView {

	private static final long serialVersionUID = 8206542333338880241L;
	private static final String sign = "Тара";
	private Tare tare = new Tare();

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return tare.getName();
	}

	@Override
	public void newObject() {
		super.newObject();
		this.tare = new Tare();
		setTitle("Новая " + getSign().toLowerCase());
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return tare != null ? this.tare : new Tare();		
	}

	public void setTare(Tare mesure) {		
			this.tare = mesure;		
	}

	public Tare getTare() {
		return tare;
	}

}
