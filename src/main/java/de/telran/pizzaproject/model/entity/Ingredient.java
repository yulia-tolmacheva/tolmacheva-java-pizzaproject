package de.telran.pizzaproject.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{field.required}")
    @Size(min = 1, max = 15, message = "{ingredients.name.invalid}")
    private String name;


    private boolean isVegetarian = false;
    private boolean isSpicy = false;
    private boolean isGlutenfree = false;

//    @ManyToMany(mappedBy = "")
//    private Set<Pizza> ingredients;


    @Override
    public String toString() {
        return name;
    }
}
