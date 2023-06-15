package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.model.PizzaSize;
import de.telran.pizzaproject.service.PizzaDataProviderService;
import de.telran.pizzaproject.service.PizzaService;
import de.telran.pizzaproject.validator.PizzaValidator;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/pizzas")
@Log4j2
public class PizzasController {

    private final PizzaService service;
    private final PizzaValidator pizzaValidator;
    private final PizzaDataProviderService pizzaDataProviderService;

    public PizzasController(PizzaService service,
                            PizzaValidator pizzaValidator,
                            PizzaDataProviderService pizzaDataProviderService) {
        this.service = service;
        this.pizzaValidator = pizzaValidator;
        this.pizzaDataProviderService = pizzaDataProviderService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("pizzas", service.getAllPizzas());
        return "pizza/pizzas";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CREATOR')")
    public String addPizzaDetails(@ModelAttribute("pizzaToAdd") Pizza pizza) {
        return "pizza/new";
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CREATOR')")
    public String addPizza(@ModelAttribute("pizzaToAdd") @Valid Pizza pizza,
                           BindingResult result,
                           RedirectAttributes attributes) {
        pizzaValidator.validate(pizza, result);
        if (result.hasErrors()) {
            log.info("Pizza wasn't created " + result.getAllErrors());
            return "pizza/new";
        }

        Pizza pizzaAdded = service.add(pizza);
        attributes.addFlashAttribute("added", pizzaAdded.getId());
        return "redirect:/pizzas";
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CREATOR')")
    public String deletePizza(@RequestParam Long id, RedirectAttributes attributes) {
        service.deletePizza(id);
        attributes.addFlashAttribute("deleted", id);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CREATOR')")
    public String openEditPage(@RequestParam Long id,
                               Model model) {
        model.addAttribute("pizzaToUpdate", service.getPizzaById(id));
        return "pizza/edit";
    }

    @PatchMapping("/edit")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CREATOR')")
    public String editPizza(@ModelAttribute("pizzaToUpdate") @Valid Pizza pizzaToUpdate,
                            BindingResult result, RedirectAttributes attributes,
                            Model model) {
        pizzaValidator.validate(pizzaToUpdate, result);
        if (result.hasErrors()) {
            log.info("Restaurant wasn't updated " + result.getAllErrors());
            return "pizza/edit";
        }
        try {
            service.update(pizzaToUpdate);
        } catch (ObjectNotFoundException e) {
            log.info("Attempt to update pizza that doesn't exist(deleted)" + result.getAllErrors());
            model.addAttribute("notfound", pizzaToUpdate.getId());
            return "pizza/edit";
        }
        attributes.addFlashAttribute("updated", pizzaToUpdate.getId());
        return "redirect:/pizzas";
    }

    @GetMapping("/search")
    public String findAllPizzasByRestaurantOrBySizeOrIngredient(
            @RequestParam(name = "size", required = false) PizzaSize size,
            @RequestParam(name = "ingredient", required = false) String ingredient,
            @RequestParam(name = "restaurantId", required = false) Long restaurantId,
            Model model) {
        List<Pizza> pizzas;

        if (restaurantId != null) {
            pizzas = service.applyRestaurantAndSizeAndIngredientFilters(restaurantId, size, ingredient);
            model.addAttribute("restaurantId", restaurantId);
        } else {
            pizzas = service.applySizeOrIngredientFilters(size, ingredient);
        }

        model.addAttribute("selectedSize", size);
        model.addAttribute("ingredient", ingredient);
        model.addAttribute("pizzas", pizzas);
        return "pizza/pizzas";
    }


    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("pizzaDataProviderService", pizzaDataProviderService);
    }
}

