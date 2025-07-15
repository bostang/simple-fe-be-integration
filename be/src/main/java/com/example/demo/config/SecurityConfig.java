// src/main/java/com/example/demo/config/SecurityConfig.java
package com.example.demo.config;

import com.example.demo.security.jwt.AuthEntryPointJwt; // Import AuthEntryPointJwt
import com.example.demo.security.jwt.AuthTokenFilter; // Import AuthTokenFilter
import com.example.demo.service.UserDetailsServiceImpl; // Import UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager; // Import AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // Import DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; // Import AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // Opsional: Untuk @PreAuthorize
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy; // Import SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Import UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Jika Anda ingin menggunakan @PreAuthorize
public class SecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService; // Inject UserDetailsServiceImpl

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler; // Inject AuthEntryPointJwt

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() { // Bean untuk JWT filter
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler)) // Tambahkan JWT AuthEntryPoint
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Set sesi tanpa status
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/auth/**", "/client/**").permitAll() // Izinkan akses publik ke auth dan client
                .anyRequest().authenticated() // Semua request lain memerlukan autentikasi
            )
            .cors(withDefaults()); // Aktifkan CORS

        http.authenticationProvider(authenticationProvider()); // Tambahkan authentication provider

        // Tambahkan JWT filter sebelum filter autentikasi username/password standar
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}