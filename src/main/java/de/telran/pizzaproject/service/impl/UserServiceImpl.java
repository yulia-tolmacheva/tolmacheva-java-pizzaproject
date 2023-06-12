package de.telran.pizzaproject.service.impl;

import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.repository.UserRepository;
import de.telran.pizzaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User addOrUpdate(User user) {
        Optional<User> byId = repository.findById(user.getId());

        if (byId.isPresent()) {
            if (user.getPassword().isEmpty()) {
                user.setPassword(byId.get().getPassword());
                return repository.save(user);
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        return repository.save(user);
    }

    @Override
    public User updateNameOrPassword(User user, Long id) {
        User byId = repository.getReferenceById(id);
        byId.setUsername(user.getUsername());
        if (!user.getPassword().isEmpty()) {
            byId.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        byId.setFirstName(user.getFirstName());
        byId.setLastName(user.getLastName());
        return repository.save(byId);
    }

    @Override
    public User getUserById(Long id) {
        return repository.getReferenceById(id);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return repository.findByUsernameIgnoreCase(username);
    }
}
