package com.docum.domain.po.common;

import javax.persistence.Entity;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class City extends IdentifiedEntity {
	private static final long serialVersionUID = -8223280387401163563L;
	private String name;
	private String englishName;
	private Boolean our = false;

	public City() {
	}

	public City(City city) {
		copy(city);
	}

	public City(String name, String englishName, Boolean our) {
		this.name = name;
		this.englishName = englishName;
		this.our = our;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnglishName() {
		return this.englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	public Boolean getOur() {
		return our;
	}

	public void setOur(Boolean our) {
		this.our = our;
	}

	public void copy(City city) {
		this.name = city.name;
		this.englishName = city.englishName;
		this.our = city.our;
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof City)) {
			return false;
		}

		return EqualsHelper.equals(getId(), ((City) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
}
