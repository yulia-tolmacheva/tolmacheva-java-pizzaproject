package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.service.PizzaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        return "pizza/pizzas";
    }

    @GetMapping("/new")
    public String addPizzaDetails(@ModelAttribute("pizzaToAdd") Pizza pizza) {
        return "pizza/new";
    }

    @PostMapping("/new")
    public String addPizza(@ModelAttribute("pizzaToAdd") @Valid Pizza pizza,
                           BindingResult result,
                           RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "pizza/new";
        }

        Pizza pizzaAdded = service.addOrUpdate(pizza);
        attributes.addFlashAttribute("added", pizzaAdded.getId());
        return "redirect:/pizzas";
    }

    @DeleteMapping("/delete")
    public String deletePizza(@RequestParam Long pizzaId, RedirectAttributes attributes) {
        service.deletePizza(pizzaId);
        attributes.addFlashAttribute("deleted", pizzaId);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit")
    public String openEditPage(@RequestParam Long pizzaToUpdateId,
                               Model model) {
        model.addAttribute("pizzaToUpdate", service.getPizzaById(pizzaToUpdateId));
        return "pizza/edit";
    }

    @PostMapping("/edit/{id}")
    public String editPizza(@PathVariable("id") Long id,
                            @ModelAttribute("pizzaToUpdate") @Valid Pizza pizzaToUpdate,
                            BindingResult result, RedirectAttributes attributes,
                            Model model) {
        if (result.hasErrors()) {
            return "pizza/edit";
        }
        service.addOrUpdate(pizzaToUpdate);
        model.addAttribute("pizzaToUpdate", pizzaToUpdate);
        attributes.addFlashAttribute("updated", pizzaToUpdate.getId());
        return "redirect:/pizzas";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("pizzaToUpdate", new Pizza());
    }
}

