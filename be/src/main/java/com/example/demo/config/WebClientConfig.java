package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

// Kelas ini digunakan untuk mengkonfigurasi WebClient yang akan digunakan untuk berkomunikasi dengan aplikasi verifikator NIK
// WebClient adalah klien HTTP reaktif yang digunakan untuk membuat permintaan HTTP secara asinkron
// Kelas ini mengatur base URL untuk aplikasi verifikator NIK yang berjalan di port 8082
@Configuration
public class WebClientConfig {

    // Menyuntikkan nilai dari properti 'app.validator.base-url'
    @Value("${app.validator.base-url}")
    private String validatorBaseUrl;

    @Bean
    // Bean untuk WebClient yang akan digunakan untuk berkomunikasi dengan aplikasi verifikator NIK
    // WebClient ini akan mengirimkan permintaan ke endpoint yang telah ditentukan

    public WebClient externalValidatorWebClient(WebClient.Builder webClientBuilder) {

        // --- ADD THIS LOG STATEMENT TO DEBUG ---
        System.out.println("Initializing WebClient with base URL: " + validatorBaseUrl);

        // Base URL untuk aplikasi verifikator NIK yang berjalan di port 8082
        return webClientBuilder
                .baseUrl(validatorBaseUrl)
                .build();
    }
}