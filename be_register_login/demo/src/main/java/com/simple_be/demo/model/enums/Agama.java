package com.simple_be.demo.model.enums;
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
}