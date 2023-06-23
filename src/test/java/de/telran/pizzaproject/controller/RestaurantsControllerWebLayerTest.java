package de.telran.pizzaproject.controller;

import de.telran.pizzaproject.model.entity.Restaurant;
import de.telran.pizzaproject.service.PizzaDataProviderService;
import de.telran.pizzaproject.service.RestaurantService;
import de.telran.pizzaproject.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = RestaurantsController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class RestaurantsControllerWebLayerTest {

    private static List<Restaurant> restaurants;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private UserService userService;

    @MockBean
    private PizzaDataProviderService pizzaDataProviderService;

    @BeforeAll
    static void addRestaurantsToTheList() {
        restaurants = new ArrayList<>();
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(1L);
        restaurant1.setName("Pizzeria Bella");
        restaurant1.setCity("Berlin");
        restaurant1.setAddress("Hauptstraße 1");
        restaurant1.setPhone("+49 30 12345678");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(2L);
        restaurant2.setName("La Pizzetta");
        restaurant2.setCity("Frankfurt");
        restaurant2.setAddress("Kaiserstraße 10");
        restaurant2.setPhone("+49 69 98765432");

        restaurants.add(restaurant1);
        restaurants.add(restaurant2);
    }

    @Test
    void getAllMethod_AddsListOfAllRestaurantsToTheModelAndReturnsAView() throws Exception {
        when(restaurantService.getAllRestaurants()).thenReturn(restaurants);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/restaurants"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("restaurant/restaurants"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("filteredList"))
                .andExpect(MockMvcResultMatchers.model()
                        .attribute("filteredList", restaurants));

        verify(restaurantService).getAllRestaurants();
    }

    @Test
    void getSearchByAddressAndCity_AcceptsNullParams_AndReturnsAView() throws Exception {
        String address = null;
        String city = null;
        when(restaurantService.getAllByAddressAndCity(address, city)).thenReturn(restaurants);

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/search"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("restaurant/restaurants"))
                .andExpect(MockMvcResultMatchers.model()
                        .attribute("filteredList", restaurants))
                .andExpect(MockMvcResultMatchers.model()
                        .attribute("address", address))
                .andExpect(MockMvcResultMatchers.model()
                        .attribute("city", city));

        verify(restaurantService).getAllByAddressAndCity(address, city);
    }

    @Test
    void getSearchByAddressAndCity_AcceptsJustCity_AndReturnsAView() throws Exception {
        String address = null;
        String city = "Berlin";

        List<Restaurant> filteredList = new ArrayList<>();

        when(restaurantService.getAllByAddressAndCity(address, city)).
                thenReturn(filteredList);

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/search")
                        .param("city", city)
                        .param("address", address))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("restaurant/restaurants"))
                .andExpect(MockMvcResultMatchers.model()
                        .attribute("filteredList", filteredList))
                .andExpect(MockMvcResultMatchers.model()
                        .attribute("address", address))
                .andExpect(MockMvcResultMatchers.model()
                        .attribute("city", city));

        verify(restaurantService).getAllByAddressAndCity(address, city);
    }

    @Test
    void getSearchByAddressAndCity_AcceptsParams_AndReturnsAView() throws Exception {
        String address = "Kais";
        String city = "Berlin";

        List<Restaurant> filteredList = new ArrayList<>();

        when(restaurantService.getAllByAddressAndCity(address, city)).
                thenReturn(filteredList);

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/search")
                        .param("city", city)
                        .param("address", address))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("restaurant/restaurants"))
                .andExpect(MockMvcResultMatchers.model()
                        .attribute("filteredList", filteredList))
                .andExpect(MockMvcResultMatchers.model()
                        .attribute("address", address))
                .andExpect(MockMvcResultMatchers.model()
                        .attribute("city", city));

        verify(restaurantService).getAllByAddressAndCity(address, city);
    }

    @Test
    void testAddRestaurantDetails_CreatesNewObject_ReturnsView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/new")
                        .flashAttr("restaurantToAdd", new Restaurant()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("restaurant/new"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ownersList"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("restaurantToAdd"));
    }

    @Test
    void testPostMethodAddRestaurant_ValidationErrors_ReturnsSameView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/new")
                        .flashAttr("restaurantToAdd", new Restaurant()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("restaurant/new"));
    }

    @Test
    void testPostMethodAddRestaurant_NoValidationErrors_RedirectsToRestaurants() throws Exception {
        when(restaurantService.addOrUpdate(restaurants.get(1))).thenReturn(restaurants.get(1));

        mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/new")
                        .flashAttr("restaurantToAdd", restaurants.get(1)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/restaurants"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("added"))
                .andExpect(MockMvcResultMatchers.flash().attribute("added", restaurants.get(1).getName()));

        verify(restaurantService).addOrUpdate(restaurants.get(1));
    }

    @Test
    void testDeleteRestaurant_AndRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/restaurants/delete")
                        .param("restaurantId", String.valueOf(restaurants.get(1).getId()))
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/restaurants"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("deleted"))
                .andExpect(MockMvcResultMatchers.flash().attribute("deleted", restaurants.get(1).getId()));

        verify(restaurantService).deletePizza(restaurants.get(1).getId());
    }

    @Test
    void testOpenEditPage_ReturnsView_ContainsRestaurantAttribute() throws Exception {
        when(restaurantService.getRestaurantById(restaurants.get(1).getId())).thenReturn(restaurants.get(1));

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/edit")
                        .param("restaurantId", String.valueOf(restaurants.get(1).getId()))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("restaurant/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("restaurantToUpdate"))
                .andExpect(MockMvcResultMatchers.model().attribute("restaurantToUpdate", restaurants.get(1)))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ownersList"));

        verify(restaurantService).getRestaurantById(restaurants.get(1).getId());
        verify(userService).getAllAvailableUsersWithOwnerRole();
    }

    @Test
    void testEditRestaurant_ValidationErrors_ReturnsEditView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/restaurants/edit")
                        .flashAttr("restaurantToUpdate", new Restaurant()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("restaurant/edit"));
    }

    @Test
    void testEditRestaurant_NoValidationErrors_RedirectsToRestaurants() throws Exception {
        when(restaurantService.addOrUpdate(restaurants.get(1))).thenReturn(restaurants.get(1));

        mockMvc.perform(MockMvcRequestBuilders.patch("/restaurants/edit")
                        .flashAttr("restaurantToUpdate", restaurants.get(1))
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/restaurants"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("updated"))
                .andExpect(MockMvcResultMatchers.flash().attribute("updated", restaurants.get(1).getName()));

        verify(restaurantService).addOrUpdate(restaurants.get(1));
    }
}
