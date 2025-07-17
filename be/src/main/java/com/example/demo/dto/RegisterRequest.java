package com.example.demo.dto;

import com.example.demo.model.Address;
import com.example.demo.model.Guardian;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public class RegisterRequest {

    @NotBlank(message = "Nama lengkap tidak boleh kosong")
    private String namaLengkap;

    @NotBlank(message = "NIK tidak boleh kosong")
    @Pattern(regexp = "\\d{16}", message = "NIK harus 16 digit angka")
    private String nik; // Diubah dari NIK menjadi nik

    @NotBlank(message = "Nama ibu kandung tidak boleh kosong")
    private String namaIbuKandung;

    @NotBlank(message = "Nomor telepon tidak boleh kosong")
    @Pattern(regexp = "^(\\+62|0)\\d{9,13}$", message = "Format nomor telepon tidak valid")
    private String nomorTelepon;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank(message = "Password tidak boleh kosong")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+-=]).{8,}$",
            message = "Password harus mengandung minimal 8 karakter, satu huruf besar, satu huruf kecil, satu angka, dan satu simbol")
    private String password;

    @NotBlank(message = "Tipe akun tidak boleh kosong")
    private String tipeAkun; // Akan diparsing ke Enum di Service

    @NotBlank(message = "Tempat lahir tidak boleh kosong")
    private String tempatLahir;

    @NotNull(message = "Tanggal lahir tidak boleh kosong")
    private LocalDate tanggalLahir;

    @NotBlank(message = "Jenis kelamin tidak boleh kosong")
    private String jenisKelamin; // Akan diparsing ke Enum di Service

    @NotBlank(message = "Agama tidak boleh kosong")
    private String agama; // Akan diparsing ke Enum di Service

    @NotBlank(message = "Status pernikahan tidak boleh kosong")
    private String statusPernikahan; // Akan diparsing ke Enum di Service

    @NotBlank(message = "Pekerjaan tidak boleh kosong")
    private String pekerjaan;

    @NotBlank(message = "Sumber penghasilan tidak boleh kosong")
    private String sumberPenghasilan; // Akan diparsing ke Enum di Service

    @NotBlank(message = "Rentang gaji tidak boleh kosong")
    private String rentangGaji; // Akan diparsing ke Enum di Service

    @NotBlank(message = "Tujuan pembuatan rekening tidak boleh kosong")
    private String tujuanPembuatanRekening; // Akan diparsing ke Enum di Service

    @NotNull(message = "Kode rekening tidak boleh kosong")
    private Integer kodeRekening;

    @Valid
    @NotNull(message = "Informasi alamat tidak boleh kosong")
    private Address alamat; // Menggunakan model Address secara langsung

    @Valid
    private Guardian wali; // Menggunakan model Guardian secara langsung, bisa null

    // --- Constructors, Getters, and Setters ---
    public RegisterRequest() {}

    // Getters and Setters for all fields
    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }

    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; } // Getter/setter untuk nik

    public String getNamaIbuKandung() { return namaIbuKandung; }
    public void setNamaIbuKandung(String namaIbuKandung) { this.namaIbuKandung = namaIbuKandung; }

    public String getNomorTelepon() { return nomorTelepon; }
    public void setNomorTelepon(String nomorTelepon) { this.nomorTelepon = nomorTelepon; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTipeAkun() { return tipeAkun; }
    public void setTipeAkun(String tipeAkun) { this.tipeAkun = tipeAkun; }

    public String getTempatLahir() { return tempatLahir; }
    public void setTempatLahir(String tempatLahir) { this.tempatLahir = tempatLahir; }

    public LocalDate getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(LocalDate tanggalLahir) { this.tanggalLahir = tanggalLahir; }

    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    public String getAgama() { return agama; }
    public void setAgama(String agama) { this.agama = agama; }

    public String getStatusPernikahan() { return statusPernikahan; }
    public void setStatusPernikahan(String statusPernikahan) { this.statusPernikahan = statusPernikahan; }

    public String getPekerjaan() { return pekerjaan; }
    public void setPekerjaan(String pekerjaan) { this.pekerjaan = pekerjaan; }

    public String getSumberPenghasilan() { return sumberPenghasilan; }
    public void setSumberPenghasilan(String sumberPenghasilan) { this.sumberPenghasilan = sumberPenghasilan; }

    public String getRentangGaji() { return rentangGaji; }
    public void setRentangGaji(String rentangGaji) { this.rentangGaji = rentangGaji; }

    public String getTujuanPembuatanRekening() { return tujuanPembuatanRekening; }
    public void setTujuanPembuatanRekening(String tujuanPembuatanRekening) { this.tujuanPembuatanRekening = tujuanPembuatanRekening; }

    public Integer getKodeRekening() { return kodeRekening; }
    public void setKodeRekening(Integer kodeRekening) { this.kodeRekening = kodeRekening; }

    public Address getAlamat() { return alamat; }
    public void setAlamat(Address alamat) { this.alamat = alamat; }

    public Guardian getWali() { return wali; }
    public void setWali(Guardian wali) { this.wali = wali; }
}