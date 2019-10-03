package com.javagda25.biblioteca.controller;

import com.javagda25.biblioteca.model.Book;
import com.javagda25.biblioteca.service.BookService;
import com.javagda25.biblioteca.service.PublishingHouseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/book/")
public class BookController {
    private final BookService bookService;
    private final PublishingHouseService publishingHouseService;

    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("books", bookService.getAll());

        return "book-list";
    }


    @GetMapping("/add")
    public String getForm(Model model, Book book) {
        book.setYearWritten(LocalDate.now().getYear());

        model.addAttribute("publishingHouses", publishingHouseService.getAll());
        model.addAttribute("book", book);

        return "book-add";
    }


    @PostMapping("/add")
    public String addBook(Book book) {
        book.setYearWritten(LocalDate.now().getYear());

        model.addAttribute("publishingHouses", publishingHouseService.getAll());
        model.addAttribute("book", book);

        return "book-add";
    }


}
