package com.example.demo.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum representing different religions.
 */
public enum Agama {
    ISLAM("Islam"),
    KRISTEN("Kristen"),
    BUDDHA("Buddha"),
    HINDU("Hindu"),
    KONGHUCU("Konghucu"),
    LAINNYA("Lainnya");

    private final String displayValue;

    Agama(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @JsonCreator
    public static Agama fromString(String text) {
        for (Agama a : Agama.values()) {
            if (a.displayValue.equalsIgnoreCase(text)) {
                return a;
            }
        }
        throw new IllegalArgumentException("Agama '" + text + "' tidak valid.");
    }
}