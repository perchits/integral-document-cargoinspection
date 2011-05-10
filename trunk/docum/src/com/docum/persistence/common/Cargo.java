package com.docum.persistence.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.persistence.IdentifiedEntity;

/**
 * Entity implementation class for Entity: Cargo
 *
 */
@Entity
public class Cargo extends IdentifiedEntity {
	private static final long serialVersionUID = 4275515653210816278L;
	
	private Article article;

	private Double weight;
	
	private Supplier supplier;
	
	@ManyToOne
	private Container container;
		
	public Cargo(){
		super();
	}
	
	public Article getArticle() {
		return article;
	}


	public void setArticle(Article goods) {
		this.article = goods;
	}


	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
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
   
}
