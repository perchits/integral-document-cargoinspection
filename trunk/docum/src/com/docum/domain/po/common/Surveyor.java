package com.docum.domain.po.common;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class Surveyor extends IdentifiedEntity {

	private static final long serialVersionUID = 2790807191758592582L;
	
	@Column(length = 50)
	private String name;
	@Column(length = 50)
	private String englishName;
	
	public Surveyor() {
	}
	
	public Surveyor(Surveyor s) {
		copy(s);
	}
	
	public Surveyor(String name, String englishName) {
		this.name = name;
		this.englishName = englishName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEnglishName() {
		return englishName;
	}
	
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	public void copy(Surveyor s) {
		this.name = s.name;
		this.englishName = s.englishName;
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Surveyor)) {
			return false;
		}

		return EqualsHelper.equals(getId(), ((Surveyor) obj).getId());
	}
	
	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
	
}
