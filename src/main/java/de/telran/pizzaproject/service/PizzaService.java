package de.telran.pizzaproject.service;


import de.telran.pizzaproject.model.entity.Pizza;

import java.util.List;

public interface PizzaService {

    List<Pizza> getAllPizzas();
    Pizza getPizzaById(Long pizzaId);

    Pizza addOrUpdate(Pizza pizza);

    void deletePizza(Long pizzaId);

}
