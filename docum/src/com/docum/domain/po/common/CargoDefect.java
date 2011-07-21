package com.docum.domain.po.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class CargoDefect extends IdentifiedEntity{
	private static final long serialVersionUID = 4390793129262143235L;

	@ManyToOne(optional=false)
	private Cargo cargo;
	
	@ManyToOne(optional=true)
	private ArticleDefect articleDefect;
	
	@Column(length=512)
	private String name;
	
	@Column(length=512)
	private String englishName;
	
	private double percentage;

	public CargoDefect() {
		super();
	}

	public CargoDefect(Cargo cargo) {
		this();
		this.cargo = cargo;
	}

	public CargoDefect(Cargo cargo, ArticleDefect articleDefect) {
		this();
		this.cargo = cargo;
		this.articleDefect = articleDefect;
	}

	public CargoDefect(Cargo cargo, String name, String englishName) {
		this();
		this.cargo = cargo;
		this.name = name;
		this.englishName = englishName;
	}
	
	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public ArticleDefect getArticleDefect() {
		return articleDefect;
	}

	public void setArticleDefect(ArticleDefect articleDefect) {
		this.articleDefect = articleDefect;
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

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof CargoDefect)) {
			return false;
		}

		return EqualsHelper.equals(getId(), ((CargoDefect) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}

}
