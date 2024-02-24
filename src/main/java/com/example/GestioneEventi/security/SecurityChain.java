package com.example.GestioneEventi.security;

import com.example.GestioneEventi.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityChain {
    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(AbstractHttpConfigurer::disable);

        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/api/auth/**").permitAll());
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/api/users/params").permitAll());
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/api/users/{id}/partecipation").permitAll());
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/api/users").hasAuthority("ADMIN"));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/api/users/{id}/upgrade").hasAuthority("ADMIN"));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/api/events").hasAnyAuthority("ADMIN","ORGANIZER"));

        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/**").denyAll());

        return httpSecurity.build();
    }
}
