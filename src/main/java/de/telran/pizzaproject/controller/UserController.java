package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.entity.Ingredient;
import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.repository.RoleRepository;
import de.telran.pizzaproject.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UsersService service;

    private final RoleRepository roleRepository;

    public UserController(UsersService service, RoleRepository roleRepository) {
        this.service = service;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String getAll(Model model) {
        List<User> users = service.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("userToAdd", new User());
        return "user/users";
    }

    @PostMapping
    public String addUsersDetails(@ModelAttribute("userToAdd") @Valid User userToAdd,
                                  BindingResult result,
                                  RedirectAttributes attributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userToAdd", userToAdd);
            return "/user/users";
        }
        User user = service.addOrUpdate(userToAdd);
        attributes.addFlashAttribute("added", user.getId());
        return "redirect:/users";
    }

    @PostMapping("/update")
    public String showUpdateFields(@RequestParam("userToUpdateId") Long userToUpdateId,
                                   Model model) {
        model.addAttribute("userToUpdate", service.getUserById(userToUpdateId));
        model.addAttribute("userToUpdateId", userToUpdateId);
        return "/user/users";
    }

    @PatchMapping("/update/{id}")
    public String updateUserDetails(@ModelAttribute("userToUpdate") @Valid User userToUpdate,
                                           BindingResult result,
                                           Model model,
                                           @RequestParam("userToUpdateId") Long userToUpdateId,
                                           RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("userToUpdate", userToUpdate);
            model.addAttribute("userToUpdateId", userToUpdateId);
            return "/user/users";
        }
        User user = service.addOrUpdate(userToUpdate);
        attributes.addFlashAttribute("updated", user.getId());
        return "redirect:/users";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam("userId") Long userId,
                                   RedirectAttributes attributes) {
        service.deleteUser(userId);
        attributes.addFlashAttribute("deleted", userId);
        return "redirect:/users";
    }

    @ModelAttribute("users")
    public List<User> getListIngredients() {
        return service.getAllUsers();
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("allRoles", roleRepository.findAll());
    }
}
