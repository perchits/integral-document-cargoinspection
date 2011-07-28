package com.docum.domain.po.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class ArticleDefect extends IdentifiedEntity {
	private static final long serialVersionUID = -3496504667598232471L;
	
	@ManyToOne(optional=false)
	private ArticleCategory category;
	
	@Column(length=512)
	private String name;
	
	@Column(length=512)
	private String englishName;

	public ArticleDefect() {
		super();
	}
	
	public ArticleDefect(ArticleCategory category) {
		this();
		this.category = category;
	}

	public ArticleCategory getCategory() {
		return category;
	}

	public void setCategory(ArticleCategory category) {
		this.category = category;
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
	
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof ArticleDefect)) {
			return false;
		}
		
		if(getId() == null || ((ArticleDefect) obj).getId() == null) {
			return false;
		}
		return EqualsHelper.equals(getId(), ((ArticleDefect) obj).getId());
	}

	public int hashCode() {
		if(getId() == null) {
			return super.hashCode();
		}
		return HashCodeHelper.hashCode(getId());
	}
	
	@Override
	public String getEntityName() {
		return "Дефект";
	}
	
	@Override
	public GenderEnum getEntityGender() {
		return GenderEnum.MALE;
	}
}
