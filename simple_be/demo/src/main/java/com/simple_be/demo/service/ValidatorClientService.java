package com.simple_be.demo.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.simple_be.demo.dto.NikExistsResponse;
import com.simple_be.demo.dto.NikNamaMatchesResponse;
import com.simple_be.demo.dto.NikValidationRequest;

import reactor.core.publisher.Mono; // Untuk WebClient

@Service
public class ValidatorClientService {

    private final WebClient webClient;

    // Ambil base URL validator dari application.properties
    @Value("${validator.service.url}")
    private String validatorServiceUrl;

    public ValidatorClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    // Metode untuk memvalidasi NIK apakah ada
    public Mono<Boolean> validateNikExists(String nik) {
        NikValidationRequest request = new NikValidationRequest(nik, null); // namaLengkap tidak relevan di sini
        String url = validatorServiceUrl + "/api/validator/validate/nik-exists";

        return webClient.post()
                .uri(url)
                .body(Mono.just(request), NikValidationRequest.class)
                .retrieve()
                .bodyToMono(NikExistsResponse.class)
                .map(NikExistsResponse::isNikExists)
                .defaultIfEmpty(false) // Jika respons kosong atau error, anggap false
                .onErrorResume(e -> {
                    System.err.println("Error validating NIK existence: " + e.getMessage());
                    return Mono.just(false); // Tangani error dan kembalikan false
                });
    }

    // Metode untuk memvalidasi NIK dan nama lengkap
    public Mono<Boolean> validateNikAndNamaLengkap(String nik, String namaLengkap) {
        NikValidationRequest request = new NikValidationRequest(nik, namaLengkap);
        String url = validatorServiceUrl + "/api/validator/validate/nik-nama";

        return webClient.post()
                .uri(url)
                .body(Mono.just(request), NikValidationRequest.class)
                .retrieve()
                .bodyToMono(NikNamaMatchesResponse.class)
                .map(NikNamaMatchesResponse::isNikNamaMatches)
                .defaultIfEmpty(false) // Jika respons kosong atau error, anggap false
                .onErrorResume(e -> {
                    System.err.println("Error validating NIK and Nama Lengkap: " + e.getMessage());
                    return Mono.just(false); // Tangani error dan kembalikan false
                });
    }
}