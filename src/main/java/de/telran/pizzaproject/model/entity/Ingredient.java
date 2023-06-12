package de.telran.pizzaproject.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "{field.required}")
    @Size(min = 1, max = 20, message = "{ingredients.name.size}")
    @Pattern(regexp = "[A-Za-z0-9 ]+", message = "{ingredients.name.invalid}")
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "is_vegetarian")
    private boolean isVegetarian;

    @Column(name = "is_spicy")
    private boolean isSpicy;

    @Column(name = "is_glutenfree")
    private boolean isGlutenfree;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
