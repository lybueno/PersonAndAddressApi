package io.github.lybueno.attornatus.dto;

import io.github.lybueno.attornatus.entities.Person;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private Long id;

    @NotBlank(message = "{field.name.required}")
    private String name;

    @NotBlank(message = "{field.birthDate.required}")
    private LocalDate birthDate;

    public PersonDTO(Person entity){
        id = entity.getId();
        name = entity.getName();
        birthDate = entity.getBirthDate();
    }

}
