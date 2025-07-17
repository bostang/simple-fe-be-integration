// STRICT CORS RULE
package com.validator.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // Suntikkan nilai dari properti app.cors.allowed-origins
    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins; // Atau List<String> jika Anda lebih suka

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Mengizinkan CORS untuk semua endpoint
                .allowedOrigins(allowedOrigins) // Asal yang diizinkan
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Metode HTTP yang diizinkan
                .allowedHeaders("*") // Header yang diizinkan
                .allowCredentials(true) // Mengizinkan kredensial (seperti cookie, header otorisasi)
                .maxAge(3600); // Durasi caching untuk preflight request (dalam detik)
    }
}


// // CORS RULE YANG LONGGAR -> ALLOW FROM ALL
// package com.validator.demo.config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import org.springframework.beans.factory.annotation.Value;

// @Configuration
// public class CorsConfig implements WebMvcConfigurer {

//     // Suntikkan nilai dari properti app.cors.allowed-origins
//     @Value("${app.cors.allowed-origins}")
//     private String[] allowedOrigins; // Atau List<String> jika Anda lebih suka

//     @Override
//     public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping("/**") // Mengizinkan CORS untuk semua endpoint
//                 .allowedOrigins(allowedOrigins) // Asal yang diizinkan
//                 .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Metode HTTP yang diizinkan
//                 .allowedHeaders("*") // Header yang diizinkan
//                 .allowCredentials(true) // Mengizinkan kredensial (seperti cookie, header otorisasi)
//                 .maxAge(3600); // Durasi caching untuk preflight request (dalam detik)
//     }
// }