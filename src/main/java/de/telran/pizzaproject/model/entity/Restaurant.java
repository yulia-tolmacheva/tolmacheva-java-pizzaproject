package de.telran.pizzaproject.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{field.required}")
    @Size(min = 1, max = 14, message = "{restaurant.name.invalid}")
    private String name;

    @NotBlank(message = "{field.required}")
    @Size(min = 1, max = 30, message = "{restaurant.address.invalid}")
    private String address;

    @NotBlank(message = "{field.required}")
    @Pattern(regexp = "\\d{6,12}", message = "{restaurant.phone.invalid}")
    private String phone;

    @OneToMany(mappedBy="restaurant", cascade=CascadeType.ALL)
    private Set<Pizza> pizzas = new HashSet<>();
}
