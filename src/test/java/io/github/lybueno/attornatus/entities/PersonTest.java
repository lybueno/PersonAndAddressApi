package io.github.lybueno.attornatus.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


class PersonTest {

    @Test
    public void personShouldHaveCorrectStructure(){

        Person entity = new Person();
        entity.setId(1L);
        entity.setName("Alan Mathison Turing");
        entity.setBirthDate(LocalDate.of(1912, 6, 23));

        Assertions.assertNotNull(entity.getId());
        Assertions.assertNotNull(entity.getName());
        Assertions.assertNotNull(entity.getBirthDate());
        Assertions.assertEquals(0, entity.getAddresses().size());

    }

}