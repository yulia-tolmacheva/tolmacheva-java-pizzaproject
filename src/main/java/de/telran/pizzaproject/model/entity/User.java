package de.telran.pizzaproject.model.entity;

import de.telran.pizzaproject.model.RoleName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "{field.required}")
    @Size(min = 1, max = 20, message = "{user.name.size}")
    @Pattern(regexp = "[A-Za-z0-9]+", message = "{user.name.invalid}")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

//    @NotBlank(message = "{field.required}")
//    @Size(min = 3, max = 100, message = "{user.password.size}")
    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    @NotNull()
    private String firstName;

    @Column(name = "last_name")
    @NotNull()
    private String lastName;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "roles")
    private List<RoleName> roles = new ArrayList<>();

    @OneToOne(mappedBy = "owner")
    private Restaurant restaurant;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (RoleName role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    @Override
    public String toString() {
        return "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName;
    }
}
