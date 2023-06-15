package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/login")
    public String loginPage() {
        return "/auth/login";
    }

    @GetMapping("/signup")
    public String signupPage(@ModelAttribute("newUser") User user) {
        return "/auth/signup";
    }
}
