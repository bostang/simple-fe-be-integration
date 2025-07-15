package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PendudukDetailResponse {
    private String nik;
    private String namaLengkap;
    private String tempatLahir;
    private String tanggalLahir; // Sesuaikan jika ini tipe Date/LocalDate di server
    private String jenisKelamin;
    private String namaAlamat; // <--- UBAH DARI 'alamat'
    private String kecamatan;
    private String kelurahan; // <--- UBAH DARI 'kelDesa'
    private String agama;
    private String statusPerkawinan;
    private String pekerjaan; // <-- Tambahkan jika ada di server, di contoh Anda null
    private String kewarganegaraan;
    private String berlakuHingga; // Sesuaikan jika ini tipe Date/LocalDate di server
    // Jika ada ID di model Penduduk server, Anda mungkin perlu menambahkannya juga:
    // private Long id;
}