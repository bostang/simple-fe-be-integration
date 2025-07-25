package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "guardians")
// Kelas ini digunakan untuk menyimpan informasi wali penduduk
// Kelas ini akan berisi informasi wali seperti jenis wali, nama lengkap, pekerjaan, alamat, dan nomor telepon
public class Guardian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jenisWali; // Ayah | Ibu
    private String namaLengkapWali;
    private String pekerjaanWali;
    private String alamatWali;
    private String nomorTeleponWali;

    // Default constructor
    public Guardian() {}

    // Constructor with all fields
    public Guardian(String jenisWali, String namaLengkapWali, String pekerjaanWali, String alamatWali, String nomorTeleponWali) {
        this.jenisWali = jenisWali;
        this.namaLengkapWali = namaLengkapWali;
        this.pekerjaanWali = pekerjaanWali;
        this.alamatWali = alamatWali;
        this.nomorTeleponWali = nomorTeleponWali;
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getJenisWali() { return jenisWali; }
    public void setJenisWali(String jenisWali) { this.jenisWali = jenisWali; }
    public String getNamaLengkapWali() { return namaLengkapWali; }
    public void setNamaLengkapWali(String namaLengkapWali) { this.namaLengkapWali = namaLengkapWali; }
    public String getPekerjaanWali() { return pekerjaanWali; }
    public void setPekerjaanWali(String pekerjaanWali) { this.pekerjaanWali = pekerjaanWali; }
    public String getAlamatWali() { return alamatWali; }
    public void setAlamatWali(String alamatWali) { this.alamatWali = alamatWali; }
    public String getNomorTeleponWali() { return nomorTeleponWali; }
    public void setNomorTeleponWali(String nomorTeleponWali) { this.nomorTeleponWali = nomorTeleponWali; }
}