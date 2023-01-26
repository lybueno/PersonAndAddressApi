package io.github.lybueno.attornatus.repositories;

import io.github.lybueno.attornatus.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}