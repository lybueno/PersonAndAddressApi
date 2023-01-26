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

    private String address;

    private String zipCode;

    private Long number;

    private Boolean isMainAddress = false;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

}
