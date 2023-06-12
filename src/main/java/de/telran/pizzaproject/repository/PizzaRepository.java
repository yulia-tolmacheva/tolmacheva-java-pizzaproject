package de.telran.pizzaproject.repository;

import de.telran.pizzaproject.model.entity.Pizza;
import de.telran.pizzaproject.model.PizzaSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    List<Pizza> getPizzasBySize(PizzaSize size);
    List<Pizza> findAllByIngredients_NameContainingIgnoreCase(String ingredient);
    List<Pizza> findAllBySizeAndIngredients_NameContainingIgnoreCase(PizzaSize size, String ingredient);
    List<Pizza> findAllByRestaurant_Id(Long restaurantId);
    List<Pizza> findAllByRestaurant_IdAndSizeAndAndIngredients_NameContainingIgnoreCase(Long restaurantId, PizzaSize size, String ingredient);

    List<Pizza> findAllByRestaurant_IdAndSize(Long restaurantId, PizzaSize size);

    List<Pizza> findAllByRestaurant_IdAndIngredients_NameContainingIgnoreCase(Long restaurantId, String ingredient);

    Optional<Pizza> getFirstByNameIgnoreCaseAndSizeAndRestaurant_Id(String name, PizzaSize size, Long id);
}
