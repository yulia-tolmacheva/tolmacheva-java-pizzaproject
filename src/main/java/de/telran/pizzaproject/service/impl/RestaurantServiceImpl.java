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

    @Override
    public Restaurant getRestaurantById(Long restaurantId) {
        return repository.getReferenceById(restaurantId);
    }

    @Override
    public List<Restaurant> getAllByAddress(String keyword) {
        return repository.findAllByAddressContainingIgnoreCase(keyword);
    }

    @Override
    public List<Restaurant> getAllByCity(String city) {
        return repository.findAllByCityContainingIgnoreCase(city);
    }

    @Override
    public Restaurant addOrUpdate(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    @Override
    public void deletePizza(Long restaurantId) {
        repository.deleteById(restaurantId);
    }

}
