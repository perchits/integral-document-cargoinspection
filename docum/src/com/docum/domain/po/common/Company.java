package com.docum.domain.po.common;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class Company extends IdentifiedEntity {

	private static final long serialVersionUID = -4764758796308063584L;
	private String name;
	private String shortName;
	private String englishName;
	private String englishShortName;
	@Column(length = 500)
	private String address;
	@Column(length = 500)
	private String englishAddress;

	public Company() {
	}

	public Company(String name, String shortName, String englishName,
			String englishShortName, String address, String englishAddress) {
		this.name = name;
		this.shortName = shortName;
		this.englishName = englishName;
		this.englishShortName = englishShortName;
		this.address = address;
		this.englishAddress = englishAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEnglishAddress() {
		return englishAddress;
	}

	public void setEnglishAddress(String englishAddress) {
		this.englishAddress = englishAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getEnglishShortName() {
		return englishShortName;
	}

	public void setEnglishShortName(String englishShortName) {
		this.englishShortName = englishShortName;
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Company)) {
			return false;
		}
		
		return EqualsHelper.equals(getId(), ((Company) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
}
