package com.simple_be.demo.config;

// src/main/java/com/yourcompany/yourapp/config/WebConfig.java

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Menandakan kelas ini sebagai konfigurasi Spring
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Mengizinkan CORS untuk semua endpoint dalam aplikasi Anda
                .allowedOrigins("http://localhost:3000", "http://127.0.0.1:3000") // **PENTING: Ganti dengan URL frontend React Anda**
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Metode HTTP yang diizinkan
                .allowedHeaders("*") // Header yang diizinkan
                .allowCredentials(true); // Mengizinkan pengiriman kredensial (seperti cookies, authorization headers)
    }
}