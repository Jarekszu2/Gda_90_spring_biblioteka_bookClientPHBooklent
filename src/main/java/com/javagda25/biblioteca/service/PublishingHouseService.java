package com.javagda25.biblioteca.service;

import com.javagda25.biblioteca.model.PublishingHouse;
import com.javagda25.biblioteca.repository.PublishingHouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PublishingHouseService {
    private final PublishingHouseRepository publishingHouseRepository;


    public List<PublishingHouse> getAll() {
        return publishingHouseRepository.findAll();
    }

    public void save(PublishingHouse publishingHouse) {
        publishingHouseRepository.save(publishingHouse);
    }
}
