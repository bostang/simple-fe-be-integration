package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Kelas ini digunakan untuk mengirimkan respons validasi NIK
// Kelas ini akan berisi informasi apakah NIK ada dan apakah nama lengkap sesuai
// Kelas ini akan digunakan untuk mengirimkan respons dari server eksternal yang melakukan verifikasi NIK
public class ValidationResponse {
    private Boolean nikExists;
    private Boolean nikNamaMatches;
}