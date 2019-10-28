package com.javagda25.biblioteca.controller;

import com.javagda25.biblioteca.model.Author;
import com.javagda25.biblioteca.model.Book;
import com.javagda25.biblioteca.service.AuthorService;
import com.javagda25.biblioteca.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/author/")
@AllArgsConstructor
public class AuthorController {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;

    @GetMapping("/add")
    public String authorForm(Model model, Author author) {
        model.addAttribute("author", author);

        return "author-add";
    }

    @PostMapping("/add")
    public String postAuthor(Author author) {
        authorService.save(author);
        return "redirect:/author/list";
    }

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<Author> authorPage = authorService.getPage(PageRequest.of(page, size));

        model.addAttribute("authors", authorPage);
        return "author-list";
    }

    @GetMapping("/books/{id}")
    public String addAuthorsToBook(Model model,
                                   @PathVariable("id") Long authorId) {
        Optional<Author> authorOptional = authorService.getAuthor(authorId);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();

            List<Book> books = bookService.getAll();

            model.addAttribute("author", author);
            model.addAttribute("books", books);

            return "author-bookform";
        }
        return "redirect:/author/list";
    }

    @PostMapping("/addBook")
    public String addBookToAuthor(Long authorId, Long bookId, HttpServletRequest request) {
        authorService.addBookToAuthor(authorId, bookId);

        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/book/remove/{bookid}/{authorid}")
    public String removeBookFromAuthor(@PathVariable("bookid") Long bookid,
                                       @PathVariable("authorid") Long authorid,
                                       HttpServletRequest request) {
        authorService.removeBookFromAuthor(authorid, bookid);

        return "redirect:" + request.getHeader("referer");
    }
}
