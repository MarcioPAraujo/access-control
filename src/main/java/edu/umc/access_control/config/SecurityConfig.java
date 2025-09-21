package edu.umc.access_control.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authz -> authz
            // Allow access to static resources and the registration page
            .requestMatchers("/css/**", "/js/**", "/login", "/register").permitAll()
            .requestMatchers("/admin/**").hasRole("MANAGER")
            .requestMatchers("/employees/**").hasAnyRole("EMPLOYEE", "MANAGER", "LEADER")
            .requestMatchers("/leader/**").hasAnyRole("LEADER", "MANAGER")
            // All other requests must be authenticated
            .anyRequest().authenticated())
        // Configure form-based login
        .formLogin(form -> form
            .loginPage("/login") // Specify a custom login page URL
            .permitAll() // Everyone can access the login page
            .defaultSuccessUrl("/home", true) // Redirect to /home on successful login
        )
        // Configure logout
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout") // Redirect to login page with a logout message
            .permitAll());
    return http.build();
  }
}
