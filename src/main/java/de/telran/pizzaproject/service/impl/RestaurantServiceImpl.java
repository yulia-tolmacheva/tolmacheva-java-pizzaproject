package de.telran.pizzaproject.service.impl;

import de.telran.pizzaproject.model.entity.Restaurant;
import de.telran.pizzaproject.repository.RestaurantRepository;
import de.telran.pizzaproject.service.RestaurantService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantServiceImpl(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return repository.findAll();
    }
}
