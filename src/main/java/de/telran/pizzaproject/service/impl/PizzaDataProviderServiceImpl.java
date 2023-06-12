package de.telran.pizzaproject.service.impl;

import de.telran.pizzaproject.model.entity.Ingredient;
import de.telran.pizzaproject.model.PizzaSize;
import de.telran.pizzaproject.model.entity.Restaurant;
import de.telran.pizzaproject.service.IngredientService;
import de.telran.pizzaproject.service.PizzaDataProviderService;
import de.telran.pizzaproject.service.PizzaService;
import de.telran.pizzaproject.service.RestaurantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PizzaDataProviderServiceImpl implements PizzaDataProviderService {
    private final PizzaService pizzaService;
    private final IngredientService ingredientService;
    private final RestaurantService restaurantService;

    public PizzaDataProviderServiceImpl(PizzaService pizzaService,
                                        IngredientService ingredientService,
                                        RestaurantService restaurantService) {
        this.pizzaService = pizzaService;
        this.ingredientService = ingredientService;
        this.restaurantService = restaurantService;
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @Override
    public List<PizzaSize> getAllPizzaSizes() {
        return List.of(PizzaSize.values());
    }

    @Override
    public Map<Long, Boolean> getSpicyPizzas() {
        return pizzaService.getMapPizzaIdIsSpicy();
    }

    @Override
    public Map<Long, Boolean> getVeggiePizzas() {
        return pizzaService.getMapPizzaIdIsVegetarian();
    }

    @Override
    public Map<Long, Boolean> getGlutenFreePizzas() {
        return pizzaService.getMapPizzaIdIsGlutenFree();
    }
}
