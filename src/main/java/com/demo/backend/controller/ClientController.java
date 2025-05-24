package com.demo.backend.controller;

import com.demo.backend.domain.Client;
import com.demo.backend.infrastructure.mapper.MapperProfile;
import com.demo.backend.infrastructure.service.ClientService;
import com.demo.backend.infrastructure.service.dto.ClientDto;
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
    public ResponseEntity<List<Client>> getAll() {
        return ResponseEntity.ok(clientService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable String id) {
        return ResponseEntity.ok(clientService.findByIdentificationNumber(id));
    }

    @PostMapping
    public ResponseEntity<String> createClient(@RequestBody ClientDto client) {
        clientService.saveClient(client);
        return ResponseEntity.created(null).body("Client created successfully");
    }

    @PutMapping
    public ResponseEntity<String> updateClient(@RequestBody ClientDto client) {
        clientService.updateClient(client);
        return ResponseEntity.ok("Client updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable String id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok("Client deleted successfully");
    }
}
