package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
// Kelas ini digunakan untuk mengelola operasi database terkait pengguna
// Kelas ini akan menyediakan metode untuk menyimpan, mengambil, memperbarui, dan menghapus informasi pengguna
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA secara otomatis akan mengimplementasikan metode ini
    Optional<User> findByUsername(String username);
}