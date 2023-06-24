package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.RoleName;
import de.telran.pizzaproject.model.entity.Restaurant;
import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.security.MyPermissionEvaluator;
import de.telran.pizzaproject.security.SecurityConfig;
import de.telran.pizzaproject.service.PizzaDataProviderService;
import de.telran.pizzaproject.service.RestaurantService;
import de.telran.pizzaproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RestaurantsController.class)
@Import({SecurityConfig.class, MyPermissionEvaluator.class})
public class RestaurantsControllerWebLayerSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private UserService userService;

    @MockBean
    private PizzaDataProviderService pizzaDataProviderService;
    @MockBean
    private Authentication authentication;

    @MockBean
    private MyPermissionEvaluator permissionEvaluator;

    private Restaurant restaurantToTest;

    @BeforeEach
    void addRestaurant() {
        restaurantToTest = new Restaurant();
        restaurantToTest.setId(1L);
        restaurantToTest.setName("Pizzeria Bella");
        restaurantToTest.setCity("Berlin");
        restaurantToTest.setAddress("Hauptstraße 1");
        restaurantToTest.setPhone("+49 30 12345678");
    }

    @Test
    @WithAnonymousUser
    void shouldNotAllowAnonymousUserToEditOrCreate() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/auth/login"));

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/edit")
                        .param("restaurantId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/auth/login"));

        mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/new")
                        .flashAttr("restaurantToAdd", restaurantToTest))
                .andExpect(status().isForbidden());

        mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/new"))
                .andExpect(status().isForbidden());

        mockMvc.perform(MockMvcRequestBuilders.patch("/restaurants/edit")
                        .flashAttr("restaurantToUpdate", restaurantToTest))
                .andExpect(status().isForbidden());

        mockMvc.perform(MockMvcRequestBuilders.patch("/restaurants/edit"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void testGetAllRestaurants_AvailableToAnonymousUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(view().name("restaurant/restaurants"));

        verify(restaurantService).getAllRestaurants();
    }

    @Test
    @WithAnonymousUser
    void testSearchRestaurants_AvailableToAnonymousUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("restaurant/restaurants"));

        verify(restaurantService).getAllByAddressAndCity(null, null);
    }

    @Test
    @WithMockUser
    void testGetAllRestaurants_AvailableToAnyUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(view().name("restaurant/restaurants"));

        verify(restaurantService).getAllRestaurants();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testGetAddRestaurantDetails_ReturnsView_AsAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("restaurant/new"));
    }

    @Test
    @WithMockUser(authorities = {"OWNER", "USER"})
    void testGetAddRestaurantDetails_Forbidden_AsOwnerOrUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/new"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"OWNER", "USER"})
    void testPostRestaurantDetails_Forbidden_AsOwnerOrUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/new"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testPostAddRestaurant_asAdmin() throws Exception {
        when(restaurantService.addOrUpdate(any())).thenReturn(restaurantToTest);

        mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/new")
                        .with(csrf())
                        .flashAttr("restaurantToAdd", restaurantToTest))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/restaurants"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testDeleteRestaurant_asAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/restaurants/delete")
                        .param("restaurantId", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/restaurants"))
                .andExpect(flash().attributeExists("deleted"));
    }

    @Test
    @WithMockUser
    void testGetAndPatchEditRestaurantDetails_whenForbiddenByPermission() throws Exception {

        when(permissionEvaluator.
                hasPermission(any(), any(), any())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/edit")
                        .param("restaurantId", "1"))
                .andExpect(status().isForbidden());

        mockMvc.perform(MockMvcRequestBuilders.patch("/restaurants/edit")
                        .flashAttr("restaurantToUpdate", restaurantToTest)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetAndPatchEditRestaurantDetails_whenAllowedByPermission() throws Exception {
        User user = new User();
        user.setUsername("owner");
        user.setRoles(Collections.singletonList(RoleName.OWNER));
        user.setRestaurant(restaurantToTest);
        restaurantToTest.setOwner(user);

        when(permissionEvaluator.
                hasPermission(any(), eq(restaurantToTest.getId()), eq("edit"))).thenReturn(true);
        when(restaurantService.getRestaurantById(any())).thenReturn(restaurantToTest);

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/edit")
                        .param("restaurantId", restaurantToTest.getId().toString())
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("restaurant/edit"));

        mockMvc.perform(MockMvcRequestBuilders.patch("/restaurants/edit")
                        .with(user(user))
                        .flashAttr("restaurantToUpdate", restaurantToTest)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/restaurants"));
    }

}
