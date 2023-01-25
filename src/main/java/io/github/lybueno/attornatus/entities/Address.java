package io.github.lybueno.attornatus.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{field.address.required}")
    private String address;

    @NotBlank(message = "{field.zipCode.required}")
    private String zipCode;

    @NotBlank(message = "{field.number.required}")
    private Long number;

    private Boolean isMainAddress = false;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

}
