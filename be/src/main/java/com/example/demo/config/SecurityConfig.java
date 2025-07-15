package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// Import untuk CORS
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays; // Import ini

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(withDefaults()) // Ini akan menggunakan CorsConfigurationSource yang kita definisikan
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/client/**").permitAll()
                // Pastikan /api/auth/** juga diizinkan, sesuaikan jika endpoint Anda hanya /auth/**
                // Jika URL Anda di frontend adalah `http://localhost:8083/api/auth/register`
                // maka di sini harus `requestMatchers("/api/auth/**").permitAll()`
                // Jika URL Anda di frontend adalah `http://localhost:8083/auth/register`
                // maka yang sekarang sudah benar `requestMatchers("/auth/**").permitAll()`
                .requestMatchers("/auth/**", "/api/auth/**").permitAll() // Tambahkan /api/auth/** untuk jaga-jaga
                .anyRequest().authenticated()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // --- TAMBAHKAN BEAN INI ---
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // **PENTING: Ganti "http://localhost:3000" dengan URL FRONTEND React Anda**
        // Jika frontend Anda berjalan di port lain, sesuaikan di sini.
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Atau "http://127.0.0.1:3000"
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*")); // Mengizinkan semua header
        configuration.setAllowCredentials(true); // Mengizinkan pengiriman kredensial (seperti cookies atau header otorisasi)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Terapkan konfigurasi CORS ini ke semua path ("/**")
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    // -------------------------
}