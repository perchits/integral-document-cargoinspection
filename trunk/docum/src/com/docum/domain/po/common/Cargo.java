package com.docum.domain.po.common;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class Cargo extends IdentifiedEntity {
	private static final long serialVersionUID = 4275515653210816278L;

	@ManyToOne(optional = false)
	private Article article;

	@ManyToOne
	private ArticleCategory articleCategory;

	@OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<CargoArticleFeature> features = new HashSet<CargoArticleFeature>();

	@OneToMany(mappedBy="cargo", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private Set<CargoPackage> cargoPackages = new HashSet<CargoPackage>();

	@OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<CargoDefect> defects = new HashSet<CargoDefect>();
	
	@ManyToOne(optional = false)
	private Supplier supplier;

	@ManyToOne(optional = false)
	private CargoCondition condition;

	public Cargo() {
		super();
	}

	public Cargo(Article article, Supplier supplier, CargoCondition condition) {
		super();
		this.setArticle(article);
		this.supplier = supplier;
		this.condition = condition;
	}

	public Cargo(Cargo cargo) {
		copy(cargo);
	}

	public void copy(Cargo cargo) {
		if (cargo != null) {
			this.setId(cargo.getId());
			this.article = cargo.article;
			this.articleCategory = cargo.articleCategory;
			this.features = cargo.features;
			this.supplier = cargo.supplier;
			this.condition = cargo.condition;
			this.cargoPackages = cargo.cargoPackages;
		}
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		if (article == null || article.equals(this.article)) {
			return;
		}
		this.features.clear();
		for (ArticleFeature feature : article.getFeatures()) {
			this.addFeature(new CargoArticleFeature(this, feature));
		}
		this.article = article;
	}

	public CargoCondition getCondition() {
		return condition;
	}

	public void setCondition(CargoCondition condition) {
		this.condition = condition;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		if (articleCategory == null || articleCategory.equals(this.articleCategory)) {
			return;
		}
		if(condition.hasDefects()) {
			defects.clear();
			for(ArticleDefect articleDefect : articleCategory.getDefects()) {
				defects.add(new CargoDefect(this, articleDefect));
			}
		}
		this.articleCategory = articleCategory;
	}

	public Set<CargoArticleFeature> getFeatures() {
		return features;
	}

	public void setFeatures(Set<CargoArticleFeature> features) {
		this.features = features;
	}

	public void addFeature(CargoArticleFeature feature) {
		if(feature != null) {
			features.add(feature);
			feature.setCargo(this);
		}
	}

	public void removeFeature(CargoArticleFeature feature) {
		if(feature != null && features.remove(feature)) {
			features.remove(feature);
			feature.setCargo(null);
		}
	}

	public Set<CargoPackage> getCargoPackages() {
		return cargoPackages;
	}

	public void setCargoPackages(Set<CargoPackage> cargoPackages) {		
		this.cargoPackages = cargoPackages;
	}

	public void addPackage(CargoPackage cargoPackage){
		if(cargoPackage != null) {
			cargoPackages.add(cargoPackage);
			cargoPackage.setCargo(this);
		}
	}

	public void removePackage(CargoPackage cargoPackage){
		if(cargoPackage != null && cargoPackages.remove(cargoPackage)) {
			cargoPackage.setCargo(null);
		}
	}
	
	public Set<CargoDefect> getDefects() {
		return defects;
	}

	public void setDefects(Set<CargoDefect> defects) {
		this.defects = defects;
	}

	public void addDefect(CargoDefect defect){
		if(defect != null && defect.getArticleDefect() == null) {
			defects.add(defect);
			defect.setCargo(this);
		}
	}

	public void removeDefect(CargoDefect defect){
		if(defect != null && defect.getArticleDefect() == null &&
				cargoPackages.remove(defect)) {
			defect.setCargo(null);
		}
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Cargo)) {
			return false;
		}

		return EqualsHelper.equals(getId(), ((Cargo) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}

	@Override
	public String toString() {
		return article != null ? article.getName() : "";
	}
}
