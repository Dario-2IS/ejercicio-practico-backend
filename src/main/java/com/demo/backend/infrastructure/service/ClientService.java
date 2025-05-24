package com.demo.backend.infrastructure.service;

import com.demo.backend.domain.Client;

import java.util.List;

public interface ClientService {
    void saveClient(Client client);

    void updateClient(Client client);

    void deleteClient(String identificationNumber);

    Client findByIdentificationNumber(String identificationNumber);

    List<Client> findAll();
}
