package com.simple_be.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain; // Import ini untuk CSRF

@Configuration
@EnableWebSecurity // Mengaktifkan konfigurasi keamanan web Spring
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean ini akan mengkonfigurasi aturan keamanan HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Menonaktifkan CSRF untuk API stateless (penting untuk Postman/Frontend)
            .authorizeHttpRequests(authorize -> authorize
                // Izinkan endpoint /api/auth/** untuk diakses tanpa otentikasi
                .requestMatchers("/api/auth/**").permitAll()
                // Semua request lain memerlukan otentikasi
                .anyRequest().authenticated()
            )
            // .formLogin() // Jika Anda ingin menggunakan form login default Spring Security
            // .and()
            // .httpBasic(); // Jika Anda ingin menggunakan basic authentication

            // Jika Anda tidak menggunakan form login atau http basic, pastikan tidak ada konflik
            // Untuk stateless API (seperti REST API dengan JWT), Anda tidak akan menggunakan ini.
            ;
        return http.build();
    }
}