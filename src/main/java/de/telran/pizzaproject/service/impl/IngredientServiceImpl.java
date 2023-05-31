package de.telran.pizzaproject.service.impl;

import de.telran.pizzaproject.model.entity.Ingredient;
import de.telran.pizzaproject.repository.IngredientRepository;
import de.telran.pizzaproject.repository.PizzaRepository;
import de.telran.pizzaproject.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository repository;

    public IngredientServiceImpl(IngredientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return repository.findAll();
    }

    @Override
    public Ingredient getIngredientById(Long ingredientId) {
        return repository.getReferenceById(ingredientId);
    }

    @Override
    public Ingredient addOrUpdate(Ingredient ingredient) {
        return repository.save(ingredient);
    }

    @Override
    public void deleteIngredient(Long ingredientId) {
        repository.deleteById(ingredientId);
    }
}
