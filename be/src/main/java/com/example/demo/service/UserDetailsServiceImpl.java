package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // Parameter tetap username, tapi kita asumsikan itu adalah email
        User user = userRepository.findByEmail(email) // Cari berdasarkan email
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email)); // Ubah pesan error

        // Mengembalikan UserDetails dengan email sebagai username untuk Spring Security
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // Gunakan email sebagai username untuk Spring Security
                user.getPassword(),
                new ArrayList<>()
        );
    }
}