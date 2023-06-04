package de.telran.pizzaproject.validator;

import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (userService.getUserByUsername(user.getUsername()).isPresent()){
            errors.rejectValue("username", "", "This username already exists");
        }
    }
}
