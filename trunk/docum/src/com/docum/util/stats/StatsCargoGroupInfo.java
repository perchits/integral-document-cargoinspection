package com.docum.util.stats;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoArticleFeature;

/**
 * Уникальная информация о группе грузов, является ключом в справочнике групп 
 *
 */
public class StatsCargoGroupInfo {
	private ArticleCategory category;
	private List<StatsCargoArticleFeature> features;
	
	public StatsCargoGroupInfo(Cargo cargo) {
		category = cargo.getArticleCategory();
		features = new ArrayList<StatsCargoArticleFeature>(cargo.getFeatures().size());
		for(CargoArticleFeature cargoFeature : cargo.getFeatures()) {
			features.add(new StatsCargoArticleFeature(cargoFeature));
		}
	}

	public ArticleCategory getCategory() {
		return category;
	}

	public List<StatsCargoArticleFeature> getFeatures() {
		return features;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		for (StatsCargoArticleFeature feature : features) {
			result = prime * result + ((feature == null) ? 0 : feature.hashCode());
		}
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
		StatsCargoGroupInfo other = (StatsCargoGroupInfo) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (features == null) {
			if (other.features != null)
				return false;
		} else if (features.size() != other.features.size()) {
			return false;
		} else {
			for (Iterator<StatsCargoArticleFeature> featureIterator = features.iterator(),
					otherFeatureIterator = other.features.iterator(); featureIterator.hasNext();) {
				if (!featureIterator.next().equals(otherFeatureIterator.next()))
					return false;
			}
		}
		return true;
	}
}