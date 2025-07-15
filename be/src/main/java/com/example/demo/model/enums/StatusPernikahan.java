package com.example.demo.model.enums;
/**
 * Enum representing different marital statuses.
 */
public enum StatusPernikahan {
    SINGLE("Single"),
    MENIKAH("Menikah"),
    DUDA("Duda"),
    JANDA("Janda");

    private final String displayValue;

    StatusPernikahan(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}