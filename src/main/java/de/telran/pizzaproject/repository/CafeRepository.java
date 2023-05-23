package de.telran.pizzaproject.repository;

import de.telran.pizzaproject.model.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    
}
