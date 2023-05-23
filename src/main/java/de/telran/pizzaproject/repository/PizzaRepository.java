package de.telran.pizzaproject.repository;

import de.telran.pizzaproject.model.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {

}
