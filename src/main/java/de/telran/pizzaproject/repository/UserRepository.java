package de.telran.pizzaproject.repository;

import de.telran.pizzaproject.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
