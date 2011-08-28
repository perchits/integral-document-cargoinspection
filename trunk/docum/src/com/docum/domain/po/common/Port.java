package com.docum.domain.po.common;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class Port extends IdentifiedEntity {

	private static final long serialVersionUID = -107225230839559627L;

	@Column(length=50)
	private String name;
	@Column(length=50)
	private String englishName;

	public Port() {
	//	super();
	}

	public Port(String name, String englishName) {
		this.name = name;
		this.englishName = englishName;
	}

	public Port(Port other) {
		copy(other);
	}

	public void copy(Port other) {
		this.name = other.name;
		this.englishName = other.englishName;
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

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Port)) {
			return false;
		}

		return EqualsHelper.equals(getId(), ((Port) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
}
