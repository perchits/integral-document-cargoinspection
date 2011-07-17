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
	
	public String getEnglishName()	{
		return this.englishName;
	}
	
	public void setEnglishName(String englishName){
		this.englishName = englishName;
	}
	
	public String getRussianName()	{
		return this.russianName;
	}
	
	public void setRussianName(String russianName){
		this.russianName = russianName;
	}
	
	public void copy(SurveyPlace sp){
		this.englishName = sp.englishName;
		this.russianName = sp.russianName;
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof SurveyPlace)) {
			return false;
		}

		return EqualsHelper.equals(getId(), ((SurveyPlace) obj).getId());
	}
	
	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
	
}
