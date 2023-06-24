package de.telran.pizzaproject.security;

import de.telran.pizzaproject.model.RoleName;
import de.telran.pizzaproject.model.entity.Restaurant;
import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.service.RestaurantService;
import de.telran.pizzaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

@Component
//@Primary
public class MyPermissionEvaluator implements PermissionEvaluator {

    private RestaurantService restaurantService;
    private UserService userService;

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        User user;
//        try {
            user = (User) authentication.getPrincipal();
//        } catch (ClassCastException e) {
//            return false;
//        }

        for (RoleName role : user.getRoles()) {
            if (role == RoleName.ADMIN) {
                return true;
            }
        }

        if (targetDomainObject instanceof Long && permission instanceof String) {
            Long id = (Long) targetDomainObject;
            String requiredPermission = (String) permission;

            if (requiredPermission.equals("edit")) {
                Restaurant restaurant = restaurantService.getRestaurantById(id);
                return restaurant.getOwner() != null
                        && Objects.equals(user.getId(), restaurant.getOwner().getId());
            } else if (requiredPermission.equals("editAccount")) {
                User userToEdit = userService.getUserById(id);
                return userToEdit != null
                        && user.getId().equals(id);
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId,
                                 String targetType, Object permission) {
        return false;
    }
}

