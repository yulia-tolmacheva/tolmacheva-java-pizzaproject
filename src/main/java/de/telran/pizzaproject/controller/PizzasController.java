package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.PizzaSize;
import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.service.PizzaDataProviderService;
import de.telran.pizzaproject.service.PizzaService;
import de.telran.pizzaproject.service.RestaurantService;
import de.telran.pizzaproject.validator.PizzaValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@Log4j2
@RequestMapping("/restaurants/{restaurantId}/pizzas")
@RequiredArgsConstructor
public class PizzasController {

    private final PizzaService service;
    private final RestaurantService restaurantService;
    private final PizzaValidator pizzaValidator;
    private final PizzaDataProviderService pizzaDataProviderService;

    //    @GetMapping
//    public String getAll(Model model) {
//        model.addAttribute("pizzas", service.getAllPizzas());
//        return "pizza/pizzas";
//    }
    @GetMapping("/search")
    public String filterPizzas(
            @RequestParam(name = "size", required = false) PizzaSize size,
            @RequestParam(name = "ingredient", required = false) String ingredient,
            @RequestParam(name = "vegetarian", required = false) boolean vegetarian,
            @RequestParam(name = "glutenfree", required = false) boolean glutenfree,
            @RequestParam(name = "spicy", required = false) boolean spicy,
            @PathVariable(name = "restaurantId") Long id,
            Model model) {
        List<Pizza> filteredPizzasList = service
                .applyFilters(id, size, ingredient, vegetarian, glutenfree, spicy);

        model.addAttribute("selectedSize", size);
        model.addAttribute("selectedIngredient", ingredient);
        model.addAttribute("selectedVegetarian", vegetarian);
        model.addAttribute("selectedGlutenfree", glutenfree);
        model.addAttribute("selectedSpicy", spicy);
        model.addAttribute("restaurantToView", restaurantService.getRestaurantById(id));
        model.addAttribute("filteredPizzasList", filteredPizzasList);
        model.addAttribute("pizzaDataProviderService", pizzaDataProviderService);
        return "restaurant/view";
    }

    @GetMapping("/new")
    @PreAuthorize("hasPermission(#restaurantId, 'edit')")
    public String addPizzaDetails(@PathVariable("restaurantId") Long restaurantId,
                                  @ModelAttribute("pizzaToAdd") Pizza pizza) {
        return "pizza/new";
    }

    @PostMapping("/new")
    @PreAuthorize("hasPermission(#restaurantId, 'edit')")
    public String addPizza(@PathVariable("restaurantId") Long restaurantId,
                           @ModelAttribute("pizzaToAdd") @Valid Pizza pizza,
                           BindingResult result,
                           RedirectAttributes attributes) {
        pizzaValidator.validate(pizza, result);
        if (result.hasErrors()) {
            log.info("Pizza wasn't created " + result.getAllErrors());
            return "pizza/new";
        }
        Pizza pizzaAdded = service.add(pizza);
        attributes.addFlashAttribute("added", pizzaAdded.getName());
        return "redirect:/restaurants/" + restaurantId;
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasPermission(#restaurantId, 'edit')")
    public String deletePizza(@RequestParam("pizzaId") Long id,
                              @PathVariable("restaurantId") Long restaurantId,
                              RedirectAttributes attributes) {
        service.deletePizza(id);
        attributes.addFlashAttribute("deleted", id);
        return "redirect:/restaurants/" + restaurantId;
    }

    @GetMapping("/edit")
    @PreAuthorize("hasPermission(#restaurantId, 'edit')")
    public String openEditPage(@RequestParam("pizzaId") Long id,
                               @PathVariable("restaurantId") Long restaurantId,
                               Model model) {
        System.out.println("restaurantId = " + restaurantId);
        System.out.println("id = " + id);
        model.addAttribute("pizzaToUpdate", service.getPizzaById(id));
        return "pizza/edit";
    }

    @PatchMapping("/edit")
    @PreAuthorize("hasPermission(#restaurantId, 'edit')")
    public String editPizza(@ModelAttribute("pizzaToUpdate") @Valid Pizza pizzaToUpdate,
                            @PathVariable("restaurantId") Long restaurantId,
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
        return "redirect:/restaurants/" + restaurantId;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("pizzaDataProviderService", pizzaDataProviderService);
    }
}

