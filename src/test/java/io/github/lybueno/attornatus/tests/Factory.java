package io.github.lybueno.attornatus.tests;

import io.github.lybueno.attornatus.entities.Address;
import io.github.lybueno.attornatus.entities.Person;

import java.time.LocalDate;

public class Factory {

    public static Person createPerson(){
        Person person = new Person();
        person.setId(1L);
        person.setName("Augusta Ada Byron King");
        person.setBirthDate( LocalDate.of(1815, 12, 10));
        return person;
    }

    public static Address createAddress(){
        Address address = new Address();
        address.setId(1L);
        address.setAddress("IT Avenue");
        address.setNumber(1010L);
        address.setIsMainAddress(true);
        address.setZipCode("00111-010");
        address.setCity("London");
        address.setPerson(createPerson());
        return address;
    }
}
