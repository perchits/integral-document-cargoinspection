package com.docum.domain.po.common;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.docum.dao.ArticleDao;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
@NamedQueries(@NamedQuery(name = ArticleDao.GET_ARTICLE_FEATURES_BY_ARTICLE_QUERY, query = "SELECT DISTINCT af FROM ArticleFeature af "
		+ "WHERE af.article.id=:articleId ORDER BY af.list, af.id"))
public class ArticleFeature extends IdentifiedEntity {
	private static final long serialVersionUID = -4670814162326883296L;

	@ManyToOne(optional = false)
	private Article article;

	private String name;

	private String englishName;

	private boolean list;

	private boolean mandatory;

	@OneToMany(mappedBy = "articleFeature", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<ArticleFeatureInstance> instances = new HashSet<ArticleFeatureInstance>();

	public ArticleFeature() {
		super();
	}

	public void addInstance(ArticleFeatureInstance instance) {
		instances.add(instance);
		instance.setArticleFeature(this);
	}

	public void removeInstance(ArticleFeatureInstance instance) {
		if (instances.remove(instance)) {
			instance.setArticleFeature(null);
		}
	}

	public ArticleFeature(Article article, String name, String englishName, boolean list) {
		this.article = article;
		this.name = name;
		this.englishName = englishName;
		this.list = list;
	}

	public ArticleFeature(ArticleFeature other) {
		copy(other);
	}

	public void copy(ArticleFeature other) {
		this.setId(other.getId());
		this.article = other.article;
		this.name = other.name;
		this.englishName = other.englishName;
		this.list = other.list;
	}

	public void deepCopy(ArticleFeature other) {
		copy(other);
		if (other.getInstances() != null) {
			instances = new HashSet<ArticleFeatureInstance>(other.instances.size());
			for (ArticleFeatureInstance inst : other.getInstances()) {
				addInstance(new ArticleFeatureInstance(inst));
			}
		}		
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

	public boolean isList() {
		return list;
	}

	public void setList(boolean list) {
		this.list = list;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Set<ArticleFeatureInstance> getInstances() {
		return instances;
	}

	public void setInstances(Set<ArticleFeatureInstance> instances) {
		this.instances = instances;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof ArticleFeature)) {
			return false;
		}

		if (getId() == null || ((ArticleFeature) obj).getId() == null) {
			return false;
		}
		return EqualsHelper.equals(getId(), ((ArticleFeature) obj).getId());
	}

	public int hashCode() {
		if (getId() == null) {
			return super.hashCode();
		}
		return HashCodeHelper.hashCode(getId());
	}

	@Override
	public String getEntityName() {
		return "Характеристика";
	}

	@Override
	public GenderEnum getEntityGender() {
		return GenderEnum.FEMALE;
	}
}
