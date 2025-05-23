package com.demo.backend.infrastructure.service;

import com.demo.backend.domain.Person;

import java.util.List;
public interface PersonService {
    void savePerson(Person person);

    void updatePerson(String identificationNumber, String password, boolean state);

    void deletePerson(String identificationNumber);

    Person findByIdentificationNumber(String identificationNumber);

    List<Person> findAll();
}
