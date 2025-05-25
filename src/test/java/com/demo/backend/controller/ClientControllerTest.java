package com.demo.backend.controller;

import com.demo.backend.domain.Client;
import com.demo.backend.infrastructure.service.ClientService;
import com.demo.backend.infrastructure.service.dto.ClientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private Client testClient1;
    private Client testClient2;
    private ClientDto testclientDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testClient1 = new Client();
        testClient1.setIdentificationNumber("123");
        testClient1.setFirstName("Juan");

        testClient2 = new Client();
        testClient2.setIdentificationNumber("456");
        testClient2.setFirstName("Pedro");

        testclientDto = ClientDto.builder()
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
    void getAll() {
        when(clientService.findAll())
                .thenReturn(Arrays.asList(testClient1, testClient2));

        ResponseEntity<List<Client>> response = clientController.getAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("123", response.getBody().get(0).getIdentificationNumber());
        assertEquals("456", response.getBody().get(1).getIdentificationNumber());

        verify(clientService).findAll();
    }

    @Test
    void getClientById() {
        when(clientService.findByIdentificationNumber("123"))
                .thenReturn(testClient1);

        ResponseEntity<Client> response = clientController.getClientById("123");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("123", response.getBody().getIdentificationNumber());
        assertEquals("Juan", response.getBody().getFirstName());

        verify(clientService).findByIdentificationNumber("123");
    }

    @Test
    void createClient() {

        doNothing().when(clientService).saveClient(testclientDto);

        ResponseEntity<String> response = clientController.createClient(testclientDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Client created successfully", response.getBody());

        verify(clientService).saveClient(testclientDto);
    }

    @Test
    void updateClient() {

        ResponseEntity<String> response = clientController.updateClient(testclientDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Client updated successfully", response.getBody());

        verify(clientService).updateClient(testclientDto);
    }

    @Test
    void deleteClient() {
        ResponseEntity<String> response = clientController.deleteClient("123");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Client deleted successfully", response.getBody());

        verify(clientService).deleteClient("123");
    }
}