package de.telran.pizzaproject.config;

import de.telran.pizzaproject.model.RoleName;
import de.telran.pizzaproject.repository.UserRepository;
import de.telran.pizzaproject.service.UserService;
import de.telran.pizzaproject.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
//@RequiredArgsConstructor
public class SecurityConfig {

//    @Autowired
//    private UserRepository userService;

//    public SecurityConfig(UserService userService) {
//        this.userService = userService;
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
////        return username -> userService.findUserByUsername(username)
//        return username -> userService.findByUsernameIgnoreCase(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }

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
                                "/restaurants/search",
                                "/restaurants",
                                "/pizzas/search",
                                "/pizzas",
                                "/auth/login", "/auth/signup")
                        .permitAll()
                        .requestMatchers("/ingredients").hasAnyAuthority(RoleName.CREATOR.name(), RoleName.ADMIN.name())
                        .requestMatchers("/admin").hasAuthority(RoleName.ADMIN.name())
                        .anyRequest().hasAnyAuthority(RoleName.ADMIN.name(), RoleName.USER.name(), RoleName.CREATOR.name()))
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
