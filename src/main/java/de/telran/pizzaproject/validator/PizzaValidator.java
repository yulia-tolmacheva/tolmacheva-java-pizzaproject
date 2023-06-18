package de.telran.pizzaproject.validator;

import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.service.PizzaService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

@Component
public class PizzaValidator implements Validator {

    private final PizzaService pizzaService;

    public PizzaValidator(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Pizza.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pizza pizza = (Pizza) target;
        Optional<Pizza> foundPizza = pizzaService.getPizzaByNameAndBySizeAndRestaurant(
                pizza.getName(),
                pizza.getSize(),
                pizza.getRestaurant().getId());

        if (foundPizza.isPresent()) {
            if (!Objects.equals(foundPizza.get().getId(), pizza.getId())) {
                errors.rejectValue("name", "", "This pizza already exists");
            }
        }
    }
}
