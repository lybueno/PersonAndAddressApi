package io.github.lybueno.attornatus.services;

import io.github.lybueno.attornatus.dto.address.AddressDTO;
import io.github.lybueno.attornatus.dto.person.PersonDTO;
import io.github.lybueno.attornatus.entities.Address;
import io.github.lybueno.attornatus.entities.Person;
import io.github.lybueno.attornatus.repositories.AddressRepository;
import io.github.lybueno.attornatus.repositories.PersonRepository;
import io.github.lybueno.attornatus.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService service;

    @Mock
    private PersonRepository pRepository;

    @Mock
    private AddressRepository aRepository;

    private long existingId;
    private long nonExistingId;
    private PageImpl<Person> page;
    private Person person;
    private Address address;
    private PersonDTO personDTO;
    private List<Address> addressList;

    @BeforeEach
    void setup() throws Exception {

        existingId = 1L;
        nonExistingId = 1000L;
        person = Factory.createPerson();
        address = Factory.createAddress();
        page = new PageImpl<>(List.of(person));
        personDTO = new PersonDTO(person);
        addressList = new ArrayList<>();


        // For Person's tests
        Mockito.when(pRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(pRepository.save(ArgumentMatchers.any())).thenReturn(person);

        Mockito.when(pRepository.findById(existingId)).thenReturn(Optional.of(person));
        Mockito.when(pRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(pRepository.getReferenceById(existingId)).thenReturn(person);
        Mockito.when(pRepository.getReferenceById(nonExistingId)).thenThrow(ResponseStatusException.class);

        // Address
        Mockito.when(aRepository.findByPersonId(existingId)).thenReturn(addressList);
        Mockito.when(aRepository.findByPersonId(nonExistingId)).thenReturn(addressList);


    }

    @Test
    public void findAllPagedShouldReturnPage(){
        Pageable pageable = PageRequest.of(0, 10);

        Page<PersonDTO> result = service.findAllPaged(pageable);

        Assertions.assertNotNull(result);
    }

    @Test
    public void findByIdShouldReturnPersonDTOWhenIdExists(){
        PersonDTO result = service.findById(existingId);

        Assertions.assertNotNull(result);
    }

    @Test
    public void findByIdShouldThrowExceptionWhenIdDoesNotExists(){

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void updateShouldReturnPersonDTOWhenIdExists(){

        PersonDTO result = service.update(existingId, personDTO);

        Assertions.assertNotNull(result);
    }

    @Test
    public void updateShouldThrowExceptionWhenIdDoesNotExists(){

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            service.update(nonExistingId, personDTO);
        });
    }

    // Address

    @Test
    public void findAllByPersonIdShouldReturnListOfAddressesWhenIdExists(){

        List<AddressDTO> result = service.findAllByPersonId(existingId);

        Assertions.assertNotNull(result);
    }

    @Test
    public void findAllByPersonIdShouldReturnNotFoundWhenIdDoesNotExists(){

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            service.findAllByPersonId(nonExistingId);
        });
    }

}