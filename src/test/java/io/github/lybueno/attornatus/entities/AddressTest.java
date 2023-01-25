package io.github.lybueno.attornatus.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    public void addressShouldHaveCorrectStructure(){

        Address entity = new Address();

        entity.setId(1L);
        entity.setAddress("Charles Road");
        entity.setNumber(20L);
        entity.setZipCode("12.345-678");
        entity.setIsMainAddress(true);
        entity.setPerson(new Person());

        Assertions.assertNotNull(entity.getId());
        Assertions.assertNotNull(entity.getAddress());
        Assertions.assertNotNull(entity.getNumber());
        Assertions.assertNotNull(entity.getZipCode());
        Assertions.assertNotNull(entity.getIsMainAddress());
        Assertions.assertNotNull(entity.getPerson());

    }

}