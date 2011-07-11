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

	@ManyToOne(optional = false)
	private Supplier supplier;

	@ManyToOne(optional = false)
	private Container container;

	@OneToOne
	private DeclaredCargoCondition declaredCondition;

	@OneToOne
	private ActualCargoCondition actualCondition;

	public Cargo() {
		super();
	}

	public Cargo(Article article, Supplier supplier, Container container) {
		super();
		this.setArticle(article);
		this.supplier = supplier;
		this.container = container;
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
			this.container = cargo.container;
			this.declaredCondition = cargo.declaredCondition;
			this.actualCondition = cargo.actualCondition;
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

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public DeclaredCargoCondition getDeclaredCondition() {
		return declaredCondition;
	}

	public void setDeclaredCondition(DeclaredCargoCondition declaredCondition) {
		this.declaredCondition = declaredCondition;
	}

	public ActualCargoCondition getActualCondition() {
		return actualCondition;
	}

	public void setActualCondition(ActualCargoCondition actualCondition) {
		this.actualCondition = actualCondition;
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}

	public Set<CargoArticleFeature> getFeatures() {
		return features;
	}

	public void setFeatures(Set<CargoArticleFeature> features) {
		this.features = features;
	}

	public void addFeature(CargoArticleFeature feature) {
		features.add(feature);
		feature.setCargo(this);
	}

	public void removeFeature(CargoArticleFeature feature) {
		features.remove(feature);
		feature.setCargo(null);
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
