// src/main/java/com/example/demo/controller/AuthController.java
package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import com.example.demo.security.jwt.JwtUtils; // Import JwtUtils
import com.example.demo.payload.response.JwtResponse; // Kita akan buat kelas ini
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager; // Import AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Import UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication; // Import Authentication
import org.springframework.security.core.context.SecurityContextHolder; // Import SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails; // Import UserDetails
import org.springframework.web.bind.annotation.*;

// Kelas ini digunakan untuk mengelola autentikasi pengguna
// Ini termasuk registrasi pengguna, login, dan mendapatkan profil pengguna
@RestController
// Menandai kelas ini sebagai RestController untuk menangani permintaan HTTP
@RequestMapping("/api/auth") // Mengatur path dasar untuk endpoint autentikasi
@CrossOrigin(origins = "${app.cors.allowed-origins}", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AuthController {

    @Autowired
    private AuthService authService; // Gunakan AuthService
    // dari AuthService.java

    @Autowired
    AuthenticationManager authenticationManager; // Inject AuthenticationManager
    // dari SecurityConfig.java

    @Autowired
    JwtUtils jwtUtils; // Inject JwtUtils
    // dari JwtUtils.java

    // Endpoint untuk registrasi dengan data identitas lengkap
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        try {
            authService.registerUser(user);
            return new ResponseEntity<>("User dan identitas berhasil terdaftar!", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint login yang dimodifikasi untuk mengembalikan JWT
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User loginRequest) {
        try {
            // Mengautentikasi user menggunakan Spring Security's AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // Set objek Authentication ke SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String jwt = jwtUtils.generateJwtToken(authentication);

            // Mendapatkan UserDetails untuk info tambahan di respons
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Mengembalikan JWTResponse dengan token dan info user
            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername())); // Anda bisa menambahkan ID, roles dll.
        } catch (Exception e) { // Tangkap Exception lebih umum untuk melihat detail error
            // Perbaiki penanganan error agar lebih spesifik
            return new ResponseEntity<>("Login gagal: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}