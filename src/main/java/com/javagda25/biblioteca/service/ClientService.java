package com.javagda25.biblioteca.service;

import com.javagda25.biblioteca.model.Client;
import com.javagda25.biblioteca.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {
    private ClientRepository clientRepository;

    public void save(Client client) {
        clientRepository.save(client);
    }

    public List<Client> getList() {
        return clientRepository.findAll();
    }

    public void delete(Long removed_client_id) {
        clientRepository.deleteById(removed_client_id);
    }

    public Optional<Client> getClientById(Long client_id) {
        return clientRepository.findById(client_id);
    }
}
