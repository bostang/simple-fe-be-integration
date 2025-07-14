// src/main/java/com/yourcompany/yourapp/model/User.java
package com.simple_be.demo.model;

import java.time.LocalDate;

import com.simple_be.demo.model.enums.Agama;
import com.simple_be.demo.model.enums.JenisKelamin;
import com.simple_be.demo.model.enums.RentangGaji;
import com.simple_be.demo.model.enums.StatusPernikahan;
import com.simple_be.demo.model.enums.SumberPenghasilan;
import com.simple_be.demo.model.enums.TipeAkun;
import com.simple_be.demo.model.enums.TujuanRekening;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String namaLengkap;
    private String NIK;
    private String namaIbuKandung;
    private String nomorTelepon;
    private String email;

    @Enumerated(EnumType.STRING) // Penting! Simpan sebagai String di DB
    private TipeAkun tipeAkun;

    private String tempatLahir;
    private LocalDate tanggalLahir;

    @Enumerated(EnumType.STRING) // Penting!
    private JenisKelamin jenisKelamin;

    @Enumerated(EnumType.STRING) // Penting!
    private Agama agama;

    @Enumerated(EnumType.STRING) // Penting!
    private StatusPernikahan statusPernikahan;

    private String pekerjaan;

    @Enumerated(EnumType.STRING) // Penting!
    private SumberPenghasilan sumberPenghasilan;

    @Enumerated(EnumType.STRING) // Penting!
    private RentangGaji rentangGaji;

    @Enumerated(EnumType.STRING) // Penting!
    private TujuanRekening tujuanPembuatanRekening;

    private Integer kodeRekening;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address alamat;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "guardian_id", referencedColumnName = "id")
    private Guardian wali;

    // Konstruktor, Getters, dan Setters

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // --- Getters and Setters (pastikan semua ada dan update tipe data) ---
    // Contoh untuk tipeAkun:
    public TipeAkun getTipeAkun() { return tipeAkun; }
    public void setTipeAkun(TipeAkun tipeAkun) { this.tipeAkun = tipeAkun; }

    // Contoh untuk jenisKelamin:
    public JenisKelamin getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(JenisKelamin jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    // Lanjutkan untuk semua field enum lainnya
    public Agama getAgama() { return agama; }
    public void setAgama(Agama agama) { this.agama = agama; }

    public StatusPernikahan getStatusPernikahan() { return statusPernikahan; }
    public void setStatusPernikahan(StatusPernikahan statusPernikahan) { this.statusPernikahan = statusPernikahan; }

    public SumberPenghasilan getSumberPenghasilan() { return sumberPenghasilan; }
    public void setSumberPenghasilan(SumberPenghasilan sumberPenghasilan) { this.sumberPenghasilan = sumberPenghasilan; }

    public RentangGaji getRentangGaji() { return rentangGaji; }
    public void setRentangGaji(RentangGaji rentangGaji) { this.rentangGaji = rentangGaji; }

    public TujuanRekening getTujuanPembuatanRekening() { return tujuanPembuatanRekening; }
    public void setTujuanPembuatanRekening(TujuanRekening tujuanPembuatanRekening) { this.tujuanPembuatanRekening = tujuanPembuatanRekening; }

    // Pastikan getters dan setters untuk field String, LocalDate, Integer, Address, Guardian lainnya tetap ada
    // ... (sisa getters dan setters seperti sebelumnya) ...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }
    public String getNIK() { return NIK; }
    public void setNIK(String NIK) { this.NIK = NIK; }
    public String getNamaIbuKandung() { return namaIbuKandung; }
    public void setNamaIbuKandung(String namaIbuKandung) { this.namaIbuKandung = namaIbuKandung; }
    public String getNomorTelepon() { return nomorTelepon; }
    public void setNomorTelepon(String nomorTelepon) { this.nomorTelepon = nomorTelepon; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTempatLahir() { return tempatLahir; }
    public void setTempatLahir(String tempatLahir) { this.tempatLahir = tempatLahir; }
    public LocalDate getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(LocalDate tanggalLahir) { this.tanggalLahir = tanggalLahir; }
    public String getPekerjaan() { return pekerjaan; }
    public void setPekerjaan(String pekerjaan) { this.pekerjaan = pekerjaan; }
    public Integer getKodeRekening() { return kodeRekening; }
    public void setKodeRekening(Integer kodeRekening) { this.kodeRekening = kodeRekening; }
    public Address getAlamat() { return alamat; }
    public void setAlamat(Address alamat) { this.alamat = alamat; }
    public Guardian getWali() { return wali; }
    public void setWali(Guardian wali) { this.wali = wali; }
}