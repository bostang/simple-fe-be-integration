package com.example.demo.controller;

import java.util.Collections; // Import DTO baru
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PendudukDetailResponse;
import com.example.demo.dto.ValidationRequest;
import com.example.demo.service.VerificationClientService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/client") // Endpoint untuk aplikasi klien
public class VerificationClientController {

    @Autowired
    private VerificationClientService verificationClientService;

    @PostMapping("/check-nik-exists")
    public Mono<ResponseEntity<Map<String, Boolean>>> checkNikExists(@RequestBody ValidationRequest request) {
        return verificationClientService.checkNikExists(request.getNik())
                .map(exists -> ResponseEntity.ok(Collections.singletonMap("nikExistsInExternal", exists)))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("error", false))); // Jika service mengembalikan Mono.just(false) karena error
    }

    @PostMapping("/check-nik-nama-matches")
    public Mono<ResponseEntity<Map<String, Boolean>>> checkNikAndNamaLengkap(@RequestBody ValidationRequest request) {
        return verificationClientService.checkNikAndNamaLengkap(request.getNik(), request.getNamaLengkap())
                .map(matches -> ResponseEntity.ok(Collections.singletonMap("nikNamaMatchesInExternal", matches)))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("error", false)));
    }

    @GetMapping("/get-penduduk-details/{nik}")
    public Mono<ResponseEntity<PendudukDetailResponse>> getPendudukDetails(@PathVariable String nik) {
        return verificationClientService.getPendudukDetails(nik)
                .map(details -> ResponseEntity.ok(details)) // Jika ditemukan, kembalikan objek PendudukDetailResponse
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Jika Mono.empty() (tidak ditemukan)
    }
}