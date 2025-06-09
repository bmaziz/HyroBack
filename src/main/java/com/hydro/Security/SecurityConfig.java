package com.hydro.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .cors() // ici !
        .and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/compagnes/**").permitAll()
                        .requestMatchers("/api/navires/**").permitAll()
                        .requestMatchers("/api/mesures/**").permitAll()
                        .requestMatchers("/api/parametres/**").permitAll()
                        .requestMatchers("/api/profils/**").permitAll()
                        .requestMatchers("/api/laboratoires/**").permitAll()
                        .requestMatchers("/api/permissions/**").permitAll()
                        .requestMatchers("/api/cli/upload").permitAll()
                        .requestMatchers("/api/datacentres/**").permitAll()
                        .requestMatchers("/api/pays/**").permitAll()
                        .requestMatchers("/api/projets/**").permitAll()
                        .requestMatchers("/api/regions/**").permitAll()
                        .requestMatchers("/api/scientifiques/**").permitAll()

                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
  
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder2() {
        return new BCryptPasswordEncoder();
    }
}