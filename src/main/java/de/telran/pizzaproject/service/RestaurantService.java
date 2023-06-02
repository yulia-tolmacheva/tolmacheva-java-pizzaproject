package de.telran.pizzaproject.service;


import de.telran.pizzaproject.model.entity.Restaurant;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> getAllRestaurants();

    Restaurant addOrUpdate(Restaurant restaurant);

    void deletePizza(Long restaurantId);

    Restaurant getRestaurantById(Long restaurantId);

    List<Restaurant> getAllByAddress(String keyword);
}
