package io.github.lybueno.attornatus.dto.person;

import io.github.lybueno.attornatus.entities.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private Long id;

    @NotBlank(message = "{field.name.required}")
    private String name;

    @NotNull(message = "{field.birthDate.required}")
    private LocalDate birthDate;

    public PersonDTO(Person entity){
        id = entity.getId();
        name = entity.getName();
        birthDate = entity.getBirthDate();
    }

}
