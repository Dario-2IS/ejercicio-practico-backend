package com.demo.backend.infrastructure.service.impl;

import com.demo.backend.domain.Client;
import com.demo.backend.infrastructure.mapper.MapperProfile;
import com.demo.backend.infrastructure.persistence.repositories.ClientRepository;
import com.demo.backend.infrastructure.service.dto.ClientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private MapperProfile mapperProfile;

    @InjectMocks
    private ClientServiceImpl clientService;

    private com.demo.backend.infrastructure.persistence.entities.Client clientEntity1;
    private com.demo.backend.infrastructure.persistence.entities.Client clientEntity2;
    private Client domainClient1;
    private Client domainClient2;
    private ClientDto clientDto1;

    @BeforeEach
    void setUp() {
        clientEntity1 = new com.demo.backend.infrastructure.persistence.entities.Client();
        clientEntity1.setIdentificationNumber("123");
        clientEntity1.setFirstName("Juan");
        clientEntity1.setLastName("Doe");
        clientEntity1.setGender(true);
        clientEntity1.setAge(30);
        clientEntity1.setPhoneNumber("123456789");
        clientEntity1.setAddress("123456789");
        clientEntity1.setPassword("123456789");
        clientEntity1.setState(true);

        clientEntity2 = new com.demo.backend.infrastructure.persistence.entities.Client();
        clientEntity2.setIdentificationNumber("456");
        clientEntity2.setFirstName("Pedro");

        List<com.demo.backend.infrastructure.persistence.entities.Client> clientEntities = Arrays.asList(clientEntity1, clientEntity2);


        domainClient1 = new com.demo.backend.domain.Client();
        domainClient1.setIdentificationNumber("123");
        domainClient1.setFirstName("Juan");

        domainClient2 = new com.demo.backend.domain.Client();
        domainClient2.setIdentificationNumber("456");
        domainClient2.setFirstName("Pedro");

        List<Client> clients = Arrays.asList(domainClient1, domainClient2);

        clientDto1 = ClientDto.builder()
                .identificationNumber("123")
                .firstName("Juan")
                .lastName("Doe")
                .gender(true)
                .age(30)
                .phoneNumber("123456789")
                .address("123456789")
                .password("123456789")
                .state(true)
                .build();
    }

    @Test
    void findAll() {
        when(clientRepository.findAll())
                .thenReturn(Arrays.asList(clientEntity1, clientEntity2));
        when(mapperProfile.toDomainClient(clientEntity1))
                .thenReturn(domainClient1);
        when(mapperProfile.toDomainClient(clientEntity2))
                .thenReturn(domainClient2);

        List<com.demo.backend.domain.Client> result = clientService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("123", result.get(0).getIdentificationNumber());
        assertEquals("456", result.get(1).getIdentificationNumber());

        verify(clientRepository).findAll();
        verify(mapperProfile, times(2))
                .toDomainClient(any());
    }

    @Test
    void findByIdentificationNumber() {
        when(clientRepository.findByIdentificationNumber("123"))
                .thenReturn(Optional.of(clientEntity1));
        when(mapperProfile.toDomainClient(clientEntity1))
                .thenReturn(domainClient1);

        Client result = clientService.findByIdentificationNumber("123");

        assertNotNull(result);
        assertEquals("123", result.getIdentificationNumber());
        assertEquals("Juan", result.getFirstName());

        verify(clientRepository).findByIdentificationNumber("123");
        verify(mapperProfile).toDomainClient(clientEntity1);
    }

    @Test
    void saveClient() {
        when(clientRepository.existsByIdentificationNumber("123"))
                .thenReturn(false);
        when(mapperProfile.toEntityClient(clientDto1))
                .thenReturn(clientEntity1);
        when(clientRepository.save(clientEntity1))
                .thenReturn(any());

        clientService.saveClient(clientDto1);

        verify(clientRepository).existsByIdentificationNumber("123");
        verify(mapperProfile).toEntityClient(clientDto1);
        verify(clientRepository).save(clientEntity1);
    }

    @Test
    void updateClient() {
        when(clientRepository.findByIdentificationNumber("123"))
                .thenReturn(Optional.of(clientEntity1));
        when(mapperProfile.toEntityClient(clientDto1))
                .thenReturn(clientEntity1);
        when(clientRepository.save(clientEntity1))
                .thenReturn(any());

        clientService.updateClient(clientDto1);

        verify(clientRepository).findByIdentificationNumber("123");
        verify(mapperProfile).toEntityClient(clientDto1);
        verify(clientRepository).save(clientEntity1);
    }

    @Test
    void deleteClient() {
        when(clientRepository.findByIdentificationNumber("123"))
                .thenReturn(Optional.of(clientEntity1));

        clientService.deleteClient("123");

        verify(clientRepository).findByIdentificationNumber("123");
        verify(clientRepository).delete(clientEntity1);
    }
}