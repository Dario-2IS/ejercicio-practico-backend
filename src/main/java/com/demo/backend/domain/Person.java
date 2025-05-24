package com.demo.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String identificationNumber;
    private String firstName;
    private String lastName;
    private boolean gender;
    private int age;
    private String phoneNumber;
    private String address;
}
