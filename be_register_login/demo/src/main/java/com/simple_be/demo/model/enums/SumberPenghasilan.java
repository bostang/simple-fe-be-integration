package com.simple_be.demo.model.enums;

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
}