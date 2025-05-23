package com.demo.backend.infrastructure.service.impl;

import com.demo.backend.domain.Client;
import com.demo.backend.infrastructure.persistence.repositories.ClientRepository;
import com.demo.backend.infrastructure.service.ClientService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    @Override
    public void saveClient(Client client) {

    }

    @Override
    public void updateClient(String identificationNumber, String password, boolean state) {

    }

    @Override
    public void deleteClient(String identificationNumber) {

    }

    @Override
    public Client findByIdentificationNumber(String identificationNumber) {
        return null;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll().stream().map(
                clientEntity -> {
                    Client client = new Client();
                    client.setIdentificationNumber(clientEntity.getIdentificationNumber());
                    client.setFirstName(clientEntity.getFirstName());
                    client.setLastName(clientEntity.getLastName());
                    client.setGender(clientEntity.isGender());
                    client.setAge(clientEntity.getAge());
                    client.setPhoneNumber(clientEntity.getPhoneNumber());
                    client.setAddress(clientEntity.getAddress());
                    return client;
                }
        ).toList();
    }
}
