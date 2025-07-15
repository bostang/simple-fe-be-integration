package com.simple_be.demo.controller;


import com.simple_be.demo.service.ValidatorClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/first-app")
public class SomeControllerInFirstBackend {

    @Autowired
    private ValidatorClientService validatorClientService;

    @GetMapping("/check-nik-validity/{nik}/{namaLengkap}")
    public ResponseEntity<String> checkNikValidity(@PathVariable String nik, @PathVariable String namaLengkap) {
        // Panggil service validator untuk mengecek keberadaan NIK
        Boolean nikExists = validatorClientService.validateNikExists(nik).block(); // .block() digunakan untuk menunggu hasil Mono secara sinkron (hanya untuk contoh)

        if (!nikExists) {
            return ResponseEntity.badRequest().body("NIK tidak ditemukan.");
        }

        // Panggil service validator untuk mengecek NIK dan nama lengkap
        Boolean nikNamaMatches = validatorClientService.validateNikAndNamaLengkap(nik, namaLengkap).block();

        if (nikNamaMatches) {
            return ResponseEntity.ok("NIK dan nama lengkap cocok. Validasi berhasil.");
        } else {
            return ResponseEntity.badRequest().body("NIK ditemukan, tetapi nama lengkap tidak cocok.");
        }
    }

    // Contoh endpoint lain yang mungkin menggunakan validator
    // Misalnya, saat pendaftaran user, Anda bisa memvalidasi NIK di sini
    // @PostMapping("/register-user")
    // public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest request) {
    //     Boolean nikNamaMatches = validatorClientService.validateNikAndNamaLengkap(request.getNik(), request.getNamaLengkap()).block();
    //     if (!nikNamaMatches) {
    //         return ResponseEntity.badRequest().body("Validasi NIK/Nama gagal.");
    //     }
    //     // Lanjutkan proses pendaftaran jika validasi berhasil
    //     return ResponseEntity.ok("Registrasi berhasil dan NIK valid.");
    // }
}