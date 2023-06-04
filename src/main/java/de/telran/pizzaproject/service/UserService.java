package de.telran.pizzaproject.service;


import de.telran.pizzaproject.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    User addOrUpdate(User userToAdd);

    User getUserById(Long userToAddId);

    void deleteUser(Long userId);

    Optional<User> getUserByUsername(String username);
}
