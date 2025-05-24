package com.demo.backend.infrastructure.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDto {
    private String identificationNumber;
    private String firstName;
    private String lastName;
    private boolean gender;
    private int age;
    private String phoneNumber;
    private String address;
    private String password;
    private boolean state;
}
