package com.demo.backend.infrastructure.service.impl;

import com.demo.backend.domain.Client;
import com.demo.backend.infrastructure.mapper.MapperProfile;
import com.demo.backend.infrastructure.persistence.repositories.ClientRepository;
import com.demo.backend.infrastructure.service.ClientService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final MapperProfile mapperProfile;
    @Override
    public void saveClient(Client client) {
        if (clientRepository.existsByIdentificationNumber(client.getIdentificationNumber())) {
            throw new IllegalArgumentException("Client already exists with this identification number.");
        }
        com.demo.backend.infrastructure.persistence.entities.Client clientEntity = mapperProfile.toEntityClient(client);
        clientRepository.save(clientEntity);
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
                mapperProfile::toDomainClient
        ).toList();
    }
}
