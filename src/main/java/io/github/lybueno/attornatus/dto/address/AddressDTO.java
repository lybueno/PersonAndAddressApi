package io.github.lybueno.attornatus.dto.address;

import io.github.lybueno.attornatus.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private Long id;
    private String address;
    private Long number;
    private String zipCode;
    private Long personId;

    public AddressDTO(Address entity){
        id = entity.getId();
        address = entity.getAddress();
        number = entity.getNumber();
        zipCode = entity.getZipCode();
        personId = entity.getPerson().getId();
    }

}
