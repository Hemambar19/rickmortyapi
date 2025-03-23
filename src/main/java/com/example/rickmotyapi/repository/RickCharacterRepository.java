package com.example.rickmotyapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

import com.example.rickmotyapi.entity.RickCharacter;

//public interface RickCharacterRepository extends JpaRepository<RickCharacter, Long> {
//}
public interface RickCharacterRepository extends JpaRepository<RickCharacter, Long>, JpaSpecificationExecutor<RickCharacter> {
    // No need to add custom queries here for Specification, JpaSpecificationExecutor handles that
}
