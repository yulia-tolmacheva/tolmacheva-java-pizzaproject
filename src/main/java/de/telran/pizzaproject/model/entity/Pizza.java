package de.telran.pizzaproject.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.digester.ArrayStack;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Size(min = 1, max = 14, message = "{pizza.name.size}")
    @Pattern(regexp = "[A-Za-z0-9]+", message = "{pizza.name.invalid}")
    @Column(name = "name")
    private String name;

    @NotNull(message = "{field.required}")
    @Check(constraints = "size in (12, 20, 28)")
    @Column(name = "size")
    private Integer size;

    @NotNull(message = "{field.required}")
    @Min(message = "{pizza.price.invalid}", value = 0)
    @Digits(integer = 2, fraction = 2, message = "{pizza.price.invalid}")
    @Column(name = "price")
    private BigDecimal price;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "pizzas_ingredients",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Pizza() {
    }

    @Override
    public String toString() {
        return name;
    }
}
