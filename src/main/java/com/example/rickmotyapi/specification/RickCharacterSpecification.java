package com.example.rickmotyapi.specification;

import com.example.rickmotyapi.entity.RickCharacter;
import org.springframework.data.jpa.domain.Specification;

public class RickCharacterSpecification {

    // Filter by name (case-insensitive)
    public static Specification<RickCharacter> hasName(String name) {
        return (root, query, criteriaBuilder) -> 
            name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    // Filter by status (exact match)
    public static Specification<RickCharacter> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> 
            status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }

    // Filter by species (exact match)
    public static Specification<RickCharacter> hasSpecies(String species) {
        return (root, query, criteriaBuilder) -> 
            species == null ? null : criteriaBuilder.equal(root.get("species"), species);
    }

    // Filter by gender (exact match)
    public static Specification<RickCharacter> hasGender(String gender) {
        return (root, query, criteriaBuilder) -> 
            gender == null ? null : criteriaBuilder.equal(root.get("gender"), gender);
    }
}
