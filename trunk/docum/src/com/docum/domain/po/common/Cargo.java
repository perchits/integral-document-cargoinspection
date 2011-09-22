package com.docum.domain.po.common;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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

	@ManyToOne(optional = false)
	private Supplier supplier;

	@ManyToOne(optional = false)
	private CargoCondition condition;
	
//	@OneToMany(mappedBy="cargo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//	private Set<CargoInspectionInfo> inspectionInfo = new HashSet<CargoInspectionInfo>();

	public Cargo() {
		super();
	}

	public Cargo(CargoCondition condition) {
		this();
		setCondition(condition);
	}

	public Cargo(Article article, Supplier supplier, CargoCondition condition) {
		this(condition);
		this.setArticle(article);
		this.supplier = supplier;
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

	public static <T> boolean checkEquality(final T first, final T second) {
		if(first != null) {
			return first.equals(second);
		} else if(second != null) {
			return second.equals(first);
		} else {
			return false;
		}
	}
	public void setCondition(CargoCondition condition) {
		if(!checkEquality(this.condition, condition)) {
			this.condition = condition;
		}
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
		if (articleCategory == null || condition == null ||
				!articleCategory.getArticle().equals(article) || //TODO что делать, если article не совпадает?
				articleCategory.equals(this.articleCategory)) {
			return;
		}
//		if(condition.isSurveyable()) {
//			inspectionInfo.resetArticleCategory(articleCategory);
//		}
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
