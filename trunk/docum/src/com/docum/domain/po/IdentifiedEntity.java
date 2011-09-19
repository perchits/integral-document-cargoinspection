package com.docum.domain.po;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@MappedSuperclass
public abstract class IdentifiedEntity extends NamedEntity implements Serializable {

	private static final long serialVersionUID = 8647183098509121405L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public <T extends IdentifiedEntity> void copy(T sourceObject) {}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		else if (obj == null || !getClass().equals(obj.getClass())) {
			return false;
		}
		else {
			return EqualsHelper.equals(getId(), ((IdentifiedEntity) obj).getId());
		}
	}
	
	public int hashCode() {
		if(getId() == null) {
			return super.hashCode();
		}
		return HashCodeHelper.hashCode(getId());
	}
}
