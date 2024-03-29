package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class CargoArticleFeature  extends IdentifiedEntity {
	private static final long serialVersionUID = 2407712209783643516L;

	@ManyToOne(optional=false)
	private Cargo cargo;

	@ManyToOne(optional=false)
	private ArticleFeature feature;
	
	@ManyToOne
	private ArticleFeatureInstance featureInstance;

	private String value;

	private String englishValue;

	
	public CargoArticleFeature() {
	}
	
	public CargoArticleFeature(Cargo cargo, ArticleFeature feature,
			String value, String englishValue) {		
		this(cargo, feature);
		this.value = value;
		this.englishValue = englishValue;
	}
	
	public CargoArticleFeature(Cargo cargo, ArticleFeature feature) {		
		this.cargo = cargo;
		this.feature = feature;		
	}

	public CargoArticleFeature(Cargo cargo, ArticleFeature feature,
			ArticleFeatureInstance featureInstance) {		
		this(cargo, feature);
		this.featureInstance = featureInstance;
	}
	
	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public ArticleFeature getFeature() {
		return feature;
	}

	public void setFeature(ArticleFeature articleFeature) {
		this.feature = articleFeature;
	}

	public ArticleFeatureInstance getFeatureInstance() {
		return featureInstance;
	}

	public void setFeatureInstance(ArticleFeatureInstance featureInstance) {
		this.featureInstance = featureInstance;
	}

	public String getValue() {
		return feature.isList() ? 
				featureInstance != null ? featureInstance.getName(): "" : value;
	}

	public void setValue(String featureValue) {
		this.value = featureValue;
	}

	public String getEnglishValue() {
		return feature.isList() ? featureInstance.getEnglishName() : englishValue;
	}

	public void setEnglishValue(String englishValue) {
		this.englishValue = englishValue;
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof CargoArticleFeature)) {
			return false;
		}
		
		if(getId() == null || ((CargoArticleFeature) obj).getId() == null) {
			return false;
		}
		return EqualsHelper.equals(getId(), ((CargoArticleFeature) obj).getId());
	}

	public int hashCode() {
		if(getId() == null) {
			return super.hashCode();
		}
		return HashCodeHelper.hashCode(getId());
	}
	
	public String toString(){		
		return feature != null ? feature.getName() : "";
	}
	
}
