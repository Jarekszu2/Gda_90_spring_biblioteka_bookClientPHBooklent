package com.javagda25.biblioteca.service;

import com.javagda25.biblioteca.model.PublishingHouse;
import com.javagda25.biblioteca.repository.PublishingHouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<PublishingHouse> getById(Long id) {
        return publishingHouseRepository.findById(id);
    }

    public void remove(Long id) {
        if (publishingHouseRepository.existsById(id)) {
            PublishingHouse publishingHouse = publishingHouseRepository.getOne(id);
            if (publishingHouse.getBooks().size() == 0) {
                publishingHouseRepository.deleteById(id);
            }else{
                System.err.println("Unable to remove publishing house with assigned books to it.");
            }
        }
    }
}
