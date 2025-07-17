package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Ubah method ini untuk mencari berdasarkan email
    Optional<User> findByEmail(String email); // Mengubah findByUsername menjadi findByEmail

    // Anda mungkin juga ingin menambahkan ini jika NIK harus unik dan Anda ingin memeriksa ketersediaan NIK
    Optional<User> findByNik(String nik);
}