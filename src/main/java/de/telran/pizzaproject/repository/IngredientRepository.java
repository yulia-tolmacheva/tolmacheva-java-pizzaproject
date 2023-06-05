package de.telran.pizzaproject.repository;

import de.telran.pizzaproject.model.entity.Ingredient;
import de.telran.pizzaproject.model.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findFirstByNameIgnoreCase(String name);
}
