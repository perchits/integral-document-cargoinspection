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

	Double temperature;
		
	/**
	 * Был ли найден температурный шпион
	 */
	Boolean hasTemperatureSpy;
	
	/**
	 * Были ли различия между измеренной температурой и рекомендуемой
	 */
	Boolean hasTemperatureTestDeviation;

	/**
	 * Были ли различия между показаниями шпиона и рекомендуемой температурой
	 */
	Boolean hasTemperatureSpyDeviation;
	
	public ActualCargoCondition() {
		super();
	}

	public ActualCargoCondition(Container container) {
		this();
		this.container = container;
	}

	public ActualCargoCondition(Container container, Double temperature) {
		this.temperature = temperature;
		this.container = container;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
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

	public Boolean getHasTemperatureSpy() {
		return hasTemperatureSpy;
	}

	public void setHasTemperatureSpy(Boolean hasTemperatureSpy) {
		this.hasTemperatureSpy = hasTemperatureSpy;
	}

	public Boolean getHasTemperatureTestDeviation() {
		return hasTemperatureTestDeviation;
	}

	public void setHasTemperatureTestDeviation(Boolean hasTemperatureTestDeviation) {
		this.hasTemperatureTestDeviation = hasTemperatureTestDeviation;
	}

	public Boolean getHasTemperatureSpyDeviation() {
		return hasTemperatureSpyDeviation;
	}

	public void setHasTemperatureSpyDeviation(Boolean hasTemperatureSpyDeviation) {
		this.hasTemperatureSpyDeviation = hasTemperatureSpyDeviation;
	}
}
