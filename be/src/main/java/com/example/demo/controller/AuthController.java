package com.example.demo.controller;

import com.example.demo.dto.LoginRequest; // Import DTO baru
import com.example.demo.dto.RegisterRequest; // Import DTO baru
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "${app.cors.allowed-origins}", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    // Endpoint untuk registrasi dengan data identitas lengkap
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            authService.registerUser(registerRequest); // Menggunakan RegisterRequest
            return new ResponseEntity<>("User dan identitas berhasil terdaftar!", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint login yang dimodifikasi untuk mengembalikan JWT
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) { // Menggunakan LoginRequest
        try {
            // Mengautentikasi user menggunakan Spring Security's AuthenticationManager
            // Perhatikan bahwa UsernamePasswordAuthenticationToken masih menggunakan "username" di parameter pertama,
            // namun di baliknya UserDetailsService akan memuat berdasarkan email.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())); // Menggunakan email

            // Set objek Authentication ke SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String jwt = jwtUtils.generateJwtToken(authentication);

            // Mendapatkan UserDetails untuk info tambahan di respons
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Mengembalikan JWTResponse dengan token dan info user (email)
            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername())); // userDetails.getUsername() akan mengembalikan email karena UserDetailsServiceImpl sudah diubah
        } catch (Exception e) {
            return new ResponseEntity<>("Login gagal: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}