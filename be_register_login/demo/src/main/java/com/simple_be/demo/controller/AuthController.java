// src/main/java/com/yourcompany/yourapp/controller/AuthController.java
package com.simple_be.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Import Address
import org.springframework.http.ResponseEntity; // Import Guardian
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; // Untuk validasi input
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.simple_be.demo.model.User;
import com.simple_be.demo.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Endpoint untuk registrasi dengan data identitas lengkap
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        // @Valid akan memicu validasi jika Anda menambahkan anotasi validasi di model User

        // 1. Cek apakah username sudah ada
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username sudah terdaftar!", HttpStatus.BAD_REQUEST);
        }

        // 2. Hash password sebelum disimpan
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 3. Simpan data user beserta alamat dan wali
        // Karena ada CascadeType.ALL di entitas User, ketika user disimpan,
        // objek Address dan Guardian yang terkait juga akan disimpan atau diupdate.
        userRepository.save(user);

        return new ResponseEntity<>("User dan identitas berhasil terdaftar!", HttpStatus.CREATED);
    }

    // Endpoint login tetap sama
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return new ResponseEntity<>("Login berhasil!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Password salah!", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Username tidak ditemukan!", HttpStatus.NOT_FOUND);
        }
    }

    // --- Opsional: Endpoint untuk mendapatkan data profil user (setelah login) ---
    // Anda mungkin perlu Spring Security lebih lanjut untuk mengidentifikasi user yang login
    // Misalnya, menggunakan JWT atau Session. Untuk contoh ini, kita anggap user ID dikirim.
    @GetMapping("/profile/{username}")
    public ResponseEntity<User> getUserProfile(@PathVariable String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Penting: Jangan kirim password kembali ke frontend
            user.setPassword(null); // Set password to null before sending
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}