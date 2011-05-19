package com.docum.view.handbook;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.docum.persistence.IdentifiedEntity;
import com.docum.persistence.common.Tare;

@ManagedBean(name = "tareBean")
@SessionScoped
public class TareView extends BaseView implements Serializable {

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
