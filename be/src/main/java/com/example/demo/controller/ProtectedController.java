// Contoh di sebuah RestController baru atau di Controller yang sudah ada
// Misal, di com.example.demo.controller.ProtectedController.java

package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Untuk otorisasi berbasis peran (opsional)
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// Menandai kelas ini sebagai RestController untuk menangani permintaan HTTP
@RequestMapping("/api")
public class ProtectedController {
    // Endpoint yang dilindungi, hanya dapat diakses oleh pengguna yang telah diautentikasi
    // Endpoint ini dapat diakses oleh pengguna yang telah berhasil login
    // dan memiliki peran yang sesuai (jika menggunakan @PreAuthorize)
    // Misalnya, jika menggunakan JWT, pengguna harus mengirimkan token JWT di header Authorization
    @GetMapping("/protected-resource")
    public ResponseEntity<String> getProtectedResource() {
        return ResponseEntity.ok("Selamat datang! Anda telah berhasil mengakses sumber daya yang dilindungi.");
        // respons ini akan dikirimkan jika pengguna telah berhasil diautentikasi (login)
    }
}