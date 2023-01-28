package io.github.lybueno.attornatus.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.lybueno.attornatus.dto.address.AddressDTO;
import io.github.lybueno.attornatus.dto.person.PersonDTO;
import io.github.lybueno.attornatus.services.PersonService;
import io.github.lybueno.attornatus.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingId;
    private Long nonExistingId;
    private PersonDTO personDTO;
    private PageImpl<PersonDTO> page;
    private List<AddressDTO> list;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() throws Exception {

        existingId = 1L;
        nonExistingId = 1000L;
        personDTO = new PersonDTO(Factory.createPerson());
        page = new PageImpl<>(List.of(personDTO));

        when(service.findAllPaged(any())).thenReturn(page);

        when(service.findById(existingId)).thenReturn(personDTO);
        when(service.findById(nonExistingId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));


        when(service.update(eq(existingId), any())).thenReturn(personDTO);
        when(service.update(eq(nonExistingId), any())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Person not found"));

        when(service.insert(any())).thenReturn(personDTO);

        // Address

        when(service.findAllByPersonId(any())).thenReturn(list);

        addressDTO = new AddressDTO(Factory.createAddress());
        when(service.findMainAddressByPersonId(existingId)).thenReturn(addressDTO);
        when(service.findMainAddressByPersonId(nonExistingId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Person " +
               "not found"));

        when(service.createAddressToPerson(eq(existingId), any())).thenReturn(addressDTO);
        when(service.createAddressToPerson(eq(nonExistingId), any())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Person not found"));
    }

    // methods related to Person

    @Test
    public void findAllShouldReturnPage() throws Exception {

        ResultActions result = mockMvc.perform(get("/person")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnPersonWhenIdExists() throws Exception{

        ResultActions result = mockMvc.perform(get("/person/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.birthDate").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {

        ResultActions result = mockMvc.perform(get("/person/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void updateShouldReturnPersonDTOWhenIdExists() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(personDTO);

        ResultActions result = mockMvc.perform(put("/person/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.birthDate").exists());
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(personDTO);

        ResultActions result = mockMvc.perform(put("/person/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());

    }

    @Test
    public void insertShouldReturnPersontDTOCreated() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(personDTO);

        ResultActions result = mockMvc.perform(post("/person")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.birthDate").exists());
    }

    // methods related to Address

    @Test
    public void findByPersonIdShouldReturnAllPersonAddressWhenIdExists() throws Exception{

        ResultActions result = mockMvc.perform(get("/person/{id}/addresses", existingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void findMainAddressByPersonIdShouldReturnAddressWhenIdExists() throws Exception{

        ResultActions result = mockMvc.perform(get("/person/{id}/main-address", existingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.address").exists());
        result.andExpect(jsonPath("$.number").exists());
        result.andExpect(jsonPath("$.zipCode").exists());
        result.andExpect(jsonPath("$.city").exists());
        result.andExpect(jsonPath("$.mainAddress").exists());
    }

    @Test
    public void findMainAddressByPersonIShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {

        ResultActions result = mockMvc.perform(get("/person/{id}/main-address", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void createAddressShouldReturnAddressDTOCreatedWhenPersonIdExists() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(addressDTO);

        ResultActions result = mockMvc.perform(post("/person/{id}/address", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.address").exists());
        result.andExpect(jsonPath("$.number").exists());
        result.andExpect(jsonPath("$.zipCode").exists());
        result.andExpect(jsonPath("$.city").exists());
        result.andExpect(jsonPath("$.mainAddress").exists());
    }
    
}