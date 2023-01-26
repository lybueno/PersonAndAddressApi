package io.github.lybueno.attornatus.repositories;

import io.github.lybueno.attornatus.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
