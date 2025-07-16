// src/main/java/com/example/demo/config/SecurityConfig.java
package com.example.demo.config;

import com.example.demo.security.jwt.AuthEntryPointJwt; // Import AuthEntryPointJwt
import com.example.demo.security.jwt.AuthTokenFilter; // Import AuthTokenFilter
import com.example.demo.service.UserDetailsServiceImpl; // Import UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

// Kelas ini digunakan untuk mengkonfigurasi keamanan aplikasi Spring Boot
// Ini termasuk pengaturan autentikasi, otorisasi, dan CORS (Cross-Origin Resource Sharing)
// Kelas ini juga mengatur filter JWT untuk autentikasi berbasis token

@Configuration // untuk menandai kelas sebagai konfigurasi Spring
@EnableWebSecurity // untuk mengaktifkan keamanan web Spring
@EnableMethodSecurity // Opsional: untuk mengaktifkan anotasi keamanan pada metode (seperti @PreAuthorize)
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
    // Bean untuk DaoAuthenticationProvider yang digunakan untuk autentikasi berbasis username/password
    // Ini menghubungkan UserDetailsServiceImpl dan PasswordEncoder
    // DaoAuthenticationProvider akan digunakan oleh Spring Security untuk memverifikasi kredensial pengguna
    // Ini juga memungkinkan penggunaan BCryptPasswordEncoder untuk mengenkripsi password
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    // Bean untuk AuthenticationManager yang digunakan untuk mengelola autentikasi
    // Ini mengambil konfigurasi autentikasi dari AuthenticationConfiguration
    // AuthenticationManager ini akan digunakan oleh Spring Security untuk memproses autentikasi
    // Ini memungkinkan penggunaan autentikasi berbasis token JWT
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    // Bean untuk PasswordEncoder yang digunakan untuk mengenkripsi password
    // BCryptPasswordEncoder adalah implementasi yang aman untuk mengenkripsi password
    // Ini akan digunakan oleh DaoAuthenticationProvider untuk memverifikasi password pengguna
    // Ini juga memungkinkan penggunaan password yang terenkripsi dalam database
    // PasswordEncoder ini akan digunakan untuk mengamankan password pengguna
    // sehingga tidak disimpan dalam bentuk teks biasa
    // Ini juga memungkinkan penggunaan password yang terenkripsi dalam database
    // sehingga password pengguna aman dari akses yang tidak sah
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    // Bean untuk SecurityFilterChain yang mengkonfigurasi keamanan aplikasi
    // Ini mengatur aturan keamanan untuk endpoint, filter JWT, dan CORS
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Nonaktifkan CSRF karena kita menggunakan JWT
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

    // Suntikkan nilai dari properti app.cors.allowed-origins
    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins; // Atau List<String> jika Anda lebih suka

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins)); // Gunakan nilai dari properti
        // Izinkan semua metode HTTP
        // Ini memungkinkan akses dari semua origin yang ditentukan dalam app.cors.allowed-origins
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}