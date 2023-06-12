package de.telran.pizzaproject.validator;

import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

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

        Optional<User> foundUser = userService.getUserByUsername(user.getUsername());

        if (foundUser.isPresent()) {
            if (!Objects.equals(foundUser.get().getId(), user.getId())) {
                errors.rejectValue("username", "", "This username already exists");
            }
        }
    }
}
