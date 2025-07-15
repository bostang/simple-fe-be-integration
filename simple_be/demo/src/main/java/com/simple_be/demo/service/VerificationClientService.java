package com.example.demo.service;


import org.springframework.beans.factory.annotation.Qualifier; // Import DTO baru
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.demo.dto.PendudukDetailResponse;
import com.example.demo.dto.ValidationRequest;
import com.example.demo.dto.ValidationResponse;

import reactor.core.publisher.Mono;

@Service
public class VerificationClientService {

    private final WebClient webClient;

    public VerificationClientService(@Qualifier("externalValidatorWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Memanggil endpoint untuk memvalidasi keberadaan NIK.
     * @param nik NIK yang akan divalidasi
     * @return Mono<Boolean> true jika NIK ada, false jika tidak. Mengembalikan Mono.just(false) jika terjadi error.
     */
    public Mono<Boolean> checkNikExists(String nik) {
        ValidationRequest request = new ValidationRequest(nik, null); // namaLengkap tidak relevan di sini

        return webClient.post()
                .uri("/validate/nik-exists")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ValidationResponse.class) // Mapping ke ValidationResponse
                .map(ValidationResponse::getNikExists)
                .onErrorResume(WebClientResponseException.class, error -> {
                    System.err.println("Error calling NIK exists API: " + error.getStatusCode() + " - " + error.getResponseBodyAsString());
                    return Mono.just(false); // Default false jika ada error HTTP
                })
                .onErrorResume(Exception.class, e -> {
                    System.err.println("Unexpected error during NIK exists API call: " + e.getMessage());
                    return Mono.just(false); // Default false jika ada error lain
                });
    }

    /**
     * Memanggil endpoint untuk memvalidasi NIK dan nama lengkap.
     * @param nik NIK yang akan divalidasi
     * @param namaLengkap Nama lengkap yang akan divalidasi
     * @return Mono<Boolean> true jika NIK dan nama lengkap cocok, false jika tidak. Mengembalikan Mono.just(false) jika terjadi error.
     */
    public Mono<Boolean> checkNikAndNamaLengkap(String nik, String namaLengkap) {
        ValidationRequest request = new ValidationRequest(nik, namaLengkap);

        return webClient.post()
                .uri("/validate/nik-nama")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ValidationResponse.class) // Mapping ke ValidationResponse
                .map(ValidationResponse::getNikNamaMatches)
                .onErrorResume(WebClientResponseException.class, error -> {
                    System.err.println("Error calling NIK and NamaLengkap API: " + error.getStatusCode() + " - " + error.getResponseBodyAsString());
                    return Mono.just(false);
                })
                .onErrorResume(Exception.class, e -> {
                    System.err.println("Unexpected error during NIK and NamaLengkap API call: " + e.getMessage());
                    return Mono.just(false);
                });
    }

    /**
     * Memanggil endpoint untuk mendapatkan detail penduduk.
     * @param nik NIK penduduk
     * @return Mono<PendudukDetailResponse> objek PendudukDetailResponse jika ditemukan. Mengembalikan Mono.empty() jika tidak ditemukan atau terjadi error.
     */
    public Mono<PendudukDetailResponse> getPendudukDetails(String nik) {
        return webClient.get()
                .uri("/get-details/{nik}", nik)
                .retrieve()
                // Menangani 404 Not Found secara spesifik: mengembalikan Mono.empty()
                .onStatus(HttpStatus.NOT_FOUND::equals, response -> {
                    System.out.println("Penduduk with NIK " + nik + " not found (HTTP 404).");
                    return Mono.empty(); // Mengembalikan Mono.empty() untuk kasus 404
                })
                // Menangani status error lainnya yang mungkin terjadi selain 404
                .onStatus(status -> status.isError(), response -> {
                    // Log error dan kembalikan Mono.error untuk diproses di onErrorResume berikutnya
                    return response.bodyToMono(String.class)
                                   .flatMap(errorBody -> {
                                       System.err.println("Error calling get details API: " + response.statusCode() + " - " + errorBody);
                                       return Mono.error(new RuntimeException("API call failed with status " + response.statusCode()));
                                   });
                })
                .bodyToMono(PendudukDetailResponse.class) // Mapping ke PendudukDetailResponse
                .onErrorResume(Exception.class, e -> {
                    System.err.println("Unexpected error during get details API call: " + e.getMessage());
                    return Mono.empty(); // Mengembalikan Mono.empty() jika ada error lain (termasuk dari onStatus sebelumnya)
                });
    }
}
