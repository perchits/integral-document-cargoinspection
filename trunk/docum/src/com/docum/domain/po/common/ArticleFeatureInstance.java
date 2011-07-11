package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.docum.dao.ArticleDao;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
@NamedQueries(
	@NamedQuery(
			name = ArticleDao.GET_ARTICLE_FEATURE_INSTANCE_BY_ARTICLE_QUERY,
			query = "SELECT DISTINCT afi FROM ArticleFeatureInstance afi " +
				"WHERE afi.articleFeature.id=:articleFeatureId ORDER BY afi.id"
	)
)
public class ArticleFeatureInstance extends IdentifiedEntity {
	private static final long serialVersionUID = 6553357822861656964L;

	@ManyToOne(optional=false)
	private ArticleFeature articleFeature;
	
	private String name;
	
	private String englishName;
	
	public ArticleFeatureInstance() {
		super();
	}

	public ArticleFeatureInstance(ArticleFeature articleFeature, String name, String englishName) {
		this.articleFeature = articleFeature;
		this.name = name;
		this.englishName = englishName;
	}

	public ArticleFeatureInstance(ArticleFeatureInstance other) {
		copy(other);
	}

	public void copy(ArticleFeatureInstance other) {
		this.articleFeature = other.articleFeature;
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

	public ArticleFeature getArticleFeature() {
		return articleFeature;
	}

	public void setArticleFeature(ArticleFeature articleFeature) {
		this.articleFeature = articleFeature;
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof ArticleFeatureInstance)) {
			return false;
		}
		
		if(getId() == null || ((ArticleFeatureInstance) obj).getId() == null) {
			return false;
		}
		return EqualsHelper.equals(getId(), ((ArticleFeatureInstance) obj).getId());
	}

	public int hashCode() {
		if(getId() == null) {
			return super.hashCode();
		}
		return HashCodeHelper.hashCode(getId());
	}
}
