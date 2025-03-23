package com.example.rickmotyapi.entity;
import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class RickCharacter  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7585854084066876742L;

	@Id
    private Long id;
    private String name;
    private String status;
    private String species;
    private String gender;

    @ManyToOne
    @JoinColumn(name = "origin_id")
    private Origin origin;

    public RickCharacter() {
		super();
	}

	@ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToMany
    @JoinTable(
        name = "character_episode",
        joinColumns = @JoinColumn(name = "character_id"),
        inverseJoinColumns = @JoinColumn(name = "episode_id")
    )
    private List<Episode> episodes;

    private String image;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}

	public String getSpecies() {
		return species;
	}

	public String getGender() {
		return gender;
	}

	public Origin getOrigin() {
		return origin;
	}

	public Location getLocation() {
		return location;
	}

	public List<Episode> getEpisodes() {
		return episodes;
	}

	public String getImage() {
		return image;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}

	public void setImage(String image) {
		this.image = image;
	}

}

