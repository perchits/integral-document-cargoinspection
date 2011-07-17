package com.docum.domain.po.common;

import javax.persistence.Entity;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class SurveyPlace extends IdentifiedEntity {

	private static final long serialVersionUID = 3121763126213905142L;
	
	private String englishName;
	private String russianName;
	
	public SurveyPlace() {
	}

	public SurveyPlace(SurveyPlace sp) {
		copy(sp);
	}
	
	public SurveyPlace(String englishName, String russianName) {
		this.englishName = englishName;
		this.russianName = russianName;
	}
	

	
	
	
}
