package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.repository.RoleRepository;
import de.telran.pizzaproject.security.CustomUserDetails;
import de.telran.pizzaproject.service.UserService;
import de.telran.pizzaproject.validator.UserValidator;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService service;
    private final UserValidator validator;
    private final RoleRepository roleRepository;

    public UsersController(UserService service, UserValidator validator, RoleRepository roleRepository) {
        this.service = service;
        this.validator = validator;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String getUser(Authentication authentication, Model model) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        model.addAttribute("userToShow",
                service.getUserById(customUserDetails.getUser().getId()));
        return "user/user";
    }

    @PostMapping("/edit")
    public String showEditFields(@RequestParam("userToUpdateId") Long userToUpdateId,
                                 Model model) {
        model.addAttribute("userToUpdate", service.getUserById(userToUpdateId));
        model.addAttribute("userToShow", service.getUserById(userToUpdateId));
        return "/user/user";
    }

    @PatchMapping("/edit")
    public String editNamesOrPasswordUser(@ModelAttribute("userToUpdate") @Valid User userToUpdate,
                                          BindingResult result,
                                          RedirectAttributes attributes,
                                          Model model) {
        validator.validate(userToUpdate, result);
        System.out.println(userToUpdate);
        if (result.hasErrors()) {
            model.addAttribute("userToUpdate", userToUpdate);
            model.addAttribute("userToUpdateId", userToUpdate.getId());
            return "/user/user";
        }
        User user = service.updateNameOrPassword(userToUpdate, userToUpdate.getId());
        attributes.addFlashAttribute("updated", userToUpdate.getId());
        return "redirect:/users";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAll(Model model) {
        List<User> users = service.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("userToAdd", new User());
        return "user/users";
    }

    @PostMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addUsersDetails(@ModelAttribute("userToAdd") @Valid User userToAdd,
                                  BindingResult result,
                                  RedirectAttributes attributes, Model model) {
        validator.validate(userToAdd, result);
        if (result.hasErrors()) {
            model.addAttribute("userToAdd", userToAdd);
            return "/user/users";
        }
        User user = service.addOrUpdate(userToAdd);
        attributes.addFlashAttribute("added", user.getId());
        return "redirect:/users";
    }

    @PostMapping("/admin/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showUpdateFields(@RequestParam("userToUpdateId") Long userToUpdateId,
                                   Model model) {
        model.addAttribute("userToUpdate", service.getUserById(userToUpdateId));
        model.addAttribute("userToUpdateId", userToUpdateId);
        return "/user/users";
    }

    @PatchMapping("/admin/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateUserDetails(@ModelAttribute("userToUpdate") @Valid User userToUpdate,
                                    BindingResult result,
                                    Model model,
                                    @RequestParam("userToUpdateId") Long userToUpdateId,
                                    RedirectAttributes attributes) {
        validator.validate(userToUpdate, result);
        if (result.hasErrors()) {
            model.addAttribute("userToUpdate", userToUpdate);
            model.addAttribute("userToUpdateId", userToUpdateId);
            return "/user/users";
        }
        User user = service.addOrUpdate(userToUpdate);
        attributes.addFlashAttribute("updated", user.getId());
        return "redirect:/users/admin";
    }

    @DeleteMapping("/admin/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@RequestParam("userId") Long userId,
                             RedirectAttributes attributes) {
        if (service.getUserById(userId).getUsername().equals("admin")) {
            attributes.addFlashAttribute("notauthorized", "not authorized");
            return "redirect:/users/admin";
        }
        service.deleteUser(userId);
        attributes.addFlashAttribute("deleted", userId);
        return "redirect:/users/admin";
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
