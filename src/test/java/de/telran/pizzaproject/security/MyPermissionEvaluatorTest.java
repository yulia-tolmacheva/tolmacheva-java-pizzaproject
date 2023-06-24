package de.telran.pizzaproject.security;

import de.telran.pizzaproject.model.RoleName;
import de.telran.pizzaproject.model.entity.Restaurant;
import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.service.RestaurantService;
import de.telran.pizzaproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class MyPermissionEvaluatorTest {

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    private MyPermissionEvaluator permissionEvaluator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        permissionEvaluator = new MyPermissionEvaluator();
        permissionEvaluator.setRestaurantService(restaurantService);
        permissionEvaluator.setUserService(userService);
    }

    @Test
    void testHasPermission_AdminRole_ReturnsTrue() {
        User user = new User();
        user.setRoles(Collections.singletonList(RoleName.ADMIN));

        when(authentication.getPrincipal()).thenReturn(user);

        Object targetDomainObject = 1L;
        Object permission = "edit";

        boolean result = permissionEvaluator.hasPermission(authentication, targetDomainObject, permission);

        assertTrue(result);

        permission = "editAccount";

        result = permissionEvaluator.hasPermission(authentication, targetDomainObject, permission);

        assertTrue(result);
    }

    @Test
    void testHasPermission_OwnerRoleAndMatchingRestaurantOwner_ReturnsTrue() {
        User user = new User();
        user.setId(1L);
        user.setRoles(Collections.singletonList(RoleName.OWNER));
        when(authentication.getPrincipal()).thenReturn(user);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setOwner(user);
        when(restaurantService.getRestaurantById(1L)).thenReturn(restaurant);

        Object targetDomainObject = 1L;
        Object permission = "edit";

        boolean result = permissionEvaluator.hasPermission(authentication, targetDomainObject, permission);

        assertTrue(result);
        verify(restaurantService, times(1)).getRestaurantById(1L);
    }

    @Test
    void testHasPermission_OwnerRoleAndNotMatchingRestaurantOwner_ReturnsFalse() {
        User user = new User();
        user.setId(1L);
        user.setRoles(Collections.singletonList(RoleName.OWNER));
        when(authentication.getPrincipal()).thenReturn(user);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setOwner(new User());
        when(restaurantService.getRestaurantById(1L)).thenReturn(restaurant);

        Object targetDomainObject = 1L;
        Object permission = "edit";

        boolean result = permissionEvaluator.hasPermission(authentication, targetDomainObject, permission);

        assertFalse(result);
        verify(restaurantService, times(1)).getRestaurantById(1L);
    }

    @Test
    void testHasPermission_OwnerRoleAndRestaurantWithoutOwner_ReturnsFalse() {
        User user = new User();
        user.setId(1L);
        user.setRoles(Collections.singletonList(RoleName.OWNER));
        when(authentication.getPrincipal()).thenReturn(user);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        when(restaurantService.getRestaurantById(1L)).thenReturn(restaurant);

        Object targetDomainObject = 1L;
        Object permission = "edit";

        boolean result = permissionEvaluator.hasPermission(authentication, targetDomainObject, permission);

        assertFalse(result);
        verify(restaurantService, times(1)).getRestaurantById(1L);
    }

    @Test
    void testHasPermission_UserAndMatchingUser_ReturnsTrue() {
        User user = new User();
        user.setId(1L);
        when(authentication.getPrincipal()).thenReturn(user);

        User userToEdit = new User();
        userToEdit.setId(1L);
        when(userService.getUserById(1L)).thenReturn(userToEdit);

        Object targetDomainObject = 1L;
        Object permission = "editAccount";

        boolean result = permissionEvaluator.hasPermission(authentication, targetDomainObject, permission);

        assertTrue(result);
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testHasPermission_UserAndNotMatchingUser_ReturnsFalse() {
        User user = new User();
        user.setId(1L);
        when(authentication.getPrincipal()).thenReturn(user);

        User userToEdit = new User();
        userToEdit.setId(2L);
        when(userService.getUserById(2L)).thenReturn(userToEdit);

        Object targetDomainObject = 1L;
        Object permission = "editAccount";

        boolean result = permissionEvaluator.hasPermission(authentication, targetDomainObject, permission);

        assertFalse(result);
        verify(userService, times(1)).getUserById(1L);
    }
}