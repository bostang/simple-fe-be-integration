// Contoh di sebuah RestController baru atau di Controller yang sudah ada
// Misal, di com.example.demo.controller.ProtectedController.java

package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Untuk otorisasi berbasis peran (opsional)
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api") // Sesuaikan base path
public class ProtectedController {

    @GetMapping("/protected-resource")
    // @PreAuthorize("hasRole('USER')") // Opsional: Hanya izinkan pengguna dengan peran 'USER'
    public ResponseEntity<String> getProtectedResource() {
        return ResponseEntity.ok("Selamat datang! Anda telah berhasil mengakses sumber daya yang dilindungi.");
    }
}