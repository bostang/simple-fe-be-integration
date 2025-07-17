package com.example.demo.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum JenisKelamin {
    LAKI_LAKI("Laki-laki"),
    PEREMPUAN("Perempuan");

    private final String displayValue;

    JenisKelamin(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @JsonCreator
    public static JenisKelamin fromString(String text) {
        for (JenisKelamin jk : JenisKelamin.values()) {
            if (jk.displayValue.equalsIgnoreCase(text)) {
                return jk;
            }
        }
        throw new IllegalArgumentException("Jenis Kelamin '" + text + "' tidak valid.");
    }
}