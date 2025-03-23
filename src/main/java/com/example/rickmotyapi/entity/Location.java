package com.example.rickmotyapi.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Location  implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8928195750226808037L;

	public Location(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}

	@Id
    private String name;

    private String url;

	public String getName() {
		return name;
	}

	public Location() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

