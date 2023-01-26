package io.github.lybueno.attornatus.services;

import io.github.lybueno.attornatus.dto.PersonDTO;
import io.github.lybueno.attornatus.dto.person.PersonInsertDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    public Page<PersonDTO> findAllPaged(Pageable pageable) {
        return null;
    }

    public PersonDTO findById(Long id) {
        return null;
    }

    public PersonInsertDTO insert(PersonInsertDTO dto) {
        return null;
    }

    public PersonDTO update(Long id, PersonDTO dto) {
        return null;
    }

    public void delete(Long id) {
        // TODO
    }
}
