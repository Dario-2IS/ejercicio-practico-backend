package com.demo.backend.infrastructure.service.impl;

import com.demo.backend.domain.Client;
import com.demo.backend.infrastructure.mapper.MapperProfile;
import com.demo.backend.infrastructure.persistence.repositories.ClientRepository;
import com.demo.backend.infrastructure.service.ClientService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public void updateClient(Client updatedClient) {
        Optional<com.demo.backend.infrastructure.persistence.entities.Client> client = clientRepository.findByIdentificationNumber(
                updatedClient.getIdentificationNumber() == null ? null : updatedClient.getIdentificationNumber().trim()
        );
        if (client.isPresent()) {
            com.demo.backend.infrastructure.persistence.entities.Client clientEntity = client.get();
            clientEntity.setFirstName(updatedClient.getFirstName());
            clientEntity.setLastName(updatedClient.getLastName());
            clientEntity.setGender(updatedClient.isGender());
            clientEntity.setAge(updatedClient.getAge());
            clientEntity.setPhoneNumber(updatedClient.getPhoneNumber());
            clientEntity.setAddress(updatedClient.getAddress());
            clientEntity.setPassword(updatedClient.getPassword());
            clientEntity.setState(updatedClient.isState());
            clientRepository.save(clientEntity);
        } else {
            throw new RuntimeException("Client not found");
        }
    }

    @Override
    public void deleteClient(String identificationNumber) {
        Optional<com.demo.backend.infrastructure.persistence.entities.Client> client = clientRepository.findByIdentificationNumber(
                identificationNumber == null ? null : identificationNumber.trim()
        );
        if (client.isPresent()) {
            clientRepository.delete(client.get());
        } else {
            throw new RuntimeException("Client not found");
        }
    }

    @Override
    public Client findByIdentificationNumber(String identificationNumber) {
        Optional<com.demo.backend.infrastructure.persistence.entities.Client> client = clientRepository.findByIdentificationNumber(
                identificationNumber == null ? null : identificationNumber.trim()
        );

        if (client.isPresent()) {
            return mapperProfile.toDomainClient(client.get());
        } else {
            throw new RuntimeException("Person not found");
        }
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll().stream().map(
                mapperProfile::toDomainClient
        ).toList();
    }
}
