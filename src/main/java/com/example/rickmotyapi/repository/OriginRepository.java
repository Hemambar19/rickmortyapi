package com.example.rickmotyapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rickmotyapi.entity.Origin;


public interface OriginRepository extends JpaRepository<Origin, String> {
}

