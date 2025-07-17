package com.example.demo.model.enums;
/**
 * Enum representing different types of accounts.
 */
import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipeAkun {
    BNI_TAPLUS("BNI Taplus"),
    BNI_TAPLUS_MUDA("BNI Taplus Muda");

    private final String displayValue;

    TipeAkun(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @JsonCreator
    public static TipeAkun fromString(String text) {
        for (TipeAkun b : TipeAkun.values()) {
            if (b.displayValue.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Tipe Akun '" + text + "' tidak valid.");
    }
}