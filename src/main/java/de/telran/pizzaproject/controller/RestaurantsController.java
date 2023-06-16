package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.entity.Restaurant;
import de.telran.pizzaproject.service.RestaurantService;
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

    @GetMapping
    public String getAll() {
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

//    @GetMapping("/{id}")
//    public String addRestaurantDetails(@PathVariable Long id, Model model) {
//        model.addAttribute("restaurantToView", service.getRestaurantById(id));
//        return "restaurant/view";
//    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addRestaurantDetails(@ModelAttribute("restaurantToAdd") Restaurant restaurant) {
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
        attributes.addFlashAttribute("added", restaurantAdded.getId());
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
        return "restaurant/edit";
    }

    @PatchMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editRestaurant(@ModelAttribute("restaurantToUpdate") @Valid Restaurant restaurantToUpdate,
                                 BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            log.info("Restaurant wasn't updated " + result.getAllErrors());
            return "restaurant/edit";
        }
        service.addOrUpdate(restaurantToUpdate);
        attributes.addFlashAttribute("updated", restaurantToUpdate.getId());
        return "redirect:/restaurants";
    }

    @ModelAttribute
    public void getList(Model model) {
        model.addAttribute("filteredList", service.getAllRestaurants());
    }
}
