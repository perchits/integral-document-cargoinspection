package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class ActualCargoCondition extends CargoCondition {
	private static final long serialVersionUID = -6128268661397690352L;

	@OneToOne(optional=false)
	private Container container;
	
	public ActualCargoCondition() {
		super();
	}

	public ActualCargoCondition(Container container) {
		this();
		this.container = container;
	}

	public ActualCargoCondition(Container container, Double temperature) {
		super(temperature);
		this.container = container;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public boolean isSurveyable() {
		return true;
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof ActualCargoCondition)) {
			return false;
		}
		
		if(getId() == null || ((ActualCargoCondition) obj).getId() == null) {
			return false;
		}
		return EqualsHelper.equals(getId(), ((ActualCargoCondition) obj).getId());
	}

	public int hashCode() {
		if(getId() == null) {
			return super.hashCode();
		}
		return HashCodeHelper.hashCode(getId());
	}
}
