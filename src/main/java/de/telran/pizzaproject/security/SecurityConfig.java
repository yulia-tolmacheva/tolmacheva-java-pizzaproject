package de.telran.pizzaproject.security;

import de.telran.pizzaproject.model.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/",
                                "/css/**",
                                "/error",
                                "/search/**",
                                "/restaurants/**",
                                "/pizzas/**",
                                "/auth/login", "/auth/signup")
                        .permitAll()
                        .requestMatchers("/ingredients").hasAnyAuthority(RoleName.OWNER.name(), RoleName.ADMIN.name())
                        .requestMatchers("/admin").hasAuthority(RoleName.ADMIN.name())
                        .anyRequest().hasAnyAuthority(RoleName.ADMIN.name(), RoleName.USER.name(), RoleName.OWNER.name()))
                        .formLogin((formLogin) -> formLogin
                                .loginPage("/auth/login")
                                .loginProcessingUrl("/process_login")
                                .failureUrl("/auth/login?error")
                                .defaultSuccessUrl("/"))
                        .logout((logout) -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/"));
        return http.build();
    }
}
