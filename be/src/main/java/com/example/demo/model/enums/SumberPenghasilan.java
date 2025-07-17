package com.example.demo.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SumberPenghasilan {
    GAJI("Gaji"),
    HASIL_INVESTASI("Hasil Investasi"),
    HASIL_USAHA("Hasil Usaha"),
    WARISAN_HIBAH("Warisan/Hibah");

    private final String displayValue;

    SumberPenghasilan(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @JsonCreator
    public static SumberPenghasilan fromString(String text) {
        for (SumberPenghasilan sp : SumberPenghasilan.values()) {
            if (sp.displayValue.equalsIgnoreCase(text)) {
                return sp;
            }
        }
        throw new IllegalArgumentException("Sumber Penghasilan '" + text + "' tidak valid.");
    }

}