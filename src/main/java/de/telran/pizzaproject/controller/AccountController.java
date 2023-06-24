package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.service.UserService;
import de.telran.pizzaproject.validator.PasswordValidator;
import de.telran.pizzaproject.validator.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
@Log4j2
public class AccountController {

    private final UserService service;
    private final UserValidator validator;
    private final PasswordValidator passwordValidator;

    @GetMapping
    public String getUser() {
        return "account/user";
    }

    @GetMapping("/restaurant")
    public String showEditFields(@RequestParam("username") String username) {
        Optional<User> userByUsername = service.findUserByUsername(username);
        if (userByUsername.isPresent()) {
            Long id = userByUsername.get().getRestaurant().getId();
            return  "redirect:/restaurants/" + id;
        }
        return "index";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasPermission(#userToUpdateId, 'editAccount')")
    public String showEditFields(@RequestParam("userToUpdateId") Long userToUpdateId,
                                 Model model) {
        model.addAttribute("userToUpdate", service.getUserById(userToUpdateId));
        return "/account/user";
    }

    @GetMapping("/edit/p")
    @PreAuthorize("hasPermission(#userToUpdateId, 'editAccount')")
    public String showEditPasswordField(@RequestParam("userToUpdateId") Long userToUpdateId,
                                        Model model) {
        model.addAttribute("passwordToUpdate", "");
        return "/account/user";
    }

    @PatchMapping("/edit")
    @PreAuthorize("hasPermission(#userToUpdate.id, 'editAccount')")
    public String editNamesOrUsername(@ModelAttribute("userToUpdate") @Valid User userToUpdate,
                                      BindingResult result,
                                      RedirectAttributes attributes,
                                      Model model) {
        validator.validate(userToUpdate, result);
        if (result.hasErrors()) {
            model.addAttribute("userToUpdate", userToUpdate);
            log.info("User wasn't updated " + result.getAllErrors());
            return "/account/user";
        }
        service.updateUsernameOrName(userToUpdate);
        attributes.addFlashAttribute("updated", userToUpdate.getUsername());
        return "redirect:/account";
    }

    @PatchMapping("/edit/p")
    @PreAuthorize("hasPermission(#userToUpdateId, 'editAccount')")
    public String updateUserPassword(@ModelAttribute("userToUpdateId") Long userToUpdateId,
                                     @ModelAttribute("password") @Valid String password,
                                     Model model,
                                     RedirectAttributes attributes) {
        String validate = passwordValidator.validate(password);
        if (!validate.isEmpty()) {
            log.info("User password wasn't updated :" + validate);
            model.addAttribute("error", validate);
            model.addAttribute("passwordToUpdate", "");
            return "/account/user";
        }
        service.updatePassword(userToUpdateId, password);
        attributes.addFlashAttribute("updated", userToUpdateId);
        return "redirect:/account";
    }

    @ModelAttribute
    public void addAttributes(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userToShow", service.getUserById(user.getId()));
    }
}
