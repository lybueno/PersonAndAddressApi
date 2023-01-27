package io.github.lybueno.attornatus.repositories;

import io.github.lybueno.attornatus.entities.Address;
import io.github.lybueno.attornatus.tests.Factory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
class AddressRepositoryTest {

    @Autowired
    AddressRepository repository;

    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
    }

    @Order(1)
    @Test
    public void saveShouldPersistsWithAutoincrementWhenIdIsNull(){
        Address address = Factory.createAddress();
        address.setId(null);
        address = repository.save(address);

        Assertions.assertNotNull(address.getId());

    }

    @Order(2)
    @Test
    public void findByIdShouldReturnNonEmptyPersonWhenIdExists(){

        Optional<Address> result = repository.findById(existingId);

        Assertions.assertTrue(result.isPresent());
    }

    @Order(3)
    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExists() {
        Optional<Address> result = repository.findById(nonExistingId);

        Assertions.assertTrue(result.isEmpty());
    }


    @Order(4)
    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        repository.deleteById(existingId);

        Optional<Address> result = repository.findById(existingId);

        Assertions.assertFalse(result.isPresent());
    }

    @Order(5)
    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExists() {

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });
    }
}