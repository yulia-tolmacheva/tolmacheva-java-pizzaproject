package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.entity.Restaurant;
import de.telran.pizzaproject.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mapping.Parameter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class CitySearchController {

    private final RestaurantService restaurantService;

    @GetMapping()
    public String getRestaurantsByCity(@RequestParam String city, Model model) {
//        List<Restaurant> filteredList;
//        if (city != null) {
//            filteredList = restaurantService.getAllByCity(city);
//            model.addAttribute("filteredList", filteredList);
//            model.addAttribute("city", city);
//        }
        return "redirect:/restaurants/" + city.toLowerCase();
    }
}
