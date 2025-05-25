package com.demo.backend.controller;

import com.demo.backend.domain.Client;
import com.demo.backend.infrastructure.mapper.MapperProfile;
import com.demo.backend.infrastructure.service.ClientService;
import com.demo.backend.infrastructure.service.dto.ClientDto;
import com.demo.backend.infrastructure.service.template.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Client>>> getAll() {
        ApiResponse<List<Client>> response = new ApiResponse<>();
        List<Client> clients = clientService.findAll();
        response.setData(clients);
        response.setMessage("Clients retrieved successfully");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Client>> getClientById(@PathVariable String id) {
        ApiResponse<Client> response = new ApiResponse<>();
        Client client = clientService.findByIdentificationNumber(id);
        response.setData(client);
        response.setMessage("Client retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createClient(@RequestBody ClientDto client) {
        ApiResponse<Void> response = new ApiResponse<>();
        clientService.saveClient(client);
        response.setData(null);
        response.setMessage("Client created successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Void>> updateClient(@RequestBody ClientDto client) {
        ApiResponse<Void> response = new ApiResponse<>();
        clientService.updateClient(client);
        response.setData(null);
        response.setMessage("Client updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClient(@PathVariable String id) {
        ApiResponse<Void> response = new ApiResponse<>();
        clientService.deleteClient(id);
        response.setData(null);
        response.setMessage("Client deleted successfully");
        return ResponseEntity.ok(response);
    }
}
