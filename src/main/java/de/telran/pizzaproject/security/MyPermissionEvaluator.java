package de.telran.pizzaproject.security;

import de.telran.pizzaproject.model.RoleName;
import de.telran.pizzaproject.model.entity.Restaurant;
import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.service.RestaurantService;
import de.telran.pizzaproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@RequiredArgsConstructor
public class MyPermissionEvaluator implements PermissionEvaluator {

    private final RestaurantService restaurantService;
    private final UserService userService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject instanceof Long && permission instanceof String) {
            Long id = (Long) targetDomainObject;
            String requiredPermission = (String) permission;

            User user = (User) authentication.getPrincipal();
            for (RoleName role : user.getRoles()) {
                if (role == RoleName.ADMIN) {
                    return true;
                }
            }

            if (requiredPermission.equals("edit")) {
                Restaurant restaurant = restaurantService.getRestaurantById(id);
                return restaurant.getOwner() != null && user.getUsername().equals(restaurant.getOwner().getUsername());
            } else if (requiredPermission.equals("editAccount")) {
                User userToEdit = userService.getUserById(id);
                return userToEdit != null && user.getUsername().equals(userToEdit.getUsername());
            }
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}

