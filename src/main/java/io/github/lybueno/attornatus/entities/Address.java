package io.github.lybueno.attornatus.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String city;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

}
