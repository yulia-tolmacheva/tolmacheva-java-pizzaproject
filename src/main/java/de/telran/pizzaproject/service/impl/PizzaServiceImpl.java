package de.telran.pizzaproject.service.impl;

import de.telran.pizzaproject.model.entity.Ingredient;
import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.repository.PizzaRepository;
import de.telran.pizzaproject.service.PizzaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PizzaServiceImpl implements PizzaService {

    private final PizzaRepository repository;

    public PizzaServiceImpl(PizzaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Pizza> getAllPizzas() {
        return repository.findAll();
    }

    @Override
    public Pizza getPizzaById(Long pizzaId) {
        return repository.getReferenceById(pizzaId);
    }

    @Override
    public Pizza addOrUpdate(Pizza pizza) {
        return repository.save(pizza);
    }

    @Override
    public void deletePizza(Long pizzaId) {
        repository.deleteById(pizzaId);
    }

    @Override
    public Map<Long, List<Ingredient>> getMapPizzaIdAndIngredients() {
        return repository.findAll().stream().collect(Collectors.toMap(Pizza::getId, Pizza::getIngredients));
    }

    @Override
    public Map<Long, Boolean> getMapPizzaIdIsSpicy(List<Pizza> pizzas) {
        return getMapPizzaIdAndIngredients().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().map(Ingredient::isSpicy)
                                .filter(b -> b).findAny().orElse(false)
                ));
    }

    @Override
    public Map<Long, Boolean> getMapPizzaIdIsVegetarian(List<Pizza> pizzas) {
        return getMapPizzaIdAndIngredients().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().map(Ingredient::isVegetarian)
                                .filter(b -> !b).findAny().orElse(true)
                ));
    }

    @Override
    public Map<Long, Boolean> getMapPizzaIdIsGlutenFree(List<Pizza> pizzas) {
        return getMapPizzaIdAndIngredients().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().map(Ingredient::isGlutenfree)
                                .filter(b -> !b).findAny().orElse(true)
                ));
    }

    @Override
    public List<Pizza> getAllPizzasBySize(Integer size) {
        return repository.getPizzasBySize(size);
    }

    @Override
    public List<Pizza> getAllPizzasBySizeAndByIngredient(Integer size, String ingredient) {
        return repository.findAllBySizeAndIngredients_NameContainingIgnoreCase(size, ingredient);
    }

    @Override
    public List<Pizza> getAllPizzasByIngredient(String ingredient) {
        return repository.findAllByIngredients_NameContainingIgnoreCase(ingredient);
    }

    @Override
    public List<Pizza> getAllPizzasByRestaurant(Long restaurantId) {
        return repository.findAllByRestaurant_Id(restaurantId);
    }
}
