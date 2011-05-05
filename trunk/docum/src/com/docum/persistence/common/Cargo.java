package com.docum.persistence.common;

import com.docum.persistence.DocumEntity;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Cargo
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "cargo_seq")
public class Cargo extends DocumEntity implements Serializable {

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
