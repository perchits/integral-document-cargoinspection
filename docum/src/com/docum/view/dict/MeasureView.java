package com.docum.view.dict;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.Measure;

@Controller("measureBean")
@Scope("session")
public class MeasureView extends BaseView {

	private static final long serialVersionUID = 8206542333338880241L;
	private static final String sign = "Ед. изм.";
	private Measure measure = new Measure();

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBriefInfo() {
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
	
	@Override
	public Map<String, SortOrderEnum> getDefaultSortFields(){
		HashMap<String, SortOrderEnum> sortFields = new HashMap<String, SortOrderEnum>();
		sortFields.put("name", SortOrderEnum.ASC);
		return sortFields;
	}

}
