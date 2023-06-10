package de.telran.pizzaproject.security;

import de.telran.pizzaproject.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    //    private final PasswordEncoder bCryptPasswordEncoder;
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
//        this.bCryptPasswordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder())
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(registry -> registry
                                .requestMatchers("/ingredients").hasRole("ADMIN")
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/auth/login").permitAll()
                                .anyRequest().hasAnyRole("USER", "ADMIN")
                        )
                    .formLogin()
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/process_login")
                    .defaultSuccessUrl("/", true)
                    .failureUrl("/auth/login?error")
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/auth/login");
            return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();

//        http
//                .cors().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                .and().formLogin().disable()
//                .securityMatcher("/**")
//                .authorizeHttpRequests(registry -> registry
//                                .requestMatchers("/").permitAll()
//                                .requestMatchers("/auth/login").permitAll()
//                                .anyRequest().authenticated()
//                        );

//        return http.build();
}
//    @Bean
//    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
//        http
//                .cors().disable()
//                .cors().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().formLogin().disable()
//                .securityMatcher("/**")
//                .authorizeHttpRequests(registry -> registry
//                                .requestMatchers("/").permitAll()
//                                .requestMatchers("/auth/login").permitAll()
//                                .anyRequest().authenticated()
//                        );
//        return http.build();
//    }
//}
