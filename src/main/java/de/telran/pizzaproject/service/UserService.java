package de.telran.pizzaproject.service;


import de.telran.pizzaproject.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    User addOrUpdate(User user);

    User getUserById(Long id);

    void deleteUser(Long id);

    Optional<User> getUserByUsername(String username);
}
