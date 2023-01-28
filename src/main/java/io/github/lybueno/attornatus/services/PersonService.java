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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
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
    public PersonDTO findById(Long id) {
        Optional<Person> obj = repository.findById(id);
        Person entity = obj.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));
        return new PersonDTO(entity);
    }

    @Transactional
    public PersonDTO insert(PersonInsertDTO dto) {
        if(dto.getAddresses().size() > 0){
            List<AddressDTO> proceddedListAddress = processAddresses(dto);
            dto.setAddresses(proceddedListAddress);
        }
        Person entity = new Person();
        copyInsertDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new PersonDTO(entity);
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

    @Transactional(readOnly = true)
    public List<AddressDTO> findAllByPersonId(Long personId){

        Optional<Person> obj = repository.findById(personId);
        Person entity = obj.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));
        List<Address> list = addressRepository.findByPersonId(personId);

        List<AddressDTO> dtoList = new ArrayList<>();
        for (Address address : list) {
            dtoList.add(new AddressDTO(address));
        }
        return dtoList;

    }

    private void copyDtoToEntity(PersonDTO dto, Person entity){
        entity.setName(dto.getName());
        entity.setBirthDate(dto.getBirthDate());
    }

    private void copyInsertDtoToEntity(PersonInsertDTO dto, Person entity) {

        copyDtoToEntity(dto, entity);
        entity.getAddresses().clear();
        for(AddressDTO addressDTO : dto.getAddresses()){
            Address address = new Address();
            copyDtoToEntityAddress(addressDTO, address);
            address.setPerson(entity);
            addressRepository.save(address);
            entity.getAddresses().add(address);
        }
    }

    private void copyDtoToEntityAddress(AddressDTO dto, Address entity) {

        entity.setAddress(dto.getAddress());
        entity.setNumber(dto.getNumber());
        entity.setZipCode(dto.getZipCode());
        entity.setCity(dto.getCity());
        entity.setIsMainAddress(dto.isMainAddress());

    }

    private List<AddressDTO> processAddresses(PersonInsertDTO dto) {
        List<AddressDTO> addressDTOList = dto.getAddresses();
        int countMainAddress = 0;
        for (AddressDTO address: addressDTOList) {
            if(address.isMainAddress()){
                countMainAddress++;
            }
        }
        if(countMainAddress != 1){
            addressDTOList.get(0).setMainAddress(true);
        }
        if(addressDTOList.size() > 1){
            for (int i = 1; i < addressDTOList.size(); i++) {
                addressDTOList.get(i).setMainAddress(false);
            }
        }
        return addressDTOList;
    }
}
