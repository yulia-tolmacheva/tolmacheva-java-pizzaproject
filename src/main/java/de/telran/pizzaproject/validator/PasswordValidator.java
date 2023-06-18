package de.telran.pizzaproject.validator;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

    public String validate(Object target) {
        String password = (String) target;

        if (password.isEmpty()) {
            return "Can't be empty";
        }
        if (password.length() < 3 || password.length() > 100) {
            return "Password length is between 3 and 100 characters";
        }
        else return "";
    }
}
