package com.javagda25.biblioteca.controller;

import com.javagda25.biblioteca.model.Book;
import com.javagda25.biblioteca.model.PublishingHouse;
import com.javagda25.biblioteca.service.PublishingHouseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/ph/")
@AllArgsConstructor
public class PublishingHouseController {
    private final PublishingHouseService publishingHouseService;

    @GetMapping("/list")
    public String list(Model model) {
        List<PublishingHouse> publishingHouses = publishingHouseService.getAll();
        model.addAttribute("phouses", publishingHouses);

        return "ph-list";
    }

    @GetMapping("/add")
    public String add(Model model, PublishingHouse publishingHouse) {
        model.addAttribute("publishingHouse", publishingHouse);
        return "ph-add";
    }

    @GetMapping("/remove/{id}")
    public String add(@PathVariable(name = "id") Long id) {
        publishingHouseService.remove(id);
        return "redirect:/ph/list";
    }

    @GetMapping("/edit/{id}")
    public String add(Model model,
                      @PathVariable(name = "id") Long id) {
        Optional<PublishingHouse> publishingHouseOptional = publishingHouseService.getById(id);
        if (publishingHouseOptional.isPresent()) {
            model.addAttribute("publishingHouse", publishingHouseOptional.get());

            return "ph-add";
        }
        return "redirect:/ph/list";
    }

    @PostMapping("/add")
    public String add(PublishingHouse publishingHouse) {
        publishingHouseService.save(publishingHouse);

        return "redirect:/ph/list";
    }
}
