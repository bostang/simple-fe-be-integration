package com.simple_be.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin; // Untuk hashing password
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple_be.demo.model.User;
import com.simple_be.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // Pastikan CORS di sini juga jika endpoint spesifik
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Akan dibahas di bawah

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Cek apakah username sudah ada
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username sudah terdaftar!", HttpStatus.BAD_REQUEST);
        }

        // Hash password sebelum disimpan
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return new ResponseEntity<>("User berhasil terdaftar!", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Verifikasi password yang di-hash
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return new ResponseEntity<>("Login berhasil!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Password salah!", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Username tidak ditemukan!", HttpStatus.NOT_FOUND);
        }
    }
}