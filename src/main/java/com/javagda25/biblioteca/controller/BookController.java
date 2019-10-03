package com.javagda25.biblioteca.controller;

import com.javagda25.biblioteca.model.Book;
import com.javagda25.biblioteca.service.BookService;
import com.javagda25.biblioteca.service.PublishingHouseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Optional;

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

    @GetMapping("/edit/{id}")
    public String getForm(Model model, @PathVariable(name = "id") Long id) {
        Optional<Book> optionalBook = bookService.getById(id);

        if (optionalBook.isPresent()) {
            model.addAttribute("publishingHouses", publishingHouseService.getAll());
            model.addAttribute("book", optionalBook.get());

            return "book-add";
        }

        return "redirect:/book/list";
    }


    @PostMapping("/add")
    public String addBook(Book book, Long publishingHouseId) {
        bookService.saveBook(book, publishingHouseId);
        return "redirect:/book/list";
    }


}
