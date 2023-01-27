package io.github.lybueno.attornatus.controllers;

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
    public ResponseEntity<PersonInsertDTO> insert(@Valid @RequestBody PersonInsertDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
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

    @ApiOperation(value = "Deletes a person by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Person deleted"),
            @ApiResponse(code = 404, message = "Person not found")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
