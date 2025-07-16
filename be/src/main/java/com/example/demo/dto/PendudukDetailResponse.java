package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Kelas ini digunakan untuk mengirimkan respons detail penduduk
// setelah melakukan verifikasi NIK atau mendapatkan data dari server eksternal
// Kelas ini akan berisi informasi detail penduduk seperti NIK, nama lengkap, tempat lahir, tanggal lahir, jenis kelamin, alamat, agama, status perkawinan, pekerjaan, kewarganegaraan, dan berlaku hingga
public class PendudukDetailResponse {
    private String nik;
    private String namaLengkap;
    private String tempatLahir;
    private String tanggalLahir; // Sesuaikan jika ini tipe Date/LocalDate di server
    private String jenisKelamin;
    private String namaAlamat; 
    private String kecamatan;
    private String kelurahan; 
    private String agama;
    private String statusPerkawinan;
    private String pekerjaan; 
    private String kewarganegaraan;
    private String berlakuHingga; // Sesuaikan jika ini tipe Date/LocalDate di server
    // Jika ada ID di model Penduduk server, Anda mungkin perlu menambahkannya juga:
    // private Long id;
}