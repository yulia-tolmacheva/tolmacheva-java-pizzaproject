package de.telran.pizzaproject.service;


import de.telran.pizzaproject.model.entity.User;

import java.util.List;

public interface UsersService {

    List<User> getAllUsers();

    User addOrUpdate(User userToAdd);

    User getUserById(Long userToAddId);

    void deleteUser(Long userId);
}
