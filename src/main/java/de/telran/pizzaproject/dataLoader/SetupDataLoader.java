package de.telran.pizzaproject.dataLoader;

import de.telran.pizzaproject.model.entity.Role;
import de.telran.pizzaproject.model.entity.RoleName;
import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.repository.RoleRepository;
import de.telran.pizzaproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {
//    public static final String ADMIN = "ADMIN";
//    public static final String USER = "USER";
//    public static final String CREATOR = "CREATOR";
    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Role admin = createRoleIfNotFound(RoleName.ADMIN);
        Role user = createRoleIfNotFound(RoleName.USER);
        Role creator = createRoleIfNotFound(RoleName.CREATOR);

        createUserIfNotFound(RoleName.ADMIN.name(), admin);
        createUserIfNotFound(RoleName.USER.name(), user);
        createUserIfNotFound(RoleName.CREATOR.name(), creator);

        alreadySetup = true;
    }

    private void createUserIfNotFound(String name, Role role) {
        Optional<User> userInDB = userRepository.findByUsernameIgnoreCase(name);

        if (userInDB.isEmpty()) {
            User user = new User();
            user.setUsername(name.toLowerCase());
            user.setPassword(passwordEncoder.encode(name.toLowerCase()));
            user.setRoles(Arrays.asList(role));
            user.setEnabled(true);
            userRepository.save(user);
        }

    }

    @Transactional
    public Role createRoleIfNotFound(RoleName roleName) {

        Role role = roleRepository.findRoleByRoleName(roleName);
        if (role == null) {
            role = new Role(roleName);
            roleRepository.save(role);
        }
        return role;
    }
}