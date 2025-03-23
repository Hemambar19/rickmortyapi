package com.example.rickmotyapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.rickmotyapi.entity.RickCharacter;
import com.example.rickmotyapi.repository.RickCharacterRepository;
import com.example.rickmotyapi.specification.RickCharacterSpecification;

@Service
public class RickCharacterService {

    @Autowired
    private RickCharacterRepository rickCharacterRepository;

    // Fetch all RickCharacters with paging, filtering, and sorting
//    @Cacheable(value = "RickCharacter", key = "#name ?: 'defaultName' + ':' + #status ?: 'defaultStatus' + ':' + #species ?: 'defaultSpecies' + ':' + #gender ?: 'defaultGender' + ':' + #page + ':' + #size + ':' + #sortBy + ':' + #sortDir")
//    @Cacheable(value = "rickCharacters", key = "#name + #status + #species + #gender + #page + #size + #sortBy + #sortDir")
    public Page<RickCharacter> getRickCharacters(String name, String status, String species, String gender,
                                                 Pageable pageable) {
        // Create the dynamic specification based on filters
        Specification<RickCharacter> spec = Specification.where(RickCharacterSpecification.hasName(name))
                .and(RickCharacterSpecification.hasStatus(status))
                .and(RickCharacterSpecification.hasSpecies(species))
                .and(RickCharacterSpecification.hasGender(gender));

        return rickCharacterRepository.findAll(spec, pageable);
    }

    // Fetch a RickCharacter by ID
    public Optional<RickCharacter> getRickCharacterById(Long id) {
        return rickCharacterRepository.findById(id);
    }

    // Save a new or updated RickCharacter
    public RickCharacter saveRickCharacter(RickCharacter rickCharacter) {
        return rickCharacterRepository.save(rickCharacter);
    }

    // Delete a RickCharacter by ID
    public void deleteRickCharacter(Long id) {
        rickCharacterRepository.deleteById(id);
    }
}
