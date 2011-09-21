package com.docum.domain.po.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.domain.po.IdentifiedEntity;

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

}
