package de.telran.pizzaproject.service.impl;

import de.telran.pizzaproject.model.entity.Ingredient;
import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.repository.PizzaRepository;
import de.telran.pizzaproject.service.PizzaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public Pizza add(Pizza pizza) {
        return repository.save(pizza);
    }

    @Override
    public Pizza update(Pizza pizza) {
        Optional<Pizza> pizzaById = repository.findById(pizza.getId());
        if (pizzaById.isPresent()) {
            return repository.save(pizza);
        }
        else return null;
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
    public Map<Long, Boolean> getMapPizzaIdIsSpicy() {
        return getMapPizzaIdAndIngredients().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().anyMatch(Ingredient::isSpicy)
                ));
    }

    @Override
    public Map<Long, Boolean> getMapPizzaIdIsVegetarian() {
        return getMapPizzaIdAndIngredients().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().allMatch(Ingredient::getIsVegetarian)
                ));
    }

    @Override
    public Map<Long, Boolean> getMapPizzaIdIsGlutenFree() {
        return getMapPizzaIdAndIngredients().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().allMatch(Ingredient::isGlutenfree)
                ));
    }


    @Override
    public List<Pizza> applyRestaurantAndSizeAndIngredientFilters(Long restaurantId, Integer size, String ingredient) {
        if (size != null && ingredient != null && !ingredient.isEmpty()) {
            return repository.findAllByRestaurant_IdAndSizeAndAndIngredients_NameContainingIgnoreCase(restaurantId, size, ingredient);
        } else if (size != null) {
            return repository.findAllByRestaurant_IdAndSize(restaurantId, size);
        } else if (ingredient != null && !ingredient.isEmpty()) {
            return repository.findAllByRestaurant_IdAndIngredients_NameContainingIgnoreCase(restaurantId, ingredient);
        } else {
            return repository.findAllByRestaurant_Id(restaurantId);
        }
    }

    @Override
    public List<Pizza> applySizeOrIngredientFilters(Integer size, String ingredient) {
        if (size != null && ingredient != null && !ingredient.isEmpty()) {
            return repository.findAllBySizeAndIngredients_NameContainingIgnoreCase(size, ingredient);
        } else if (size != null) {
            return repository.getPizzasBySize(size);
        } else if (ingredient != null && !ingredient.isEmpty()) {
            return repository.findAllByIngredients_NameContainingIgnoreCase(ingredient);
        } else {
            return repository.findAll();
        }
    }

    @Override
    public Optional<Pizza> getPizzaByNameAndBySizeAndRestaurant(String name, Integer size, Long id) {
        return repository.getFirstByNameIgnoreCaseAndSizeAndRestaurant_Id(name, size, id);
    }
}
