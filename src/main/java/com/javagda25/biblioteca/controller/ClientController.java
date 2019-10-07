package com.javagda25.biblioteca.controller;

import com.javagda25.biblioteca.model.Book;
import com.javagda25.biblioteca.model.BookLent;
import com.javagda25.biblioteca.model.Client;
import com.javagda25.biblioteca.repository.ClientRepository;
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

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/client/")
public class ClientController {
    private final ClientService clientService;
    private final BookService bookService;
    private final BookLentService bookLentService;

    @GetMapping(path = "/add")
    public String getForm_AddClient(Model model, Client client) {
        model.addAttribute("client", client);
        return "client-add";
    }

    @PostMapping(path = "/add")
    public String saveAddedClient(Client client) {
        clientService.save(client);
        return "redirect:/client/list";
    }

    @GetMapping(path = "/list")
    public String list(Model model) {
        List<Client> clients = clientService.getList();
        model.addAttribute("clients", clients);
        return "client-list";
    }

    @GetMapping(path = "/remove/{removed_client_id}")
    public String removeClient(@PathVariable(name = "removed_client_id") Long removed_client_id) throws BibliotecaException {
        Optional<Client> optionalClient = clientService.getClientById(removed_client_id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            if (client.getSetOfBookLent().size() < 1) {
                clientService.delete(removed_client_id);
                return "redirect:/client/list";
            } else {
                return "client-remove-info";
//                return "redirect:/client/list";
            }
//            if (client.getSetOfBookLent().size() > 0) {
//                throw new BibliotecaException("Can not remove the client - there are some borrowed books.");
//            } else {
//                clientService.delete(removed_client_id);
//                return "redirect:/client/list";
//            }
        } else {
            throw new EntityNotFoundException("Client not found");
        }
    }

    @GetMapping(path = "/edit/{client_id}")
    public String editClient(Model model,
                             @PathVariable( name = "client_id") Long client_id) {
        Optional<Client> optionalClient = clientService.getClientById(client_id);
        if (optionalClient.isPresent()) {
            model.addAttribute("client", optionalClient.get());
            return "client-add";
        }
        return "redirect:/client/list";
    }

    @GetMapping(path = "/books/{client_id}")
    public String getClientsBooks(Model model,
                                  HttpServletRequest request,
                                  @PathVariable(name = "client_id") Long client_id) {
        Optional<Client> optionalClient = clientService.getClientById(client_id);
        if (optionalClient.isPresent()) {
            Set<BookLent> bookLentsAll = optionalClient.get().getSetOfBookLent();
            Set<BookLent> bookLents = bookLentsAll.stream()
                    .filter(bookLent -> bookLent.getDateReturned() == null)
                    .collect(Collectors.toSet());
            model.addAttribute("booklents", bookLents);
//            model.addAttribute("booklents", optionalClient.get().getSetOfBookLent());
            model.addAttribute("client", optionalClient.get());
            model.addAttribute("referer", request.getHeader("referer"));
            return "client-books";
        }
        return "redirect:/client/list";
    }

    @GetMapping(path = "/details/{client_id}")
    public String clientDetails(Model model,
                                HttpServletRequest request,
                                @PathVariable(name = "client_id") Long client_id) {
        String referer = request.getHeader("referer");

        Optional<Client> optionalClient = clientService.getClientById(client_id);
        if (optionalClient.isPresent()) {
            model.addAttribute("referer", referer);
            model.addAttribute("client", optionalClient.get());
            model.addAttribute("booklents", optionalClient.get().getSetOfBookLent());
            return "client-details";
        }
        return "redirect:/client/list";
    }

    @GetMapping(path = "/addBook/{client_id}")
    public String addBookToClient(Model model,
                                  BookLent bookLent,
                                  @PathVariable(name = "client_id") Long client_id) {
        Optional<Client> optionalClient = clientService.getClientById(client_id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            bookLent.setClient(client);

            model.addAttribute("booklent", bookLent);
            model.addAttribute("books", bookService.getAll());
            return "client-addBook";
        }
        return "redirect:/client/list";
    }

    @PostMapping(path = "/addBook")
    public String addBookToClient(BookLent bookLent, Long bookId) {
        bookLentService.saveBookLentByBookId(bookLent, bookId);

        return "redirect:/client/list";
    }

    @GetMapping(name = "/removeInfo")
    public String clientRemoveInfo(Model model,
                                   HttpServletRequest request) {
        String referer = request.getHeader("referer");
        model.addAttribute("referer", referer);
        return "client-remove-info";
    }
}
