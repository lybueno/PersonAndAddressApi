package io.github.lybueno.attornatus.dto.address;
import io.github.lybueno.attornatus.entities.Address;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private Long id;

    @NotBlank(message = "{field.address.required}")
    private String address;

    @NotNull(message = "{field.number.required}")
    private Long number;

    @NotBlank(message = "{field.zipCode.required}")
    private String zipCode;

    @NotBlank(message = "{field.city.required}")
    private String city;

    private Long personId;

    private boolean isMainAddress = false;

    public AddressDTO(Address entity){
        id = entity.getId();
        address = entity.getAddress();
        number = entity.getNumber();
        zipCode = entity.getZipCode();
        city = entity.getCity();
        personId = entity.getPerson().getId();
        isMainAddress = entity.getIsMainAddress();
    }

}
