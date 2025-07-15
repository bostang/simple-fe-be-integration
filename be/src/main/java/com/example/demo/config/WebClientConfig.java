package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient externalValidatorWebClient(WebClient.Builder webClientBuilder) {
        // Base URL untuk aplikasi verifikator NIK yang berjalan di port 8082
        return webClientBuilder
                .baseUrl("http://localhost:8082/api/validator")
                .build();
    }
}