package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Lombok: Menghasilkan getter, setter, toString, equals, hashCode
@NoArgsConstructor // Lombok: Menghasilkan constructor tanpa argumen
@AllArgsConstructor // Lombok: Menghasilkan constructor dengan semua argumen
public class Penduduk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nik;
    private String namaLengkap;
    private String tanggalLahir;
    private String alamat;
}