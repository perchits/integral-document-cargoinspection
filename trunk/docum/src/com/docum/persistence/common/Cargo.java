package com.docum.persistence.common;

import com.docum.persistence.DocumEntity;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Cargo
 *
 */
@Entity

public class Cargo extends DocumEntity implements Serializable {

	private static final long serialVersionUID = 4275515653210816278L;
	
	private String name;
	private String shortName;
	private String englishName;

	public Cargo() {
	}

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(nullable = false)
	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	
   
}
