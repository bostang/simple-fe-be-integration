package com.simple_be.demo.model.enums;
/**
 * Enum representing different types of accounts.
 */
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
}