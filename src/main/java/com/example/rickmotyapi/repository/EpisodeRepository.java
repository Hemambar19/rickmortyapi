package com.example.rickmotyapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rickmotyapi.entity.Episode;

public interface EpisodeRepository extends JpaRepository<Episode, String> {
}
