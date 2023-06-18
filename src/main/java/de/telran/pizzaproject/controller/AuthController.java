package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.service.UserService;
import de.telran.pizzaproject.validator.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserValidator validator;
    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "/auth/login";
    }

    @GetMapping("/signup")
    public String signupPage(@ModelAttribute("newUser") User user) {
        return "/auth/signup";
    }

    @PostMapping("/signup")
    public String saveNewUser(@ModelAttribute("newUser") @Valid User user,
                              BindingResult result,
                              RedirectAttributes attributes,
                              Model model) {
        validator.validate(user, result);
        if (result.hasErrors()) {
            model.addAttribute("newUser", user);
            return "auth/signup";
        }
        User user1 = userService.addUser(user);
        attributes.addFlashAttribute("registered", user1.getUsername());
        return "redirect:/";
    }
}
