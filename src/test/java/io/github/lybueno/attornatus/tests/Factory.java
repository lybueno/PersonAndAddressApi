package io.github.lybueno.attornatus.tests;

import io.github.lybueno.attornatus.entities.Address;
import io.github.lybueno.attornatus.entities.Person;

import java.time.LocalDate;
import java.util.ArrayList;

public class Factory {

    public static Person createPerson(){
        Person person = new Person();
        person.setId(1L);
        person.setName("Augusta Ada Byron King");
        person.setBirthDate( LocalDate.of(1815, 12, 10));
        return person;
    }

    public static Address createAddress(){
        Address address = new Address(1L, "IT Avenue", "00111-010",1010L, true, createPerson());
        return address;
    }
}
