package de.telran.pizzaproject.security;

import de.telran.pizzaproject.model.RoleName;
import de.telran.pizzaproject.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

//    private final UserDetailsService userDetailsService;

//    @Autowired
//    private AuthEntryPointJwt unauthorizedHandler;

//    @Bean
//    public AuthTokenFilter authenticationJwtTokenFilter() {
//        return new AuthTokenFilter();
//    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(getPasswordEncoder());
//
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
//            throws Exception {
//        return authConfig.getAuthenticationManager();
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
                                "/restaurants/search",
                                "/restaurants",
                                "/pizzas/search",
                                "/pizzas",
                                "/auth/login")
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
