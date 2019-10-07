package com.javagda25.biblioteca.controller;

import com.javagda25.biblioteca.model.Book;
import com.javagda25.biblioteca.model.BookLent;
import com.javagda25.biblioteca.service.BookLentService;
import com.javagda25.biblioteca.service.BookService;
import com.javagda25.biblioteca.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/bl/")
public class BookLentController {
    private final BookLentService bookLentService;
    private final BookService bookService;
    private final ClientService clientService;

    @GetMapping(path = "/add")
    public String addBookLent(Model model, BookLent bookLent) {
        // sprawdzam, czy ilość egzemplarzy książek > 0
        List<Book> bookList = bookService.getAll();
        List<Book> booksInReal = bookList.stream()
                .filter(book -> book.getNumberOfAvailableCopies() > 0)
                .collect(Collectors.toList());

        model.addAttribute("booklent", bookLent);
        model.addAttribute("books", booksInReal);
        model.addAttribute("clients", clientService.getList());
        return "booklent-add";
    }

    @PostMapping(path = "/add")
    public String addBookLent(BookLent bookLent, Long bookId, Long clientId) {
        bookLentService.saveBookLentByBookIdByClientId(bookLent, bookId, clientId);
        return "redirect:/bl/list";
    }

    @GetMapping(path = "/list")
    public String getAll(Model model) {
        List<BookLent> bookLents = bookLentService.findAll();
        model.addAttribute("booklents", bookLents);
        return "booklent-list";
    }

    @GetMapping(path = "/remove/{removed_id}")
    public String removeBookLent(@PathVariable(name = "removed_id") Long removed_id) {
        bookLentService.deleteBookLent(removed_id);
        return "redirect:/bl/list";
    }

    @GetMapping(path = "/return/{involved_id}")
    public String returnOfTheBook(Model model,
                                  HttpServletRequest request,
                                  @PathVariable(name = "involved_id") Long involved_id) {
        String referer = request.getHeader("referer");

        Optional<BookLent> optionalBookLent = bookLentService.getById(involved_id);
        if (optionalBookLent.isPresent()) {
            BookLent bookLent = optionalBookLent.get();
            bookLent.setDateReturned(LocalDateTime.now());
            Book book = bookLent.getBook();
            book.setNumberOfAvailableCopies(book.getNumberOfAvailableCopies() + 1);
            bookLent.setBook(book);
            bookLentService.saveBookLent(bookLent);

            if (referer != null) {
                return "redirect:" + referer;
            }
        }
        return "redirect:/bl/list";
    }

//    @PostMapping(path = "/return")
//    public String saveBookLent(BookLent bookLent) {
//        bookLentService.saveBookLent(bookLent);
//        return "redirect:/client/list";
//    }
}
