package com.javagda25.biblioteca.repository;

import com.javagda25.biblioteca.model.BookLent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLentRepository extends JpaRepository<BookLent, Long> {
}
