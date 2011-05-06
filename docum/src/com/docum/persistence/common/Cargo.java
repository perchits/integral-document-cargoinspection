package com.docum.persistence.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.docum.persistence.IdentifiedEntity;

/**
 * Entity implementation class for Entity: Cargo
 *
 */
@Entity
public class Cargo extends IdentifiedEntity {

	private static final long serialVersionUID = 4275515653210816278L;
	
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String shortName;

	@Column(nullable = false)
	private String englishName;

	public Cargo() {
	}
	
	public Cargo(String name, String shortName, String englishName) {
		this.name = name;
		this.shortName = shortName;
		this.englishName = englishName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	
   
}
