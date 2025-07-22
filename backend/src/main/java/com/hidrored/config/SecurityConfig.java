package com.hidrored.config;

import com.hidrored.exception.SecurityConfigurationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;




@Configuration
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    try{
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/api/**").permitAll()
            .anyRequest().authenticated());
    return http.build();
    } catch (Exception e) {
      // Se captura el error y se lanza uno con un mensaje personalizado
      throw new SecurityConfigurationException("Error al configurar la seguridad", e);
    }
  }
}
