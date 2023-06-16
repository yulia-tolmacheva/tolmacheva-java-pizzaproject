package de.telran.pizzaproject.service.impl;

import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.repository.UserRepository;
import de.telran.pizzaproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return repository.getReferenceById(id);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return repository.findByUsernameIgnoreCase(username);
    }

    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        return repository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User byId = repository.getReferenceById(user.getId());
        user.setPassword(byId.getPassword());
        return repository.save(user);
    }

    @Override
    public User updateUsernameOrName(User user) {
        User byId = repository.getReferenceById(user.getId());
        byId.setUsername(user.getUsername());
        byId.setFirstName(user.getFirstName());
        byId.setLastName(user.getLastName());
        return repository.save(byId);
    }

    @Override
    public User updatePassword(Long id, String password) {
        User byId = repository.getReferenceById(id);
        byId.setPassword(passwordEncoder.encode(password));
        return repository.save(byId);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

}
