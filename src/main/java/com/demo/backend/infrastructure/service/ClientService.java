package com.demo.backend.infrastructure.service;

import com.demo.backend.domain.Client;
import com.demo.backend.infrastructure.service.dto.ClientDto;

import java.util.List;

public interface ClientService {
    void saveClient(ClientDto clientDto);

    void updateClient(ClientDto clientDto);

    void deleteClient(String identificationNumber);

    Client findByIdentificationNumber(String identificationNumber);

    List<Client> findAll();
}
