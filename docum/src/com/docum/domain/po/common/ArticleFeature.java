package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.docum.dao.ArticleDao;
import com.docum.domain.po.IdentifiedEntity;

@Entity
@NamedQueries(
	@NamedQuery(
			name = ArticleDao.GET_ARTICLE_FEATURES_BY_ARTICLE_QUERY,
			query = "SELECT DISTINCT af FROM ArticleFeature af " +
				"WHERE af.article.id=:articleId ORDER BY af.list, af.id"
	)
)
public class ArticleFeature extends IdentifiedEntity {
	private static final long serialVersionUID = -4670814162326883296L;

	@ManyToOne(optional=false)
	private Article article;
	
	private String name;
	
	private String englishName;
	
	private boolean list;
	
	private boolean mandatory;
	
	@OneToMany(mappedBy = "articleFeature")
	private List<ArticleFeatureInstance> instances = new ArrayList<ArticleFeatureInstance>();
	
	public ArticleFeature() {
		super();
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
		this.article = other.article;
		this.name = other.name;
		this.englishName = other.englishName;
		this.list = other.list;
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

	public List<ArticleFeatureInstance> getInstances() {
		return instances;
	}

	public void setInstances(List<ArticleFeatureInstance> instances) {
		this.instances = instances;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
}
