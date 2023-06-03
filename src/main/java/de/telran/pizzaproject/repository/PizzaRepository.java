package de.telran.pizzaproject.repository;

import de.telran.pizzaproject.model.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    List<Pizza> getPizzasBySize(Integer size);
    List<Pizza> findAllByIngredients_NameContainingIgnoreCase(String ingredient);
    List<Pizza> findAllBySizeAndIngredients_NameContainingIgnoreCase(Integer size, String ingredient);
    List<Pizza> findAllByRestaurant_Id(Long restaurantId);

}
