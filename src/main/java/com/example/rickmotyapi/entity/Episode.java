package com.example.rickmotyapi.entity;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Episode implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -504615549364622841L;

	public Episode(String url, String name) {
		super();
		this.url = url;
		this.name = name;
	}

	public Episode() {
		super();
	}

	@Id
    private String url;

    private String name;

	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setName(String name) {
		this.name = name;
	}
}


