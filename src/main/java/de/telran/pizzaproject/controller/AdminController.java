package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.RoleName;
import de.telran.pizzaproject.model.entity.User;
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

    private final UserService service;
    private final UserValidator validator;
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
        User user = service.addUserAsAdmin(userToAdd);
        attributes.addFlashAttribute("added", user.getUsername());
        return "redirect:/admin/users";
    }

    @GetMapping("/pizzas")
    public String getAllPizzas() {
        return "admin/pizzas";
    }

    @GetMapping("/restaurants")
    public String getAllRestaurants() {
        return "admin/restaurants";
    }

    @GetMapping("/ingredients")
    public String getAllIngredients() {
        return "redirect:/ingredients";
    }

    @GetMapping("/users/edit")
    public String showEditFields(@RequestParam("userToUpdateId") Long userToUpdateId,
                                 Model model) {
        model.addAttribute("userToUpdate", service.getUserById(userToUpdateId));
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
        service.updateUserAsAdmin(userToUpdate);
        attributes.addFlashAttribute("updated", userToUpdate.getUsername());
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/delete")
    public String deleteUser(@RequestParam("userId") Long userId,
                             RedirectAttributes attributes) {
        if (service.getUserById(userId).getUsername().equals("admin")) {
            log.info("Attempt to delete admin user");
            attributes.addFlashAttribute("notauthorized", "not authorized");
            return "redirect:/admin/users";
        }
        service.deleteUser(userId);
        attributes.addFlashAttribute("deleted", userId);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/search")
    public String searchUsers(Model model,
                              @RequestParam(required = false) String username) {
        List<User> filteredList;
        filteredList = service.getAllUsersByUsername(username);
        model.addAttribute("filteredList", filteredList);
        model.addAttribute("username", username);
        return "admin/users";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("filteredList", service.getAllUsers());
        model.addAttribute("allRoles", RoleName.values());
        model.addAttribute("userToAdd", new User());
    }


}
