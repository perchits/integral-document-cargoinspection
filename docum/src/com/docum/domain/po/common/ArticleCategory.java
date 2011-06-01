package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.docum.dao.ArticleDao;
import com.docum.domain.po.IdentifiedEntity;

@Entity
@NamedQueries(
	@NamedQuery(
			name = ArticleDao.GET_ARTICLE_CATEGORIES_BY_ARTICLE_QUERY,
			query = "SELECT DISTINCT ac FROM ArticleCategory ac " +
				"WHERE ac.article.id=:articleId"
	)
)
public class ArticleCategory extends IdentifiedEntity {
	private static final long serialVersionUID = -3149818615410332097L;

	@ManyToOne(optional=false)
	private Article article;
	
	private String name;

	private String englishName;
	
	public ArticleCategory() {
		super();
	}

	public ArticleCategory(Article article, String name, String englishName) {
		this.article = article;
		this.name = name;
		this.englishName = englishName;
	}

	public ArticleCategory(ArticleCategory other) {
		copy(other);
	}

	public void copy(ArticleCategory other) {
		this.article = other.article;
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

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
}
