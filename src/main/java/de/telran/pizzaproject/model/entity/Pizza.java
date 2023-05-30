package de.telran.pizzaproject.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "pizzas")
@Getter
@Setter
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{field.required}")
    @Size(min = 1, max = 14, message = "{pizza.name.invalid}")
    private String name;

    @NotNull(message = "{field.required}")
    @Min(message = "{pizza.size.invalid}", value = 12)
    @Max(message = "{pizza.size.invalid}", value = 28)
    private Integer size;

    @NotNull(message = "{field.required}")
    @Min(message = "{pizza.price.invalid}", value = 0)
    @Max(message = "{pizza.price.invalid}", value = 100)
    private BigDecimal price;

    @NotBlank(message = "{field.required}")
    @Size(min = 5, max = 40, message = "{pizza.ingredients.invalid}")
    private String keyIngredients;

    private Long restaurantId;

    public Pizza(Long id) {
        this.id = id;
    }

    public Pizza() {
    }
}
