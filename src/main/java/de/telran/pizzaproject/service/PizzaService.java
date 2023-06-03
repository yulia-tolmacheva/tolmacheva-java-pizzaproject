package de.telran.pizzaproject.service;


import de.telran.pizzaproject.model.entity.Ingredient;
import de.telran.pizzaproject.model.entity.Pizza;

import java.util.List;
import java.util.Map;

public interface PizzaService {

    List<Pizza> getAllPizzas();
    Pizza getPizzaById(Long pizzaId);

    Pizza addOrUpdate(Pizza pizza);

    void deletePizza(Long pizzaId);

    Map<Long, List<Ingredient>> getMapPizzaIdAndIngredients();

    Map<Long, Boolean> getMapPizzaIdIsSpicy(List<Pizza> pizzas);

    Map<Long, Boolean> getMapPizzaIdIsVegetarian(List<Pizza> pizzas);

    Map<Long, Boolean> getMapPizzaIdIsGlutenFree(List<Pizza> pizzas);

    List<Pizza> getAllPizzasBySize(Integer size);

    List<Pizza> getAllPizzasBySizeAndByIngredient(Integer size, String ingredient);

    List<Pizza> getAllPizzasByIngredient(String ingredient);

    List<Pizza> getAllPizzasByRestaurant(Long restaurantId);
}
