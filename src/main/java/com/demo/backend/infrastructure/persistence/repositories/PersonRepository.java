package com.demo.backend.infrastructure.persistence.repositories;

import com.demo.backend.infrastructure.persistence.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByIdentificationNumber(String identificationNumber);
}
