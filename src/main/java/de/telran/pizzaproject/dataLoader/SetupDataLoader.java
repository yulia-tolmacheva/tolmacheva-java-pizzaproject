package de.telran.pizzaproject.dataLoader;

import de.telran.pizzaproject.model.RoleName;
import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    private final UserService userService;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        createUserIfNotFound(RoleName.ADMIN.name(), RoleName.ADMIN);
        createUserIfNotFound(RoleName.USER.name(), RoleName.USER);
        createUserIfNotFound(RoleName.OWNER.name(), RoleName.OWNER);
        createUserIfNotFound(RoleName.OWNER.name()+"2", RoleName.OWNER);

        alreadySetup = true;
    }

    private void createUserIfNotFound(String name, RoleName roleName) {
        Optional<User> userInDB = userService.findUserByUsername(name);

        if (userInDB.isEmpty()) {
            User user = new User();
            user.setUsername(name.toLowerCase());
            user.setFirstName(name.toLowerCase());
            user.setLastName(name.toLowerCase());
            user.setPassword(name.toLowerCase());
            user.setRoles(Collections.singletonList(roleName));
            user.setEnabled(true);
            userService.addUser(user);
        }

    }
}