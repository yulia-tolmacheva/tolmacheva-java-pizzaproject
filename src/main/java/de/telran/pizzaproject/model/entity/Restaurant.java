package de.telran.pizzaproject.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "{field.required}")
    @Size(min = 1, max = 30, message = "{restaurant.name.invalid}")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "{field.required}")
    @Size(min = 1, max = 30, message = "{restaurant.address.invalid}")
    @Column(name = "address")
    private String address;

    @NotBlank(message = "{field.required}")
    @Size(min = 1, max = 30, message = "{restaurant.address.invalid}")
    @Column(name = "city")
    private String city;

    @NotBlank(message = "{field.required}")
    @Pattern(regexp = ".\\d{6,12}", message = "{restaurant.phone.invalid}")
    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy="restaurant", cascade=CascadeType.ALL)
    private List<Pizza> pizzas = new ArrayList<>();
}
