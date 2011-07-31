package com.docum.domain.po.common;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.docum.domain.po.OrderedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class CargoDefectGroup extends OrderedEntity {
	private static final long serialVersionUID = -1289620501327722936L;

	@ManyToOne(optional=false)
	private Cargo cargo;
	
	@ManyToOne(optional=false)
	private ArticleCategory articleCategory;

	@OneToMany(mappedBy = "defectGroup", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<CargoDefect> defects = new HashSet<CargoDefect>();

	public CargoDefectGroup() {
		super();
	}

	public CargoDefectGroup(Cargo cargo) {
		this();
		this.cargo = cargo;
	}

	public CargoDefectGroup(Cargo cargo, ArticleCategory articleCategory, int ord) {
		this(cargo);
		setArticleCategory(articleCategory);
		setOrd(ord);
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		if (articleCategory == null || articleCategory.equals(this.articleCategory)) {
			return;
		}
		this.articleCategory = articleCategory;
		defects.clear();
		for(ArticleDefect articleDefect : articleCategory.getDefects()) {
			defects.add(new CargoDefect(this, articleDefect));
		}
	}
	
	public Set<CargoDefect> getDefects() {
		return defects;
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof CargoDefectGroup)) {
			return false;
		}

		return EqualsHelper.equals(getId(), ((CargoDefectGroup) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}

}
