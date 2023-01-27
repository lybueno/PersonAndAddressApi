package io.github.lybueno.attornatus.services;

import io.github.lybueno.attornatus.dto.address.AddressDTO;
import io.github.lybueno.attornatus.dto.person.PersonDTO;
import io.github.lybueno.attornatus.dto.person.PersonInsertDTO;
import io.github.lybueno.attornatus.entities.Address;
import io.github.lybueno.attornatus.entities.Person;
import io.github.lybueno.attornatus.repositories.AddressRepository;
import io.github.lybueno.attornatus.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional(readOnly = true)
    public Page<PersonDTO> findAllPaged(Pageable pageable) {
        Page<Person> list = repository.findAll(pageable);
        return list.map(person -> new PersonDTO(person));
    }

    @Transactional(readOnly = true)
    public PersonInsertDTO findById(Long id) {
        Optional<Person> obj = repository.findById(id);
        Person entity = obj.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));
        return new PersonInsertDTO(entity, entity.getAddresses());
    }

    @Transactional
    public PersonInsertDTO insert(PersonInsertDTO dto) {
        Person entity = new Person();
        copyInsertDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new PersonInsertDTO(entity, entity.getAddresses());
    }

    public PersonDTO update(Long id, PersonDTO dto) {

        try{
            Person entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new PersonDTO(entity);
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + id + " not found");
        }

    }

    public void delete(Long id) {
        try{
            Optional<Person> person = repository.findById(id);
            List<Address> addresses = person.get().getAddresses();
            repository.deleteById(id);
            if(person.isPresent()){
                for (Address address : addresses){
                    addressRepository.deleteById(address.getId());
                }
            }
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + id + " not found");
        }
    }

    private void copyDtoToEntity(PersonDTO dto, Person entity){
        entity.setName(dto.getName());
        entity.setBirthDate(dto.getBirthDate());
    }

    private void copyInsertDtoToEntity(PersonInsertDTO dto, Person entity) {

        copyDtoToEntity(dto, entity);

        entity.getAddresses().clear();
        for(AddressDTO addressDTO : dto.getAddresses()){
            Address address = addressRepository.getReferenceById(addressDTO.getId());
            entity.getAddresses().add(address);
        }
    }
}
