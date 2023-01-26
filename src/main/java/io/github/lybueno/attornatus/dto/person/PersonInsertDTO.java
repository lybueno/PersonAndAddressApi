package io.github.lybueno.attornatus.dto.person;

import io.github.lybueno.attornatus.dto.PersonDTO;
import io.github.lybueno.attornatus.dto.address.AddressDTO;
import io.github.lybueno.attornatus.entities.Address;
import io.github.lybueno.attornatus.entities.Person;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonInsertDTO extends PersonDTO {

    private List<AddressDTO> addresses = new ArrayList<>();

    public PersonInsertDTO(Person entity, Set<Address> addresses){
        super(entity);
        addresses.forEach(address -> this.addresses.add(new AddressDTO(address)));
    }
}