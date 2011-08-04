package com.docum.domain.po.common;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;


@Entity
public class NormativeDocument extends IdentifiedEntity implements Serializable {
	private static final long serialVersionUID = -6892861541305786883L;
	private String name;
	private String englishName;
	
	@ManyToOne(optional=false)
	private Article article;
	

	public NormativeDocument() {
		super();
	} 
		
	public NormativeDocument(String name, String englishName) {
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
   
}
