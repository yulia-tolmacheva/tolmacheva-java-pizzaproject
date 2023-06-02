package de.telran.pizzaproject.repository;

import de.telran.pizzaproject.model.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findAllByAddressContainingIgnoreCase(String keyword);
    List<Restaurant> findAllByNameContainingIgnoreCase(String keyword);

}
