package de.telran.pizzaproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
public class SearchController {
        @GetMapping()
    public String getRestaurantsByCity(@RequestParam String city, Model model) {
        model.addAttribute("city", city);
        return "redirect:/restaurants/" + city.toLowerCase();
    }
}
