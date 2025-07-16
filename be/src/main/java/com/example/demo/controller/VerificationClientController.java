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
// Menandai kelas ini sebagai RestController untuk menangani permintaan HTTP
@RequestMapping("/client") // Endpoint untuk aplikasi klien
public class VerificationClientController {

    // injeksi VerificationClientService
    @Autowired
    private VerificationClientService verificationClientService; 
    // Service ini akan berkomunikasi dengan aplikasi verifikator NIK
    // yang berjalan di port 8082
    // Service ini akan mengirimkan permintaan HTTP ke aplikasi verifikator NIK
    // dan menerima respons dari aplikasi tersebut

    // Endpoint untuk memeriksa apakah NIK ada di database eksternal
    // Endpoint ini akan menerima NIK dari permintaan POST
    // dan mengirimkan permintaan ke aplikasi verifikator NIK
    // Jika NIK ada di database eksternal, akan mengembalikan respons dengan status 200 OK
    // Jika NIK tidak ada di database eksternal, akan mengembalikan respons dengan status 404 Not Found
    // Jika terjadi kesalahan dalam komunikasi dengan aplikasi eksternal, akan mengembalikan respons dengan status 500 Internal Server Error
    @PostMapping("/check-nik-exists")
    public Mono<ResponseEntity<Map<String, Boolean>>> checkNikExists(@RequestBody ValidationRequest request) {
        return verificationClientService.checkNikExists(request.getNik())
                .map(exists -> ResponseEntity.ok(Collections.singletonMap("nikExistsInExternal", exists)))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("error", false))); // Jika service mengembalikan Mono.just(false) karena error
    }

    // Endpoint untuk memeriksa apakah NIK dan nama lengkap cocok di database eksternal
    // Endpoint ini akan menerima NIK dan nama lengkap dari permintaan POST
    // dan mengirimkan permintaan ke aplikasi verifikator NIK
    // Jika NIK dan nama lengkap cocok di database eksternal, akan mengembalikan respons dengan status 200 OK
    // Jika NIK dan nama lengkap tidak cocok di database eksternal, akan mengembalikan respons dengan status 404 Not Found
    // Jika terjadi kesalahan dalam komunikasi dengan aplikasi eksternal, akan mengembalikan respons dengan status 500 Internal Server Error
    @PostMapping("/check-nik-nama-matches")
    public Mono<ResponseEntity<Map<String, Boolean>>> checkNikAndNamaLengkap(@RequestBody ValidationRequest request) {
        return verificationClientService.checkNikAndNamaLengkap(request.getNik(), request.getNamaLengkap())
                .map(matches -> ResponseEntity.ok(Collections.singletonMap("nikNamaMatchesInExternal", matches)))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("error", false)));
    }

    // Endpoint untuk mendapatkan detail penduduk berdasarkan NIK
    // Endpoint ini akan menerima NIK dari permintaan GET
    // dan mengirimkan permintaan ke aplikasi verifikator NIK
    // Jika NIK ditemukan, akan mengembalikan objek PendudukDetailResponse dengan status 200 OK
    // Jika NIK tidak ditemukan, akan mengembalikan respons dengan status 404 Not Found
    @GetMapping("/get-penduduk-details/{nik}")
    public Mono<ResponseEntity<PendudukDetailResponse>> getPendudukDetails(@PathVariable String nik) {
        return verificationClientService.getPendudukDetails(nik)
                .map(details -> ResponseEntity.ok(details)) // Jika ditemukan, kembalikan objek PendudukDetailResponse
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Jika Mono.empty() (tidak ditemukan)
    }
}