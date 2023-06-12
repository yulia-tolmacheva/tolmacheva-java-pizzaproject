package de.telran.pizzaproject.service;

import de.telran.pizzaproject.model.PizzaSize;
import de.telran.pizzaproject.model.entity.Ingredient;
import de.telran.pizzaproject.model.entity.Restaurant;

import java.util.List;
import java.util.Map;

public interface PizzaDataProviderService {
    List<Ingredient> getAllIngredients();
    List<Restaurant> getAllRestaurants();
    List<PizzaSize> getAllPizzaSizes();
    Map<Long, Boolean> getSpicyPizzas();
    Map<Long, Boolean> getVeggiePizzas();
    Map<Long, Boolean> getGlutenFreePizzas();
}
