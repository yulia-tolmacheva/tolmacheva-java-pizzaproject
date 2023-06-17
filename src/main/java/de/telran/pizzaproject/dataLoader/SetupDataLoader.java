package de.telran.pizzaproject.dataLoader;

import de.telran.pizzaproject.model.entity.Role;
import de.telran.pizzaproject.model.RoleName;
import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.service.RoleService;
import de.telran.pizzaproject.service.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    private final UserService userService;
    private final RoleService roleService;

    public SetupDataLoader(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Role admin = createRoleIfNotFound(RoleName.ADMIN);
        Role user = createRoleIfNotFound(RoleName.USER);
        Role creator = createRoleIfNotFound(RoleName.CREATOR);
        Role owner = createRoleIfNotFound(RoleName.OWNER);
        Role owner2 = createRoleIfNotFound(RoleName.OWNER);

        createUserIfNotFound(RoleName.ADMIN.name(), admin);
        createUserIfNotFound(RoleName.USER.name(), user);
        createUserIfNotFound(RoleName.CREATOR.name(), creator);
        createUserIfNotFound(RoleName.OWNER.name(), owner);
        createUserIfNotFound(RoleName.OWNER.name()+"2", owner);

        alreadySetup = true;
    }

    private void createUserIfNotFound(String name, Role role) {
        Optional<User> userInDB = userService.findUserByUsername(name);

        if (userInDB.isEmpty()) {
            User user = new User();
            user.setUsername(name.toLowerCase());
            user.setFirstName(name.toLowerCase());
            user.setLastName(name.toLowerCase());
            user.setPassword(name.toLowerCase());
            user.setRoles(Collections.singletonList(role));
            user.setEnabled(true);
            userService.addUser(user);
        }

    }

    @Transactional
    public Role createRoleIfNotFound(RoleName roleName) {

        Role role = roleService.findRoleByRoleName(roleName);
        if (role == null) {
            role = new Role(roleName);
            roleService.add(role);
        }
        return role;
    }
}