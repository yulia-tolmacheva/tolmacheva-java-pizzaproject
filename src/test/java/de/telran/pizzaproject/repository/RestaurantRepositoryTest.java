package de.telran.pizzaproject.repository;

import de.telran.pizzaproject.model.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class RestaurantRepositoryTest {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void testFindAllByAddressContainingIgnoreCase() {
        List<Restaurant> foundRestaurants = restaurantRepository.findAllByAddressContainingIgnoreCase("ha");
        assertEquals(1, foundRestaurants.size());

        foundRestaurants = restaurantRepository.findAllByAddressContainingIgnoreCase("1");
        assertEquals(2, foundRestaurants.size());

        foundRestaurants = restaurantRepository.findAllByAddressContainingIgnoreCase("w");
        assertEquals(0, foundRestaurants.size());
    }

    @Test
    public void testFindAllByAddressContainingIgnoreCaseAndCityContainingIgnoreCase() {
        List<Restaurant> foundRestaurants = restaurantRepository
                .findAllByAddressContainingIgnoreCaseAndCityContainingIgnoreCase("ha", "ber");
        assertEquals(1, foundRestaurants.size());

        foundRestaurants = restaurantRepository
                .findAllByAddressContainingIgnoreCaseAndCityContainingIgnoreCase("ha", "fra");
        assertEquals(0, foundRestaurants.size());
    }

    @Test
    public void testFindAllByCityContainingIgnoreCase() {
        List<Restaurant> foundRestaurants = restaurantRepository.findAllByCityContainingIgnoreCase("fra");
        assertEquals(1, foundRestaurants.size());
    }

}