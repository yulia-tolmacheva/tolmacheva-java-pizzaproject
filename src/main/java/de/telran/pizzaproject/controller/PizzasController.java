package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.repository.IngredientRepository;
import de.telran.pizzaproject.repository.RestaurantRepository;
import de.telran.pizzaproject.service.PizzaService;
import de.telran.pizzaproject.validator.PizzaValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/pizzas")
public class PizzasController {

    private final PizzaService service;
    private final PizzaValidator pizzaValidator;
    private final IngredientRepository ingredientRepository;
    private final RestaurantRepository restaurantRepository;

    public PizzasController(PizzaService service, PizzaValidator pizzaValidator, IngredientRepository ingredientRepository, RestaurantRepository restaurantRepository) {
        this.service = service;
        this.pizzaValidator = pizzaValidator;
        this.ingredientRepository = ingredientRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("pizzas", service.getAllPizzas());
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
        pizzaValidator.validate(pizza, result);
        if (result.hasErrors()) {
            return "pizza/new";
        }

        Pizza pizzaAdded = service.add(pizza);
        attributes.addFlashAttribute("added", pizzaAdded.getId());
        return "redirect:/pizzas";
    }

    @DeleteMapping("/delete")
    public String deletePizza(@RequestParam Long id, RedirectAttributes attributes) {
        service.deletePizza(id);
        attributes.addFlashAttribute("deleted", id);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit")
    public String openEditPage(@RequestParam Long id,
                               Model model) {
        model.addAttribute("pizzaToUpdate", service.getPizzaById(id));
        return "pizza/edit";
    }

    @PatchMapping("/edit")
    public String editPizza(@ModelAttribute("pizzaToUpdate") @Valid Pizza pizzaToUpdate,
                            BindingResult result, RedirectAttributes attributes,
                            Model model) {
        pizzaValidator.validate(pizzaToUpdate, result);
        if (result.hasErrors()) {
            return "pizza/edit";
        }
        Pizza updated = service.update(pizzaToUpdate);
        if (updated == null) {
            model.addAttribute("notfound", pizzaToUpdate.getId());
            return "pizza/edit";
        }
        attributes.addFlashAttribute("updated", pizzaToUpdate.getId());
        return "redirect:/pizzas";
    }

    @GetMapping("/search")
    public String findAllPizzasByRestaurantOrBySizeOrIngredient(
            @RequestParam(name = "size", required = false) Integer size,
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
        model.addAttribute("allIngredients", ingredientRepository.findAll());
        model.addAttribute("allRestaurants", restaurantRepository.findAll());
        model.addAttribute("allPizzaSizes", List.of(12, 20, 28));

        model.addAttribute("spicyPizzas", service.getMapPizzaIdIsSpicy());
        model.addAttribute("veggiePizzas", service.getMapPizzaIdIsVegetarian());
        model.addAttribute("glutenFreePizzas", service.getMapPizzaIdIsGlutenFree());
    }
}

