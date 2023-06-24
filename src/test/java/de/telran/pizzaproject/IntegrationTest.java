package de.telran.pizzaproject;

import de.telran.pizzaproject.model.entity.Restaurant;
import de.telran.pizzaproject.repository.RestaurantRepository;
import de.telran.pizzaproject.security.MyPermissionEvaluator;
import de.telran.pizzaproject.security.SecurityConfig;
import de.telran.pizzaproject.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc(addFilters = false)
@Import({SecurityConfig.class, MyPermissionEvaluator.class})
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantRepository restaurantRepository;
    @MockBean
    private RestaurantService restaurantService;

    @Test
    @Transactional
    public void testGetRestaurantById() throws Exception {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Pizzeria Bella");

        when(restaurantService.getRestaurantById(1L)).thenReturn(restaurant);

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.view().name("restaurant/view"));

        Restaurant foundInRepoRestaurant = restaurantRepository.getReferenceById(1L);
        assertEquals(restaurantService.getRestaurantById(1L).getName(), foundInRepoRestaurant.getName());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = "ADMIN")
    public void testAddOrUpdateRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(10L);
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("Test Restaurant");
        restaurant.setCity("Test Restaurant");
        restaurant.setPhone("1234567654");
        when(restaurantService.addOrUpdate(restaurant)).thenReturn(restaurant);

        assertEquals(2, restaurantRepository.findAll().size());
        mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/new")
                        .with(csrf())
                        .flashAttr("restaurantToAdd", restaurant))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        verify(restaurantService, times(1)).addOrUpdate(restaurant);
    }

}
