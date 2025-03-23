package com.example.rickmotyapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rickmotyapi.entity.Location;

public interface LocationRepository extends JpaRepository<Location, String> {
}
