package com.docum.view.handbook;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.docum.persistence.IdentifiedEntity;
import com.docum.persistence.common.Mesure;

@ManagedBean(name = "mesureBean")
@SessionScoped
public class MesureView extends BaseView implements Serializable {

	private static final long serialVersionUID = 8206542333338880241L;
	private static final String sign = "Ед. изм.";
	private Mesure mesure = new Mesure();		
	

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {		
		return mesure.getName();
	}
	
	@Override
	public void newObject() {
		super.newObject();
		this.mesure = new Mesure();
		setTitle("Новая " + getSign().toLowerCase());
	}

	@Override
	public IdentifiedEntity getBeanObject() {		
		return mesure;
	}

	public void setMesure(Mesure mesure) {
		this.mesure = mesure;
	}

	public Mesure getMesure() {
		return mesure;
	}

}
