package com.example.rickmotyapi.service;
import com.example.rickmotyapi.entity.Episode;
import com.example.rickmotyapi.entity.Location;
import com.example.rickmotyapi.entity.Origin;
import com.example.rickmotyapi.entity.RickCharacter;
import com.example.rickmotyapi.repository.EpisodeRepository;
import com.example.rickmotyapi.repository.LocationRepository;
import com.example.rickmotyapi.repository.OriginRepository;
import com.example.rickmotyapi.repository.RickCharacterRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class RickAndMortyService {

    private final WebClient.Builder webClientBuilder;
    private final RickCharacterRepository characterRepository;
    private final OriginRepository originRepository;
    private final LocationRepository locationRepository;
    private final EpisodeRepository episodeRepository;

    @Autowired
    public RickAndMortyService(
            WebClient.Builder webClientBuilder,
            RickCharacterRepository characterRepository,
            OriginRepository originRepository,
            LocationRepository locationRepository,
            EpisodeRepository episodeRepository) {
        this.webClientBuilder = webClientBuilder;
        this.characterRepository = characterRepository;
        this.originRepository = originRepository;
        this.locationRepository = locationRepository;
        this.episodeRepository = episodeRepository;
    }

    public void fetchAndSaveCharacters() {
        webClientBuilder.baseUrl("https://rickandmortyapi.com")
                .build()
                .get()
                .uri("/api/character")
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMap(response -> {
					JsonNode characters = response.get("results");
                    for (JsonNode characterNode : characters) {
                        // Parse the Origin
                        JsonNode originNode = characterNode.get("origin");
                        Origin origin = originRepository.save(new Origin(
                                originNode.get("name").asText(),
                                originNode.get("url").asText()));

                        // Parse the Location
                        JsonNode locationNode = characterNode.get("location");
                        Location location = locationRepository.save(new Location(
                                locationNode.get("name").asText(),
                                locationNode.get("url").asText()));

                        // Parse the Episodes
                        List<Episode> episodes = new ArrayList<>();
                        characterNode.get("episode").forEach(episodeNode -> {
                            String episodeUrl = episodeNode.asText();
                            Episode episode = episodeRepository.save(new Episode(episodeUrl, episodeUrl));
                            episodes.add(episode);
                        });

                        // Create and save the Character
                        RickCharacter character = new RickCharacter();
                        character.setId(characterNode.get("id").asLong());
                        character.setName(characterNode.get("name").asText());
                        character.setStatus(characterNode.get("status").asText());
                        character.setSpecies(characterNode.get("species").asText());
                        character.setGender(characterNode.get("gender").asText());
                        character.setOrigin(origin);
                        character.setLocation(location);
                        character.setEpisodes(episodes);
                        character.setImage(characterNode.get("image").asText());
                        saveCharacter(character);
//                        characterRepository.save(character);
                    }
                    return Mono.empty();
                }).block();
    }
//    @CachePut(cacheNames = "rickcharacter", key = "#id")
    public void saveCharacter(RickCharacter character) {
    	characterRepository.save(character);
    }
    
    public void tst() {
    	System.out.print("test");
        WebClient webClient = WebClient.builder()
                .baseUrl("https://rickandmortyapi.com")
                .build();

        // Perform the GET request and block until the result is received
        Mono<JsonNode> responseMono = webClient.get()
                .uri("/api/character")
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("API error")))
                .bodyToMono(JsonNode.class)
                .flatMap(response -> {
					JsonNode characters = response.get("results");
					   for (JsonNode characterNode : characters) {
							 System.out.println("characterNode.get(\"species\").asText(): " + characterNode.get("species").asText());
					   }
                    return Mono.empty();
                });

        // Block to get the response and print it
        JsonNode response = responseMono.block();  // This blocks until the response is received
        System.out.println("Response: " + response);
    }
}
