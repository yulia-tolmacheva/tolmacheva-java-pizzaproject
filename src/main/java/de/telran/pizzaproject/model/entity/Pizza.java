package de.telran.pizzaproject.model.entity;

import jakarta.persistence.*;
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

    private String name;
    private int size;
    private BigDecimal price;
    private String keyIngredients;
    private Long restaurantId;

    public Pizza(Long id) {
        this.id = id;
    }

    public Pizza() {
    }
}
