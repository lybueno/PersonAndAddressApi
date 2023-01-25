package io.github.lybueno.attornatus.entities;

import jakarta.persistence.*;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{field.name.required}")
    private String name;

    @Temporal(TemporalType.DATE)
    @NotBlank(message = "{field.birthDate.required}")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "person")
    private List<Address> addresses = new ArrayList<>();
}
