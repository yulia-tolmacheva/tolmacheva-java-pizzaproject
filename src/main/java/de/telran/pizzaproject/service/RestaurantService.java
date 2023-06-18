package de.telran.pizzaproject.service;


import de.telran.pizzaproject.model.entity.Restaurant;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> getAllRestaurants();

    Restaurant getRestaurantById(Long restaurantId);

    List<Restaurant> getAllByAddressAndCity(String address, String city);

    Restaurant addOrUpdate(Restaurant restaurant);

    void deletePizza(Long restaurantId);
}
