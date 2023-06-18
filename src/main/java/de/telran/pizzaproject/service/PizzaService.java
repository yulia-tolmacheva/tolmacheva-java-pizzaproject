package de.telran.pizzaproject.service;


import de.telran.pizzaproject.model.entity.Ingredient;
import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.model.PizzaSize;

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

    List<Pizza> applyRestaurantAndSizeAndIngredientFilters(Long restaurantId, PizzaSize size, String ingredient);

    List<Pizza> applySizeOrIngredientFilters(PizzaSize size, String ingredient);

    Optional<Pizza> getPizzaByNameAndBySizeAndRestaurant(String name, PizzaSize size, Long id);

    List<Pizza> applyFilters(Long id, PizzaSize size, String ingredient, boolean vegetarian, boolean glutenfree, boolean spicy);
    List<Pizza> applyFilters(Long id, PizzaSize size, String ingredient);
}
