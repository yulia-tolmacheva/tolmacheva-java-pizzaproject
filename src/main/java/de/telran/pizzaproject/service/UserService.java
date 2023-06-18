package de.telran.pizzaproject.service;


import de.telran.pizzaproject.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> findUserByUsername(String username);
    User getUserById(Long id);
    User addUser(User user);
    User addUserAsAdmin(User user);
    User updateUsernameOrName(User user);
    User updateUserAsAdmin(User user);
    User updatePassword(Long id, String password);
    void deleteUser(Long id);
    List<User> getAllUsersWithOwnerRole();
    List<User> getAllUsersByUsername(String username);
}
