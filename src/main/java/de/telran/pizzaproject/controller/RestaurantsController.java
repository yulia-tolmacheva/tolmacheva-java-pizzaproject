package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.PizzaSize;
import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.model.entity.Restaurant;
import de.telran.pizzaproject.service.PizzaDataProviderService;
import de.telran.pizzaproject.service.PizzaService;
import de.telran.pizzaproject.service.RestaurantService;
import de.telran.pizzaproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/restaurants")
@RequiredArgsConstructor
@Log4j2
public class RestaurantsController {

    private final RestaurantService service;
    private final PizzaService pizzaService;
    private final UserService userService;
    private final PizzaDataProviderService pizzaDataProviderService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("filteredList", service.getAllRestaurants());
        return "restaurant/restaurants";
    }

    @GetMapping("/search")
    public String getSearchByAddressAndCity(Model model,
                                            @RequestParam(required = false) String address,
                                            @RequestParam(required = false) String city) {
        List<Restaurant> filteredList;
        filteredList = service.getAllByAddressAndCity(address, city);
        model.addAttribute("filteredList", filteredList);
        model.addAttribute("address", address);
        model.addAttribute("city", city);
        return "restaurant/restaurants";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addRestaurantDetails(@ModelAttribute("restaurantToAdd") Restaurant restaurant,
                                       Model model) {
        model.addAttribute("ownersList", userService.getAllUsersWithOwnerRole());
        return "restaurant/new";
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addRestaurant(@ModelAttribute("restaurantToAdd") @Valid Restaurant restaurant,
                                BindingResult result,
                                RedirectAttributes attributes) {
        if (result.hasErrors()) {
            log.info("Restaurant wasn't created " + result.getAllErrors());
            return "restaurant/new";
        }

        Restaurant restaurantAdded = service.addOrUpdate(restaurant);
        attributes.addFlashAttribute("added", restaurantAdded.getName());
        return "redirect:/restaurants";
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteRestaurant(@RequestParam Long restaurantId, RedirectAttributes attributes) {
        service.deletePizza(restaurantId);
        log.info("Restaurant was deleted : " + restaurantId);
        attributes.addFlashAttribute("deleted", restaurantId);
        return "redirect:/restaurants";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String openEditPage(@RequestParam Long restaurantId,
                               Model model) {
        model.addAttribute("restaurantToUpdate", service.getRestaurantById(restaurantId));
        model.addAttribute("ownersList", userService.getAllUsersWithOwnerRole());
        return "restaurant/edit";
    }

    @PatchMapping("/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editRestaurant(@ModelAttribute("restaurantToUpdate") @Valid Restaurant restaurantToUpdate,
                                 BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            log.info("Restaurant wasn't updated " + result.getAllErrors());
            return "restaurant/edit";
        }
        service.addOrUpdate(restaurantToUpdate);
        attributes.addFlashAttribute("updated", restaurantToUpdate.getName());
        return "redirect:/restaurants";
    }

    @GetMapping("/{restaurantId}/view")
    public String getAll(@PathVariable("restaurantId") Long id,
                         Model model) {
        model.addAttribute("filteredPizzasList", service.getRestaurantById(id).getPizzas());
        model.addAttribute("restaurantToView", service.getRestaurantById(id));
        model.addAttribute("pizzaDataProviderService", pizzaDataProviderService);
        return "restaurant/view";
    }

    @GetMapping("/{restaurantId}/pizzas/search")
    public String filterPizzas(
            @RequestParam(name = "size", required = false) PizzaSize size,
            @RequestParam(name = "ingredient", required = false) String ingredient,
            @RequestParam(name = "vegetarian", required = false) boolean vegetarian,
            @RequestParam(name = "glutenfree", required = false) boolean glutenfree,
            @RequestParam(name = "spicy", required = false) boolean spicy,
            @PathVariable(name = "restaurantId") Long id,
            Model model) {
        List<Pizza> filteredPizzasList = pizzaService
                .applyFilters(id, size, ingredient, vegetarian, glutenfree, spicy);

        model.addAttribute("selectedSize", size);
        model.addAttribute("selectedIngredient", ingredient);
        model.addAttribute("selectedVegetarian", vegetarian);
        model.addAttribute("selectedGlutenfree", glutenfree);
        model.addAttribute("selectedSpicy", spicy);
        model.addAttribute("restaurantToView", service.getRestaurantById(id));
        model.addAttribute("filteredPizzasList", filteredPizzasList);
        model.addAttribute("pizzaDataProviderService", pizzaDataProviderService);
        return "restaurant/view";
    }
}

//    public boolean canEditRestaurant(Long restaurantId, Principal principal) {
//        Authentication authentication = (Authentication) principal;
//        boolean isAdmin = authentication.getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
//        if (isAdmin) {
//            return true;
//        } else {
//            User owner = userRepository.findByRestaurant_Id(restaurantId);
//            String loggedInUsername = principal.getName();
//            return owner.getUsername().equals(loggedInUsername);
//        }
//    }
