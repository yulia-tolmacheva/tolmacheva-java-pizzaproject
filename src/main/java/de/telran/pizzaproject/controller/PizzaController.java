package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.model.entity.Restaurant;
import de.telran.pizzaproject.service.PizzaService;
import de.telran.pizzaproject.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    private final PizzaService service;

    public PizzaController(PizzaService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(Model model) {
        List<Pizza> pizzas = service.getAllPizzas();
        model.addAttribute("pizzas", pizzas);
        model.addAttribute("pizzaToAdd", new Pizza());
        return "pizza/pizzas";
    }
}
