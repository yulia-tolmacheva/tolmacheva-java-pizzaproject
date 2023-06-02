package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.entity.Ingredient;
import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.repository.IngredientRepository;
import de.telran.pizzaproject.repository.RestaurantRepository;
import de.telran.pizzaproject.service.PizzaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    private final PizzaService service;
    private final IngredientRepository ingredientRepository;
    private final RestaurantRepository restaurantRepository;

    public PizzaController(PizzaService service, IngredientRepository ingredientRepository, RestaurantRepository restaurantRepository) {
        this.service = service;
        this.ingredientRepository = ingredientRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping
    public String getAll(Model model) {
        List<Pizza> pizzas = service.getAllPizzas();
        model.addAttribute("pizzas", pizzas);

        Map<Long, Boolean> spicyPizzas = pizzas.stream().collect(Collectors.toMap(Pizza::getId,
                pizza -> pizza.getIngredients().stream().map(Ingredient::isSpicy)
                        .filter(aBoolean -> aBoolean).findAny().orElse(false)));

        Map<Long, Boolean> veggiePizzas = pizzas.stream().collect(Collectors.toMap(Pizza::getId,
                pizza -> pizza.getIngredients().stream().map(Ingredient::isVegetarian)
                        .filter(aBoolean -> !aBoolean).findAny().orElse(true)));

        Map<Long, Boolean> glutenFreePizzas = pizzas.stream().collect(Collectors.toMap(Pizza::getId,
                pizza -> pizza.getIngredients().stream().map(Ingredient::isGlutenfree)
                        .filter(aBoolean -> aBoolean).findAny().orElse(false)));

        model.addAttribute("spicyPizzas", spicyPizzas);
        model.addAttribute("veggiePizzas", veggiePizzas);
        model.addAttribute("glutenFreePizzas", glutenFreePizzas);

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

    @PatchMapping("/edit/{id}")
    public String editPizza(@ModelAttribute("pizzaToUpdate") @Valid Pizza pizzaToUpdate,
                            BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "pizza/edit";
        }
        service.addOrUpdate(pizzaToUpdate);
        attributes.addFlashAttribute("updated", pizzaToUpdate.getId());
        return "redirect:/pizzas";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("allIngredients", ingredientRepository.findAll());
        model.addAttribute("allRestaurants", restaurantRepository.findAll());
        model.addAttribute("allPizzaSizes", List.of(12, 20, 28));
    }
}

