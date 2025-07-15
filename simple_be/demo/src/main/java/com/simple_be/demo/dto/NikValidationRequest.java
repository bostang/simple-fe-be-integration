package com.simple_be.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NikValidationRequest {
    private String nik;
    private String namaLengkap; // Opsional, tergantung validasi yang ingin digunakan
}
