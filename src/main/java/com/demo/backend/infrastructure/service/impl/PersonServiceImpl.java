package com.demo.backend.infrastructure.service.impl;

import com.demo.backend.domain.Person;
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

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @Override
    public void savePerson(Person person) {
        com.demo.backend.infrastructure.persistence.entities.Person personEntity = new com.demo.backend.infrastructure.persistence.entities.Person();
        personEntity.setIdentificationNumber(person.getIdentificationNumber());
        personEntity.setFirstName(person.getFirstName());
        personEntity.setLastName(person.getLastName());
        personEntity.setGender(person.isGender());
        personEntity.setAge(person.getAge());
        personEntity.setPhoneNumber(person.getPhoneNumber());
        personEntity.setAddress(person.getAddress());

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
            return Person.builder()
                    .identificationNumber(person.get().getIdentificationNumber())
                    .firstName(person.get().getFirstName())
                    .lastName(person.get().getLastName())
                    .gender(person.get().isGender())
                    .age(person.get().getAge())
                    .phoneNumber(person.get().getPhoneNumber())
                    .address(person.get().getAddress())
                    .build();
        } else {
            throw new RuntimeException("Person not found");
        }
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll().stream().map(
                person -> new Person(
                        person.getIdentificationNumber(),
                        person.getFirstName(),
                        person.getLastName(),
                        person.isGender(),
                        person.getAge(),
                        person.getPhoneNumber(),
                        person.getAddress()
                )
        ).toList();
    }
}
