package de.telran.pizzaproject.service.impl;

import de.telran.pizzaproject.model.RoleName;
import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.repository.UserRepository;
import de.telran.pizzaproject.service.UserService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
//        if (user.getRoleName() == null) {
//            user.setRoleName(RoleName.USER);
//        }
        if (user.getRoles().isEmpty()) {
            user.setRoles(List.of(RoleName.USER));
        }
        return repository.save(user);
    }

    @Override
    public User addUserAsAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public User updateUserAsAdmin(User user) {
        if (user.getPassword().isEmpty()) {
            User byId = repository.getReferenceById(user.getId());
            user.setPassword(byId.getPassword());
            return repository.save(user);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
    public void deleteUser(Long id) throws ConstraintViolationException {
        User userToDelete = repository.getReferenceById(id);
        if (userToDelete.getRoles().contains(RoleName.ADMIN)) {
//        if (userToDelete.getRoleName().equals(RoleName.ADMIN)) {
            throw new ConstraintViolationException("User with ADMIN role can't be deleted", Set.of());
        }
        if (userToDelete.getRestaurant() != null) {
            userToDelete.getRestaurant().setOwner(null);
        }
        repository.deleteById(userToDelete.getId());
    }

    @Override
    public List<User> getAllAvailableUsersWithOwnerRole() {
//        return repository.findAll().stream().filter((user) -> user.getRoleName().equals(RoleName.OWNER))
        return repository.findAll().stream().filter((user) -> user.getRoles()
                        .contains(RoleName.OWNER))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllUsersByUsername(String username) {
        return repository.findAllByUsernameContainingIgnoreCase(username);
    }

}
