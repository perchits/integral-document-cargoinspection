package com.docum.domain.po.common;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class FileUrl extends IdentifiedEntity {
	private static final long serialVersionUID = -5504733448869479627L;

	@Column(length=512, nullable=false)
	private String value; 

	public FileUrl() {
		super();
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof FileUrl)) {
			return false;
		}

		return EqualsHelper.equals(getId(), ((FileUrl) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
}
