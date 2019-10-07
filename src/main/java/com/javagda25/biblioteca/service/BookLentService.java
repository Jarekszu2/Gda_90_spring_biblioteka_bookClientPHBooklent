package com.javagda25.biblioteca.service;

import com.javagda25.biblioteca.model.Book;
import com.javagda25.biblioteca.model.BookLent;
import com.javagda25.biblioteca.model.Client;
import com.javagda25.biblioteca.repository.BookLentRepository;
import com.javagda25.biblioteca.repository.BookRepository;
import com.javagda25.biblioteca.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookLentService {
    private final BookLentRepository bookLentRepository;
    private final BookRepository bookRepository;
    private final ClientRepository clientRepository;

    public void saveBookLentByBookIdByClientId(BookLent bookLent, Long bookId, Long clientId) {
        if (bookRepository.existsById(bookId)) {
            if (clientRepository.existsById(clientId)) {
                Book book = bookRepository.getOne(bookId);
                book.setNumberOfAvailableCopies(book.getNumberOfAvailableCopies() - 1);
                Client client = clientRepository.getOne(clientId);
                bookLent.setBook(book);
                bookLent.setClient(client);

                bookLentRepository.save(bookLent);
            } else {
                throw new EntityNotFoundException("Client not found.");
            }
        } else {
            throw new EntityNotFoundException("Book not found.");
        }
    }

    public List<BookLent> findAll() {
        return bookLentRepository.findAll();
    }

    public void deleteBookLent(Long removed_id) {
        bookLentRepository.deleteById(removed_id);
    }

    public Optional<BookLent> getById(Long involved_id) {
        return bookLentRepository.findById(involved_id);
    }

    public void saveBookLent(BookLent bookLent) {
        bookLentRepository.save(bookLent);
    }

    public void saveBookLentByBookId(BookLent bookLent, Long bookId) {
        if (bookRepository.existsById(bookId)) {
            Book book = bookRepository.getOne(bookId);
            bookLent.setBook(book);
            bookLentRepository.save(bookLent);
            return;
        } else {
            throw new EntityNotFoundException("Book not found.");
        }
    }
}
