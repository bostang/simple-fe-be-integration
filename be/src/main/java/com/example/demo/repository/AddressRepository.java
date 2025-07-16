package com.example.demo.repository;

// src/main/java/com/yourcompany/yourapp/repository/AddressRepository.java

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Address;

@Repository
// Kelas ini digunakan untuk mengelola operasi database terkait alamat
// Kelas ini akan menyediakan metode untuk menyimpan, mengambil, memperbarui, dan menghapus alamat penduduk
public interface AddressRepository extends JpaRepository<Address, Long> {
}