package com.example.rickmotyapi.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Origin  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3691618171552081693L;

	public Origin() {
		super();
	}

	public Origin(String name, String url) {
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

