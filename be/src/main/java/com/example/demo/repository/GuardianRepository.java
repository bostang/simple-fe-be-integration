package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Guardian;

@Repository
// Kelas ini digunakan untuk mengelola operasi database terkait wali penduduk
// Kelas ini akan menyediakan metode untuk menyimpan, mengambil, memperbarui, dan menghapus informasi wali penduduk
public interface GuardianRepository extends JpaRepository<Guardian, Long> {
}