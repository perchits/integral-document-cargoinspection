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
	private CargoDefectGroup defectGroup;
	
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

	public CargoDefect(CargoDefectGroup defectGroup) {
		this();
		this.defectGroup = defectGroup;
	}

	public CargoDefect(CargoDefectGroup defectGroup, ArticleDefect articleDefect) {
		this(defectGroup);
		this.articleDefect = articleDefect;
	}

	public CargoDefect(CargoDefectGroup defectGroup, String name, String englishName) {
		this();
		this.defectGroup = defectGroup;
		this.name = name;
		this.englishName = englishName;
	}
	
	public CargoDefectGroup getDefectGroup() {
		return defectGroup;
	}

	public void setDefectGroup(CargoDefectGroup cargo) {
		this.defectGroup = cargo;
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
		CargoDefect other = (CargoDefect)obj;
		if((getId() == null || other.getId() == null) &&
				articleDefect != null && other.articleDefect != null) {
			return articleDefect.equals(other.articleDefect);
		}

		return EqualsHelper.equals(getId(), ((CargoDefect) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}

}
