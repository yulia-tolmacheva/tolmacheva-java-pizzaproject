package de.telran.pizzaproject.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.pizzaproject.model.entity.Ingredient;
import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.service.IngredientService;
import de.telran.pizzaproject.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

@Component
public class IngredientValidator implements Validator {

    private final IngredientService ingredientService;

    public IngredientValidator(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Ingredient.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Ingredient ingredient = (Ingredient) target;
        Optional<Ingredient> foundIngredient = ingredientService.getIngredientByName(ingredient.getName());

        if (foundIngredient.isPresent()) {
            if (!Objects.equals(foundIngredient.get().getId(), ingredient.getId())) {
                errors.rejectValue("name", "", "This ingredient already exists");
            }
        }
    }
}
