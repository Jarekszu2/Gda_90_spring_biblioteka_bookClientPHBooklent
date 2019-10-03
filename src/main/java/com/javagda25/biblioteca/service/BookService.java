package com.javagda25.biblioteca.service;

import com.javagda25.biblioteca.model.Book;
import com.javagda25.biblioteca.model.PublishingHouse;
import com.javagda25.biblioteca.repository.BookRepository;
import com.javagda25.biblioteca.repository.PublishingHouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final PublishingHouseRepository publishingHouseRepository;

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public void saveBook(Book book, Long publishingHouseId) {
        if (publishingHouseRepository.existsById(publishingHouseId)) {
            PublishingHouse ph = publishingHouseRepository.getOne(publishingHouseId);
            book.setPublishingHouse(ph);

            bookRepository.save(book);
        }else {
            throw new EntityNotFoundException("Publishing house not found.");
        }
    }

    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }
}
