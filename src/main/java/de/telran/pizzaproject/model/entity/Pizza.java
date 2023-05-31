package de.telran.pizzaproject.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
//    @UniqueElements
    private String name;

    @NotNull(message = "{field.required}")
    @Min(message = "{pizza.size.invalid}", value = 12)
    @Max(message = "{pizza.size.invalid}", value = 28)
    private Integer size;

    @NotNull(message = "{field.required}")
    @Min(message = "{pizza.price.invalid}", value = 0)
    @Digits(integer = 2, fraction = 2, message = "{pizza.price.invalid}")
    private BigDecimal price;

    @NotBlank(message = "{field.required}")
//    @Size(min = 5, max = 40, message = "{pizza.ingredients.invalid}")
//    @ManyToMany(mappedBy = "")
//    private Set<Ingredient> ingredients;
    private String ingredients;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Pizza(Long id) {
        this.id = id;
    }

    public Pizza() {
    }
}
