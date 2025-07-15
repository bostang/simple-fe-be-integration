package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Penduduk;

@Repository
public interface PendudukRepository extends JpaRepository<Penduduk, Long> {
    Optional<Penduduk> findByNik(String nik);
    Optional<Penduduk> findByNikAndNamaLengkap(String nik, String namaLengkap);
}