package de.telran.pizzaproject.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pizzas")
@Getter
@Setter
public class Pizza {

    public static final int PIZZA_MIN_SIZE = 12;
    public static final int PIZZA_MAX_SIZE = 28;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "{field.required}")
    @Size(min = 1, max = 14, message = "{pizza.name.invalid}")
    @Column(name = "name")
    private String name;

    @NotNull(message = "{field.required}")
    @Min(message = "{pizza.size.invalid}", value = PIZZA_MIN_SIZE)
    @Max(message = "{pizza.size.invalid}", value = PIZZA_MAX_SIZE)
    private Integer size;

    @NotNull(message = "{field.required}")
    @Min(message = "{pizza.price.invalid}", value = 0)
    @Digits(integer = 2, fraction = 2, message = "{pizza.price.invalid}")
    private BigDecimal price;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "pizzas_ingredients",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

//    private boolean isSpicy = ingredients.str

    public Pizza() {
    }

    @Override
    public String toString() {
        return name;
    }
}
