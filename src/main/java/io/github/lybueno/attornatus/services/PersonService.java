package io.github.lybueno.attornatus.services;

import io.github.lybueno.attornatus.dto.address.AddressDTO;
import io.github.lybueno.attornatus.dto.person.PersonDTO;
import io.github.lybueno.attornatus.dto.person.PersonInsertDTO;
import io.github.lybueno.attornatus.entities.Address;
import io.github.lybueno.attornatus.entities.Person;
import io.github.lybueno.attornatus.repositories.AddressRepository;
import io.github.lybueno.attornatus.repositories.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository repository;

    private final AddressRepository addressRepository;

    public PersonService(PersonRepository repository, AddressRepository addressRepository) {
        this.repository = repository;
        this.addressRepository = addressRepository;
    }

    @Transactional(readOnly = true)
    public Page<PersonDTO> findAllPaged(Pageable pageable) {
        Page<Person> list = repository.findAll(pageable);
        return list.map(PersonDTO::new);
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

        Person entity = findPerson(personId);
        List<Address> list = addressRepository.findByPersonId(entity.getId());

        List<AddressDTO> dtoList = new ArrayList<>();
        for (Address address : list) {
            dtoList.add(new AddressDTO(address));
        }
        return dtoList;

    }

    @Transactional(readOnly = true)
    public AddressDTO findMainAddressByPersonId(Long personId){

        Person entity = findPerson(personId);
        List<Address> list = addressRepository.findByPersonId(entity.getId());
        for (Address address: list) {
            if(address.getIsMainAddress()){
                return new AddressDTO(address);
            }
        }

        return null;

    }

    @Transactional
    public AddressDTO createAddressToPerson(Long id, AddressDTO dto) {
        Person entity = findPerson(id);
        if(dto.isMainAddress()){
            changeAllAddressesToFalse(entity.getAddresses());
        }
        Address newAddress = new Address();
        copyDtoToEntityAddress(dto, newAddress);
        newAddress.setPerson(entity);
        addressRepository.save(newAddress);
        return new AddressDTO(newAddress);
    }

    private Person findPerson(Long id) {
        Optional<Person> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));
    }

    private void changeAllAddressesToFalse(List<Address> addresses) {
        for (Address address : addresses) {
            address.setIsMainAddress(false);
            addressRepository.save(address);
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
        if(countMainAddress == 0){
            addressDTOList.get(0).setMainAddress(true);
        } else {
            for(int j = 0; j < addressDTOList.size() - 1; j++){
                for (int i = 1; i < addressDTOList.size(); i++) {
                    if(addressDTOList.get(j).isMainAddress() && addressDTOList.get(j).isMainAddress()) {
                        addressDTOList.get(i).setMainAddress(false);
                    }
                }
            }
        }

        return addressDTOList;
    }

}
