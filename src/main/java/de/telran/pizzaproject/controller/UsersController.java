package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.service.RoleService;
import de.telran.pizzaproject.service.UserService;
import de.telran.pizzaproject.validator.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class UsersController {

    private final UserService service;
    private final RoleService roleService;
    private final UserValidator validator;

    @GetMapping
    public String getUser() {
        return "users/user";
    }

    @PostMapping("/edit")
    public String showEditFields(@RequestParam("userToUpdateId") Long userToUpdateId,
                                 Model model) {
        model.addAttribute("userToUpdate", service.getUserById(userToUpdateId));
        return "/users/user";
    }

    @PostMapping("/edit/p")
    public String showEditPassword(@RequestParam("userToUpdateId") Long userToUpdateId,
                                 Model model) {
        model.addAttribute("userToUpdatePas", service.getUserById(userToUpdateId));
        return "/users/user";
    }

    @PatchMapping("/edit")
    public String editNameOrUsername(@ModelAttribute("userToUpdate") @Valid User userToUpdate,
                                          BindingResult result,
                                          RedirectAttributes attributes,
                                          Model model) {
        validator.validate(userToUpdate, result);
        if (result.hasErrors()) {
            model.addAttribute("userToUpdate", userToUpdate);
            log.info("User wasn't updated " + result.getAllErrors());
            return "/users/user";
        }
        service.updateUsernameOrName(userToUpdate);
        attributes.addFlashAttribute("updated", userToUpdate.getUsername());
        return "redirect:/users";
    }

    @PatchMapping("/edit/p")
    public String editPassword(@ModelAttribute("userToUpdatePas") @Valid User userToUpdate,
                            BindingResult result,
                            RedirectAttributes attributes,
                            Model model) {
        validator.validate(userToUpdate, result);
        if (result.hasErrors()) {
            model.addAttribute("userToUpdatePas", userToUpdate);
            log.info("User wasn't updated " + result.getAllErrors());
            return "/users/user";
        }
        service.updatePassword(userToUpdate);
        attributes.addFlashAttribute("updated", userToUpdate.getUsername());
        return "redirect:/users";
    }

    @GetMapping("/admin")
    public String getAll(Model model) {
        model.addAttribute("userToAdd", new User());
        return "users/admin";
    }

    @PostMapping("/admin/new")
    public String addUsersDetails(@ModelAttribute("userToAdd") @Valid User userToAdd,
                                  BindingResult result,
                                  RedirectAttributes attributes, Model model) {
        validator.validate(userToAdd, result);
        if (result.hasErrors()) {
            log.info("User wasn't created " + result.getAllErrors());
            model.addAttribute("userToAdd", userToAdd);
            return "users/admin";
        }
        User user = service.addUser(userToAdd);
        attributes.addFlashAttribute("added", user.getUsername());
        return "redirect:/users/admin";
    }

    @PostMapping("/admin/edit")
    public String showUpdateFields(@RequestParam("userToUpdateId") Long userToUpdateId,
                                   Model model) {
        model.addAttribute("userToUpdate", service.getUserById(userToUpdateId));
        return "/users/admin";
    }

    @PostMapping("/admin/edit/p")
    public String showUpdatePassword(@RequestParam("userToUpdateId") Long userToUpdateId,
                                   Model model) {
        model.addAttribute("userToUpdatePas", service.getUserById(userToUpdateId));
        return "/users/admin";
    }

    @PatchMapping("/admin/edit")
    public String updateUserDetails(@ModelAttribute("userToUpdate") @Valid User userToUpdate,
                                    BindingResult result,
                                    Model model,
                                    RedirectAttributes attributes) {
        validator.validate(userToUpdate, result);
        if (result.hasErrors()) {
            log.info("User wasn't updated " + result.getAllErrors());
            model.addAttribute("userToUpdate", userToUpdate);
            return "/users/admin";
        }
        User user = service.updateUser(userToUpdate);
        attributes.addFlashAttribute("updated", user.getUsername());
        return "redirect:/users/admin";
    }

    @PatchMapping("/admin/edit/p")
    public String updateUserPassword(@ModelAttribute("userToUpdatePas") @Valid User userToUpdate,
                                    BindingResult result,
                                    Model model,
                                    RedirectAttributes attributes) {
        validator.validate(userToUpdate, result);
        if (result.hasErrors()) {
            log.info("User password wasn't updated " + result.getAllErrors());
            model.addAttribute("userToUpdatePas", userToUpdate);
            return "/users/admin";
        }
        User user = service.updatePassword(userToUpdate);
        attributes.addFlashAttribute("updated", user.getUsername());
        return "redirect:/users/admin";
    }

    @DeleteMapping("/admin/delete")
    public String deleteUser(@RequestParam("userId") Long userId,
                             RedirectAttributes attributes) {
        if (service.getUserById(userId).getUsername().equals("admin")) {
            log.info("Attempt to delete admin user");
            attributes.addFlashAttribute("notauthorized", "not authorized");
            return "redirect:/users/admin";
        }
        service.deleteUser(userId);
        attributes.addFlashAttribute("deleted", userId);
        return "redirect:/users/admin";
    }

    @ModelAttribute
    public void addAttributes(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userToShow", service.getUserById(user.getId()));
        model.addAttribute("users", service.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
    }
}
