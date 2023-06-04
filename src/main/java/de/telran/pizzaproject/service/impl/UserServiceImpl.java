package de.telran.pizzaproject.service.impl;

import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.repository.UserRepository;
import de.telran.pizzaproject.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User addOrUpdate(User userToAdd) {
        return repository.save(userToAdd);
    }

    @Override
    public User getUserById(Long userToAddId) {
        return repository.getReferenceById(userToAddId);
    }

    @Override
    public void deleteUser(Long userId) {
        repository.deleteById(userId);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return repository.findFirstByUsernameIgnoreCase(username);
    }
}
