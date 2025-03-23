package com.example.rickmotyapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.rickmotyapi.cache.TTLCache;
import com.example.rickmotyapi.entity.RickCharacter;
import com.example.rickmotyapi.service.RickAndMortyService;
import com.example.rickmotyapi.service.RickCharacterService;

@RestController("/rickapi")
@CacheConfig(cacheNames = "rickcharacter")
public class CharacterController {

    private final RickAndMortyService rickAndMortyService;

    private final RickCharacterService rickCharacterService;
    
    TTLCache<String, String> ttlCache = new TTLCache<>(1000);
    
    @Autowired
    public CharacterController(RickAndMortyService rickAndMortyService,RickCharacterService rickCharacterService) {
        this.rickAndMortyService = rickAndMortyService;
        this.rickCharacterService = rickCharacterService;
    }
    
    @GetMapping("/hello")
    public String hello() {
//        rickAndMortyService.fetchAndSaveCharacters();
//    	rickAndMortyService.tst();
        return "Fetching characters and saving to database!";
    }

    @GetMapping("/store-characters")
    public String storeAndSaveCharacters() {
        rickAndMortyService.fetchAndSaveCharacters();
        return "Fetching characters and saving to database!";
    }
    
    @GetMapping("/fetch-characters")
    public Page<RickCharacter> getRickCharacters(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "species", required = false) String species,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {

        // Create sorting based on the parameters
        Sort sort = "desc".equalsIgnoreCase(sortDir) ? Sort.by(Sort.Order.desc(sortBy)) : Sort.by(Sort.Order.asc(sortBy));

        // Create Pageable instance
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // Get the filtered, paginated, and sorted results
        return rickCharacterService.getRickCharacters(name, status, species, gender, pageRequest);
    }

    // Get a single RickCharacter by ID
    @GetMapping("/{id}")
    public RickCharacter getRickCharacterById(@PathVariable Long id) {
        return rickCharacterService.getRickCharacterById(id).orElseThrow(() -> new RuntimeException("Character not found"));
    }
}

