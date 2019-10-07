package com.javagda25.biblioteca.repository;

import com.javagda25.biblioteca.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
