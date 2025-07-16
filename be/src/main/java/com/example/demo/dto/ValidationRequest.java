// src/main/java/com/client/demo/dto/ValidationRequest.java
package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Kelas ini digunakan untuk mengirimkan permintaan validasi NIK
// Kelas ini akan berisi NIK dan nama lengkap (jika diperlukan)
public class ValidationRequest {
    private String nik;
    private String namaLengkap; // Ini akan null untuk nik-exists, tapi tidak masalah
}