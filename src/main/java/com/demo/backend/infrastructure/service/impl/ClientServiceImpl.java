package com.demo.backend.infrastructure.service.impl;

import com.demo.backend.domain.Client;
import com.demo.backend.infrastructure.mapper.MapperProfile;
import com.demo.backend.infrastructure.persistence.repositories.ClientRepository;
import com.demo.backend.infrastructure.service.ClientService;
import com.demo.backend.infrastructure.service.dto.ClientDto;
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
    public void saveClient(ClientDto clientDto) {
        if (clientRepository.existsByIdentificationNumber(clientDto.getIdentificationNumber())) {
            throw new IllegalArgumentException("Client already exists with this identification number.");
        }
        com.demo.backend.infrastructure.persistence.entities.Client clientEntity = mapperProfile.toEntityClient(clientDto);
        clientRepository.save(clientEntity);
    }

    @Override
    public void updateClient(ClientDto clientDto) {
        Optional<com.demo.backend.infrastructure.persistence.entities.Client> existClient = clientRepository.findByIdentificationNumber(
                clientDto.getIdentificationNumber() == null ? null : clientDto.getIdentificationNumber().trim()
        );
        if (existClient.isPresent()) {
            com.demo.backend.infrastructure.persistence.entities.Client clientEntity = mapperProfile.toEntityClient(clientDto);
            clientEntity.setId(existClient.get().getId());
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
