package io.github.lybueno.attornatus.repositories;

import io.github.lybueno.attornatus.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByPersonId(Long personId);
}
