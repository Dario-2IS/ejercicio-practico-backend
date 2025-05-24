package com.demo.backend.controller;

import com.demo.backend.domain.Person;
import com.demo.backend.infrastructure.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping
    public List<Person> getAll() {
        return personService.findAll();
    }

}
