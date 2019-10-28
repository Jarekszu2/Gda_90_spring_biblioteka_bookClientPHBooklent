package com.javagda25.biblioteca.service;

import com.javagda25.biblioteca.model.Author;
import com.javagda25.biblioteca.model.Book;
import com.javagda25.biblioteca.repository.AuthorRepository;
import com.javagda25.biblioteca.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    public void save(Author author){
        authorRepository.save(author);
    }

    public Page<Author> getPage(PageRequest of) {
        return authorRepository.findAll(of);
    }

    public Optional<Author> getAuthor(Long authorId) {
        return authorRepository.findById(authorId);
    }

    public void addBookToAuthor(Long authorId, Long bookId) {
        if (!authorRepository.existsById(authorId)) {
            return;
        }
        if (!bookRepository.existsById(bookId)) {
            return;
        }
        Author author = authorRepository.getOne(authorId);
        Book book = bookRepository.getOne(bookId);

        author.getBooks().add(book);
        authorRepository.save(author);
    }

    public void removeBookFromAuthor(Long authorId, Long bookId) {
        if (!authorRepository.existsById(authorId)) {
            return;
        }
        Author author = authorRepository.getOne(authorId);
        if (!bookRepository.existsById(bookId)) {
            return;
        }
        Book book = bookRepository.getOne(bookId);

        author.getBooks().remove(book);
        authorRepository.save(author);
    }
}
