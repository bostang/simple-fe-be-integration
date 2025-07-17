package com.example.demo.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum representing different salary ranges.
 */
public enum RentangGaji {
    KURANG_DARI_3_JUTA("Kurang dari Rp3 juta"),
    ANTARA_3_5_JUTA(">Rp3 - 5 juta"),
    ANTARA_5_10_JUTA(">Rp5 - 10 juta"),
    ANTARA_10_20_JUTA(">Rp10 - 20 juta"),
    ANTARA_20_50_JUTA(">Rp20 - 50 juta"),
    ANTARA_50_100_JUTA(">Rp50 - 100 juta"),
    LEBIH_DARI_100_JUTA(">Rp100 juta");

    private final String displayValue;

    RentangGaji(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @JsonCreator
    public static RentangGaji fromString(String text) {
        for (RentangGaji rg : RentangGaji.values()) {
            if (rg.displayValue.equalsIgnoreCase(text)) {
                return rg;
            }
        }
        throw new IllegalArgumentException("Rentang Gaji '" + text + "' tidak valid.");
    }
}