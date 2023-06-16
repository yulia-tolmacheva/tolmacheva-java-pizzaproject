package de.telran.pizzaproject.model.entity;

import de.telran.pizzaproject.model.PizzaSize;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pizzas")
@Getter
@Setter
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "{field.required}")
    @Size(min = 1, max = 30, message = "{pizza.name.size}")
    @Pattern(regexp = "[A-Za-z0-9 ]+", message = "{pizza.name.invalid}")
    @Column(name = "name")
    private String name;

    @NotNull(message = "{field.required}")
    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    private PizzaSize size;

    @NotNull(message = "{field.required}")
    @Min(message = "{pizza.price.invalid}", value = 0)
    @Digits(integer = 2, fraction = 2, message = "{pizza.price.invalid}")
    @Column(name = "price")
    private BigDecimal price;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "pizzas_ingredients",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    public Pizza() {
    }

    @Override
    public String toString() {
        return name;
    }
}
