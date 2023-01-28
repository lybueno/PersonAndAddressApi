package io.github.lybueno.attornatus.controllers;

import io.github.lybueno.attornatus.dto.address.AddressDTO;
import io.github.lybueno.attornatus.dto.person.PersonDTO;
import io.github.lybueno.attornatus.dto.person.PersonInsertDTO;
import io.github.lybueno.attornatus.services.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/person")
public class PersonController {

    @Autowired
    private PersonService service;

    @ApiOperation(value = "Returns list of person")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns list of person"),
            @ApiResponse(code = 404, message = "Not found.")
    })
    @GetMapping(produces="application/json")
    public ResponseEntity<Page<PersonDTO>> findAll(Pageable pageable){
        Page<PersonDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Returns a person by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns a person by ID"),
            @ApiResponse(code = 404, message = "Person not found.")
    })
    @GetMapping(value = "/{id}", produces="application/json")
    public ResponseEntity<PersonDTO> findById(@PathVariable Long id){
        PersonDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Creates person")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Person created")
    })
    @PostMapping(produces="application/json", consumes="application/json")
    public ResponseEntity<PersonDTO> insert(@Valid @RequestBody PersonInsertDTO dto){
        PersonDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @ApiOperation(value = "Updates a person by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Person updated"),
            @ApiResponse(code = 404, message = "Person not found")
    })
    @PutMapping(value = "/{id}", produces="application/json", consumes="application/json")
    public ResponseEntity<PersonDTO> update(@PathVariable Long id, @Valid @RequestBody PersonDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Returns all addresses by person ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns person's addresses"),
            @ApiResponse(code = 404, message = "Person not found")
    })
    @GetMapping(value = "/{id}/addresses", produces="application/json")
    public ResponseEntity<List<AddressDTO>> findAllAddressByPerson(@PathVariable Long id){
        return ResponseEntity.ok().body(service.findAllByPersonId(id));
    }

    @ApiOperation(value = "Returns mainaddress by person ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns person's main address"),
            @ApiResponse(code = 404, message = "Person not found")
    })
    @GetMapping(value = "/{id}/main-address", produces="application/json")
    public ResponseEntity<AddressDTO> findMainAddressByPerson(@PathVariable Long id){
        return ResponseEntity.ok().body(service.findMainAddressByPersonId(id));
    }

    @ApiOperation(value = "Creates a person's address")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Address created"),
            @ApiResponse(code = 404, message = "Person not found")
    })
    @PostMapping(value = "/{id}/address" ,produces="application/json", consumes="application/json")
    public ResponseEntity<AddressDTO> createAddress(@PathVariable Long id, @Valid @RequestBody AddressDTO dto){
        AddressDTO newDto = service.createAddressToPerson(id, dto);
       URI uri =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/address").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

}
