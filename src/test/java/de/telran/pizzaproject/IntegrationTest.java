package de.telran.pizzaproject;

import de.telran.pizzaproject.model.entity.Restaurant;
import de.telran.pizzaproject.repository.RestaurantRepository;
import de.telran.pizzaproject.security.MyPermissionEvaluator;
import de.telran.pizzaproject.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@Import({SecurityConfig.class, MyPermissionEvaluator.class})
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    @Transactional
    public void testGetRestaurantById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.view().name("restaurant/view"));

        Restaurant foundInRepoRestaurant = restaurantRepository.getReferenceById(1L);
        assertEquals("Pizzeria Bella", foundInRepoRestaurant.getName());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = "ADMIN")
    public void testAddOrUpdateRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("Test Restaurant");
        restaurant.setCity("Test Restaurant");
        restaurant.setPhone("1234567654");

        assertEquals(2, restaurantRepository.findAll().size());

        mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/new")
                        .with(csrf())
                        .flashAttr("restaurantToAdd", restaurant))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        assertEquals(3, restaurantRepository.findAll().size());

        assertEquals(restaurant.getName(), restaurantRepository.findAll().get(2).getName());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = "ADMIN")
    public void testDeleteRestaurant() throws Exception {
        assertEquals(2, restaurantRepository.findAll().size());
        assertTrue(restaurantRepository.existsById(1L));

        mockMvc.perform(MockMvcRequestBuilders.delete("/restaurants/delete")
                        .with(csrf())
                        .param("restaurantId", "1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        assertEquals(1, restaurantRepository.findAll().size());
        assertFalse(restaurantRepository.existsById(1L));
    }

}
