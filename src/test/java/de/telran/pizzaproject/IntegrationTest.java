package de.telran.pizzaproject;

import de.telran.pizzaproject.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class IntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RestaurantRepository repository;

    @Test
    public void givenRestaurants_whenGetEmployees_thenStatus200()
            throws Exception {

//        createTestRestaurant("Restaurant Name");

//        mvc.perform(get("/restaurants").
//                .andExpect(status().isOk())
//                .andExpect(content()
//                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].name", is("bob")));
//    }

//    private void createTestRestaurant(String name) {
//    }

    }
}
