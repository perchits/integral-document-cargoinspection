package com.docum.domain.po.common;

import java.io.Serializable;

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
			name = ArticleDao.GET_ARTICLE_DOCUMENTS_BY_ARTICLE_QUERY,
			query = "SELECT DISTINCT d FROM NormativeDocument d " +
			" WHERE d.article.id=:articleId ORDER BY d.id "
	)
)
public class NormativeDocument extends IdentifiedEntity implements Serializable {
	private static final long serialVersionUID = -6892861541305786883L;
	private String name;
	private String englishName;
	
	@ManyToOne(optional=false)
	private Article article;
	

	public NormativeDocument() {
		super();
	} 
		
	public NormativeDocument(Article article, String name, String englishName) {
		this.article = article;
		this.name = name;
		this.englishName = englishName;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public String getEnglishName() {
		return this.englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	public Article getArticle() {
		return this.article;
	}
	
	public void setArticle(Article article) {
		this.article = article;
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof NormativeDocument)) {
			return false;
		}

		return EqualsHelper.equals(getId(), ((NormativeDocument) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
	
	@Override
	public String getEntityName() {
		return "Документ";
	}
	
	@Override
	public GenderEnum getEntityGender() {
		return GenderEnum.MALE;
	}
   
}
