package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.entity.Restaurant;
import de.telran.pizzaproject.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService service;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping
    public String getAllCafes(Model model) {
        List<Restaurant> restaurants = service.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("restaurantToAdd", new Restaurant());
        return "restaurants/restaurants";
    }
}
