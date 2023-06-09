package de.telran.pizzaproject.service;


import de.telran.pizzaproject.model.entity.Ingredient;
import de.telran.pizzaproject.model.entity.Pizza;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PizzaService {

    List<Pizza> getAllPizzas();
    Pizza getPizzaById(Long pizzaId);

    Pizza add(Pizza pizza);

    Pizza update(Pizza pizza);

    void deletePizza(Long pizzaId);

    Map<Long, List<Ingredient>> getMapPizzaIdAndIngredients();

    Map<Long, Boolean> getMapPizzaIdIsSpicy();

    Map<Long, Boolean> getMapPizzaIdIsVegetarian();

    Map<Long, Boolean> getMapPizzaIdIsGlutenFree();

    List<Pizza> applyRestaurantAndSizeAndIngredientFilters(Long restaurantId, Integer size, String ingredient);

    List<Pizza> applySizeOrIngredientFilters(Integer size, String ingredient);

    Optional<Pizza> getPizzaByNameAndBySizeAndRestaurant(String name, Integer size, Long id);
}
