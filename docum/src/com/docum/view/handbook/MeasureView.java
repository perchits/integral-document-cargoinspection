package com.docum.view.handbook;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Measure;

@ManagedBean(name = "measureBean")
@SessionScoped
public class MeasureView extends BaseView implements Serializable {

	private static final long serialVersionUID = 8206542333338880241L;
	private static final String sign = "Ед. изм.";
	private Measure measure = new Measure();

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return measure.getName();
	}

	@Override
	public void newObject() {
		super.newObject();
		this.measure = new Measure();
		setTitle("Новая " + getSign().toLowerCase());
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return measure != null ? this.measure : new Measure();		
	}

	public void setMeasure(Measure mesure) {		
			this.measure = mesure;		
	}

	public Measure getMeasure() {
		return measure;
	}

}
