package com.docum.util.stats;

import com.docum.domain.po.common.ArticleFeatureInstance;
import com.docum.domain.po.common.CargoArticleFeature;

public class StatsCargoArticleFeature {
	public StatsCargoArticleFeature(CargoArticleFeature cargoFeature) {
		super();
		this.featureInstance = cargoFeature.getFeatureInstance();
		this.value = cargoFeature.getValue();
		this.englishValue = cargoFeature.getEnglishValue();
	}

	private ArticleFeatureInstance featureInstance;
	private String value;
	private String englishValue;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((englishValue == null) ? 0 : englishValue.hashCode());
		result = prime * result + ((featureInstance == null) ? 0 : featureInstance.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatsCargoArticleFeature other = (StatsCargoArticleFeature) obj;
		if (englishValue == null) {
			if (other.englishValue != null)
				return false;
		} else if (!englishValue.equals(other.englishValue))
			return false;
		if (featureInstance == null) {
			if (other.featureInstance != null)
				return false;
		} else if (!featureInstance.equals(other.featureInstance))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
