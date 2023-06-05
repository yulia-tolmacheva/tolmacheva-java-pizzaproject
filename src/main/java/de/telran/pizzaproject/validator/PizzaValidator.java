package de.telran.pizzaproject.validator;

import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.service.PizzaService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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

        if (pizzaService.getPizzaByNameAndBySizeAndRestaurant(pizza.getName(), pizza.getSize(), pizza.getRestaurant().getId()).isPresent()){
            errors.rejectValue("name", "", "This pizza already exists");
        }
        if (!(pizza.getSize().equals(12) || pizza.getSize().equals(20) || pizza.getSize().equals(28))) {
            errors.rejectValue("size", "", "Available pizza sizes: 12, 20, 28");
        }
    }
}
