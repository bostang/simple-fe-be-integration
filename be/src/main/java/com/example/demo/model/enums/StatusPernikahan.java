package com.example.demo.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

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

    @JsonCreator
    public static StatusPernikahan fromString(String text) {
        for (StatusPernikahan sp : StatusPernikahan.values()) {
            if (sp.displayValue.equalsIgnoreCase(text)) {
                return sp;
            }
        }
        throw new IllegalArgumentException("Status Pernikahan '" + text + "' tidak valid.");
    }
}