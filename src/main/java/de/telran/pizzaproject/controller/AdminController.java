package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.PizzaSize;
import de.telran.pizzaproject.model.RoleName;
import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.service.PizzaDataProviderService;
import de.telran.pizzaproject.service.PizzaService;
import de.telran.pizzaproject.service.UserService;
import de.telran.pizzaproject.validator.PasswordValidator;
import de.telran.pizzaproject.validator.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Log4j2
public class AdminController {

    private final UserService userService;
    private final PizzaService pizzaService;
    private final UserValidator validator;
    private final PizzaDataProviderService pizzaDataProviderService;
    private final PasswordValidator passwordValidator;

    @GetMapping()
    public String getAdmin() {
        return "admin/admin";
    }

    @GetMapping("/users")
    public String getAllUsers() {
        return "admin/users";
    }

    @PostMapping("/users/new")
    public String addUsersDetails(@ModelAttribute("userToAdd") @Valid User userToAdd,
                                  BindingResult result,
                                  RedirectAttributes attributes, Model model) {
        validator.validate(userToAdd, result);
        if (result.hasErrors()) {
            log.info("User wasn't created " + result.getAllErrors());
            model.addAttribute("userToAdd", userToAdd);
            return "admin/users";
        }
        User user = userService.addUserAsAdmin(userToAdd);
        attributes.addFlashAttribute("added", user.getUsername());
        return "redirect:/admin/users";
    }

    @GetMapping("/pizzas")
    public String getAllPizzas(Model model) {
        model.addAttribute("filteredPizzasList", pizzaService.getAllPizzas());
        return "admin/all-pizzas";
    }

    @DeleteMapping("/pizzas/delete")
    public String deletePizza(@RequestParam Long id, RedirectAttributes attributes) {
        pizzaService.deletePizza(id);
        attributes.addFlashAttribute("deleted", id);
        return "redirect:/admin/pizzas";
    }

    @GetMapping("/pizzas/edit")
    public String getPizzaEditPage(@RequestParam("id") Long id, Model model) {
        model.addAttribute("pizzaToUpdate", pizzaService.getPizzaById(id));
        return "pizza/edit";
    }

    @GetMapping("/pizzas/search")
    public String filterPizzasAdmin(
            @RequestParam(name = "size", required = false) PizzaSize size,
            @RequestParam(name = "ingredient", required = false) String ingredient,
            @RequestParam(name = "restaurantId", required = false) Long id,
            Model model) {
        List<Pizza> filteredPizzasList = pizzaService
                .applyFilters(id, size, ingredient);

        model.addAttribute("selectedSize", size);
        model.addAttribute("restaurantId", id);
        model.addAttribute("selectedIngredient", ingredient);
        model.addAttribute("filteredPizzasList", filteredPizzasList);
        model.addAttribute("pizzaDataProviderService", pizzaDataProviderService);
        return "admin/all-pizzas";
    }

    @GetMapping("/restaurants")
    public String getAllRestaurants() {
        return "admin/restaurants";
    }

    @GetMapping("/users/edit")
    public String showEditFields(@RequestParam("userToUpdateId") Long userToUpdateId,
                                 Model model) {
        model.addAttribute("userToUpdate", userService.getUserById(userToUpdateId));
        return "/admin/users";
    }

    @PatchMapping("/users/edit")
    public String editUser(@ModelAttribute("userToUpdate") @Valid User userToUpdate,
                           BindingResult result,
                           RedirectAttributes attributes,
                           Model model) {
        validator.validate(userToUpdate, result);
        if (result.hasErrors()) {
            model.addAttribute("userToUpdate", userToUpdate);
            log.info("User wasn't updated " + result.getAllErrors());
            return "/admin/users";
        }
        userService.updateUserAsAdmin(userToUpdate);
        attributes.addFlashAttribute("updated", userToUpdate.getUsername());
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/delete")
    public String deleteUser(@RequestParam("userId") Long userId,
                             RedirectAttributes attributes) {
        if (userService.getUserById(userId).getUsername().equals("admin")) {
            log.info("Attempt to delete admin user");
            attributes.addFlashAttribute("notauthorized", "not authorized");
            return "redirect:/admin/users";
        }
        userService.deleteUser(userId);
        attributes.addFlashAttribute("deleted", userId);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/search")
    public String searchUsers(Model model,
                              @RequestParam(required = false) String username) {
        List<User> filteredList;
        filteredList = userService.getAllUsersByUsername(username);
        model.addAttribute("filteredList", filteredList);
        model.addAttribute("username", username);
        return "admin/users";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("filteredList", userService.getAllUsers());
        model.addAttribute("pizzaDataProviderService", pizzaDataProviderService);
        model.addAttribute("allRoles", RoleName.values());
        model.addAttribute("userToAdd", new User());
    }
}
