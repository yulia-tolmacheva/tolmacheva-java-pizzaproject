package de.telran.pizzaproject.service;

import de.telran.pizzaproject.model.entity.Ingredient;
import de.telran.pizzaproject.model.entity.Pizza;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    List<Ingredient> getAllIngredients();
    Ingredient getIngredientById(Long ingredientId);

    Ingredient addOrUpdate(Ingredient ingredient);

    void deleteIngredient(Long ingredientId);

    Optional<Ingredient> getIngredientByName(String name);
}
