package com.javagda25.biblioteca.controller;

import com.javagda25.biblioteca.model.Book;
import com.javagda25.biblioteca.model.PublishingHouse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path = "/ph/")
public class PublishingHouseController {

    @GetMapping("/list")
    public String list(Model model) {
        List<PublishingHouse> publishingHouses = new ArrayList<>(Arrays.asList(
                new PublishingHouse("a"),
                new PublishingHouse("b"),
                new PublishingHouse("c"),
                new PublishingHouse("d"),
                new PublishingHouse("e"),
                new PublishingHouse("f")
        ));
        model.addAttribute("phouses", publishingHouses);

        return "ph-list";
    }
}
