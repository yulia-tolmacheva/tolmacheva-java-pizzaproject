package de.telran.pizzaproject.validator;

import de.telran.pizzaproject.model.entity.Ingredient;
import de.telran.pizzaproject.model.entity.User;
import de.telran.pizzaproject.service.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientValidatorTest {

    @Mock
    private IngredientService ingredientService;

    private IngredientValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new IngredientValidator(ingredientService);
    }

    @Test
    void testSupports_ValidClass_ReturnsTrue() {
        Class<?> clazz = Ingredient.class;

        boolean result = validator.supports(clazz);

        assertTrue(result);
    }

    @Test
    void testSupports_InvalidClass_ReturnsFalse() {
        Class<?> clazz = User.class;

        boolean result = validator.supports(clazz);

        assertFalse(result);
    }

    @Test
    void testValidate_IngredientWithNameAlreadyExists_ErrorsContainsErrorMessage() {

        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Cheese");

        Ingredient ingredientToAdd = new Ingredient();
        ingredient.setId(2L);
        ingredient.setName("Cheese");

        Optional<Ingredient> foundIngredient = Optional.of(ingredientToAdd);

        when(ingredientService.getIngredientByName("Cheese")).thenReturn(foundIngredient);
        Errors errors = new BeanPropertyBindingResult(ingredient, "ingredient");

        validator.validate(ingredient, errors);

        assertTrue(errors.hasErrors());
        assertEquals("This ingredient already exists", errors.getFieldError("name").getDefaultMessage());
        verify(ingredientService, times(1)).getIngredientByName("Cheese");
    }

    @Test
    void testValidate_IngredientWithNameDoesNotExist_ErrorsIsEmpty() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Cheese");

        Optional<Ingredient> foundIngredient = Optional.empty();

        when(ingredientService.getIngredientByName("Salami")).thenReturn(foundIngredient);
        Errors errors = new BeanPropertyBindingResult(ingredient, "ingredient");

        validator.validate(ingredient, errors);

        assertFalse(errors.hasErrors());
        verify(ingredientService, times(1)).getIngredientByName(any());
    }

}