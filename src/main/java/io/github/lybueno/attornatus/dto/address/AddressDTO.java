package io.github.lybueno.attornatus.dto.address;

import io.github.lybueno.attornatus.entities.Address;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "{field.address.required}")
    private String address;

    @NotBlank(message = "{field.number.required}")
    private Long number;

    @NotBlank(message = "{field.zipCode.required}")
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
