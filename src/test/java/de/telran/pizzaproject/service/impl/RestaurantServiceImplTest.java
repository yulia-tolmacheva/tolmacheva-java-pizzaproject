package de.telran.pizzaproject.service.impl;

import de.telran.pizzaproject.model.entity.Restaurant;
import de.telran.pizzaproject.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository repository;

    private RestaurantServiceImpl service;

    private static List<Restaurant> restaurants;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new RestaurantServiceImpl(repository);

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(1L);
        restaurant1.setName("Pizzeria Bella");
        restaurant1.setCity("Berlin");
        restaurant1.setAddress("Hauptstraße 1");
        restaurant1.setPhone("+49 30 12345678");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(2L);
        restaurant2.setName("La Pizzetta");
        restaurant2.setCity("Frankfurt");
        restaurant2.setAddress("Kaiserstraße 10");
        restaurant2.setPhone("+49 69 98765432");


        restaurants = Arrays.asList(restaurant1, restaurant2);
    }

    @Test
    void testGetAllRestaurants() {
        when(repository.findAll()).thenReturn(restaurants);
        List<Restaurant> result = service.getAllRestaurants();
        assertEquals(restaurants, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetRestaurantById() {
        when(repository.getReferenceById(1L)).thenReturn(restaurants.get(0));
        Restaurant result = service.getRestaurantById(1L);
        assertEquals(restaurants.get(0), result);
        verify(repository, times(1)).getReferenceById(1L);
    }

    @Test
    void testGetAllByAddressAndCity_WithBothAddressAndCity() {
        String address = "1";
        String city = "Berlin";
        when(repository.findAllByAddressContainingIgnoreCaseAndCityContainingIgnoreCase(address, city))
                .thenReturn(Collections.singletonList(restaurants.get(0)));

        List<Restaurant> result = service.getAllByAddressAndCity(address, city);

        assertEquals(List.of(restaurants.get(0)), result);

        verify(repository, times(1))
                .findAllByAddressContainingIgnoreCaseAndCityContainingIgnoreCase(address, city);
        verify(repository, never()).findAllByAddressContainingIgnoreCase(address);
        verify(repository, never()).findAllByCityContainingIgnoreCase(city);
    }

    @Test
    void testGetAllByAddressAndCity_WithAddressOnly() {
        String address = "Address";
        when(repository.findAllByAddressContainingIgnoreCase(address)).thenReturn(restaurants);

        List<Restaurant> result = service.getAllByAddressAndCity(address, null);

        assertEquals(restaurants, result);

        verify(repository, times(1)).findAllByAddressContainingIgnoreCase(address);
        verify(repository, never()).findAllByAddressContainingIgnoreCaseAndCityContainingIgnoreCase(any(), any());
        verify(repository, never()).findAllByCityContainingIgnoreCase(any());
    }

    @Test
    void testGetAllByAddressAndCity_WithCityOnly() {
        String city = "City";
        when(repository.findAllByCityContainingIgnoreCase(city)).thenReturn(restaurants);

        List<Restaurant> result = service.getAllByAddressAndCity(null, city);

        assertEquals(restaurants, result);
        verify(repository, times(1)).findAllByCityContainingIgnoreCase(city);
        verify(repository, never()).findAllByAddressContainingIgnoreCase(any());
        verify(repository, never()).findAllByAddressContainingIgnoreCaseAndCityContainingIgnoreCase(any(), any());
    }

    @Test
    void testAddOrUpdate() {
        when(repository.save(restaurants.get(0))).thenReturn(restaurants.get(0));

        Restaurant result = service.addOrUpdate(restaurants.get(0));

        assertEquals(restaurants.get(0), result);
        verify(repository, times(1)).save(restaurants.get(0));
    }

    @Test
    void testDeleteRestaurant() {
        service.deleteRestaurant(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}