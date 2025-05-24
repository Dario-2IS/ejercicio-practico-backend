package com.demo.backend.infrastructure.service.impl;

import com.demo.backend.domain.Person;
import com.demo.backend.infrastructure.mapper.MapperProfile;
import com.demo.backend.infrastructure.persistence.repositories.PersonRepository;
import com.demo.backend.infrastructure.service.PersonService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final MapperProfile mapperProfile;

    @Override
    public void savePerson(Person person) {
        com.demo.backend.infrastructure.persistence.entities.Person personEntity = mapperProfile.toEntityPerson(person);
        personRepository.save(personEntity);
    }

    @Override
    public void updatePerson(String identificationNumber, String password, boolean state) {
        Optional<com.demo.backend.infrastructure.persistence.entities.Person> person = personRepository.findByIdentificationNumber(
                identificationNumber == null ? null : identificationNumber.trim()
        );
        if (person.isPresent()) {
            com.demo.backend.infrastructure.persistence.entities.Person personEntity = person.get();
            personEntity.setIdentificationNumber(identificationNumber);
            //personEntity.setPassword(password);
            //personEntity.setState(state);
            personRepository.save(personEntity);
        } else {
            throw new RuntimeException("Person not found");
        }
    }

    @Override
    public void deletePerson(String identificationNumber) {
        Optional<com.demo.backend.infrastructure.persistence.entities.Person> person = personRepository.findByIdentificationNumber(
                identificationNumber == null ? null : identificationNumber.trim()
        );
        if (person.isPresent()) {
            personRepository.delete(person.get());
        } else {
            throw new RuntimeException("Person not found");
        }
    }

    @Override
    public Person findByIdentificationNumber(String identificationNumber) {
        Optional<com.demo.backend.infrastructure.persistence.entities.Person> person = personRepository.findByIdentificationNumber(
                identificationNumber == null ? null : identificationNumber.trim()
        );

        if (person.isPresent()) {
            return mapperProfile.toDomainPerson(person.get());
        } else {
            throw new RuntimeException("Person not found");
        }
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll().stream().map(
                mapperProfile::toDomainPerson
        ).toList();
    }
}
