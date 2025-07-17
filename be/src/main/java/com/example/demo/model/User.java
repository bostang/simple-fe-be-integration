package com.example.demo.model;

import java.time.LocalDate;

import com.example.demo.model.enums.Agama;
import com.example.demo.model.enums.JenisKelamin;
import com.example.demo.model.enums.RentangGaji;
import com.example.demo.model.enums.StatusPernikahan;
import com.example.demo.model.enums.SumberPenghasilan;
import com.example.demo.model.enums.TipeAkun;
import com.example.demo.model.enums.TujuanRekening;

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

    // Field username dihapus
    // @Column(unique = true, nullable = false)
    // private String username;

    @Column(nullable = false)
    private String password;

    private String namaLengkap;

    @Column(unique = true, nullable = false) // NIK harus unik
    private String nik; // Diubah dari NIK menjadi nik

    private String namaIbuKandung;
    private String nomorTelepon;

    @Column(unique = true, nullable = false) // Email harus unik dan digunakan untuk login
    private String email;

    @Enumerated(EnumType.STRING)
    private TipeAkun tipeAkun;

    private String tempatLahir;
    private LocalDate tanggalLahir;

    @Enumerated(EnumType.STRING)
    private JenisKelamin jenisKelamin;

    @Enumerated(EnumType.STRING)
    private Agama agama;

    @Enumerated(EnumType.STRING)
    private StatusPernikahan statusPernikahan;

    private String pekerjaan;

    @Enumerated(EnumType.STRING)
    private SumberPenghasilan sumberPenghasilan;

    @Enumerated(EnumType.STRING)
    private RentangGaji rentangGaji;

    @Enumerated(EnumType.STRING)
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

    // Konstruktor disesuaikan, tidak ada username lagi
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // Hapus getter/setter untuk username
    // public String getUsername() { return username; }
    // public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }

    public String getNik() { return nik; } // Getter/setter untuk nik
    public void setNik(String nik) { this.nik = nik; } // Getter/setter untuk nik

    public String getNamaIbuKandung() { return namaIbuKandung; }
    public void setNamaIbuKandung(String namaIbuKandung) { this.namaIbuKandung = namaIbuKandung; }

    public String getNomorTelepon() { return nomorTelepon; }
    public void setNomorTelepon(String nomorTelepon) { this.nomorTelepon = nomorTelepon; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public TipeAkun getTipeAkun() { return tipeAkun; }
    public void setTipeAkun(TipeAkun tipeAkun) { this.tipeAkun = tipeAkun; }

    public String getTempatLahir() { return tempatLahir; }
    public void setTempatLahir(String tempatLahir) { this.tempatLahir = tempatLahir; }

    public LocalDate getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(LocalDate tanggalLahir) { this.tanggalLahir = tanggalLahir; }

    public JenisKelamin getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(JenisKelamin jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    public Agama getAgama() { return agama; }
    public void setAgama(Agama agama) { this.agama = agama; }

    public StatusPernikahan getStatusPernikahan() { return statusPernikahan; }
    public void setStatusPernikahan(StatusPernikahan statusPernikahan) { this.statusPernikahan = statusPernikahan; }

    public String getPekerjaan() { return pekerjaan; }
    public void setPekerjaan(String pekerjaan) { this.pekerjaan = pekerjaan; }

    public SumberPenghasilan getSumberPenghasilan() { return sumberPenghasilan; }
    public void setSumberPenghasilan(SumberPenghasilan sumberPenghasilan) { this.sumberPenghasilan = sumberPenghasilan; }

    public RentangGaji getRentangGaji() { return rentangGaji; }
    public void setRentangGaji(RentangGaji rentangGaji) { this.rentangGaji = rentangGaji; }

    public TujuanRekening getTujuanPembuatanRekening() { return tujuanPembuatanRekening; }
    public void setTujuanPembuatanRekening(TujuanRekening tujuanPembuatanRekening) { this.tujuanPembuatanRekening = tujuanPembuatanRekening; }

    public Integer getKodeRekening() { return kodeRekening; }
    public void setKodeRekening(Integer kodeRekening) { this.kodeRekening = kodeRekening; }

    public Address getAlamat() { return alamat; }
    public void setAlamat(Address alamat) { this.alamat = alamat; }

    public Guardian getWali() { return wali; }
    public void setWali(Guardian wali) { this.wali = wali; }
}