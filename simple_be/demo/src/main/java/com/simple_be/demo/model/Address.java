package com.simple_be.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String namaAlamat; // Nama Alamat (misal: Alamat Rumah, Alamat Kantor)

    @Column(nullable = false)
    private String provinsi;

    @Column(nullable = false)
    private String kota;

    @Column(nullable = false)
    private String kecamatan;

    @Column(nullable = false)
    private String kelurahan;

    @Column(nullable = false)
    private String kodePos;

    // Default constructor
    public Address() {}

    // Constructor with all fields
    public Address(String namaAlamat, String provinsi, String kota, String kecamatan, String kelurahan, String kodePos) {
        this.namaAlamat = namaAlamat;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.kelurahan = kelurahan;
        this.kodePos = kodePos;
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNamaAlamat() { return namaAlamat; }
    public void setNamaAlamat(String namaAlamat) { this.namaAlamat = namaAlamat; }
    public String getProvinsi() { return provinsi; }
    public void setProvinsi(String provinsi) { this.provinsi = provinsi; }
    public String getKota() { return kota; }
    public void setKota(String kota) { this.kota = kota; }
    public String getKecamatan() { return kecamatan; }
    public void setKecamatan(String kecamatan) { this.kecamatan = kecamatan; }
    public String getKelurahan() { return kelurahan; }
    public void setKelurahan(String kelurahan) { this.kelurahan = kelurahan; }
    public String getKodePos() { return kodePos; }
    public void setKodePos(String kodePos) { this.kodePos = kodePos; }
}