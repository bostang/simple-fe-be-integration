package com.example.demo.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TujuanRekening {
    INVESTASI("Investasi"),
    TABUNGAN("Tabungan"),
    TRANSAKSI("Transaksi");

    private final String displayValue;

    TujuanRekening(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @JsonCreator
    public static TujuanRekening fromString(String text) {
        for (TujuanRekening tr : TujuanRekening.values()) {
            if (tr.displayValue.equalsIgnoreCase(text)) {
                return tr;
            }
        }
        throw new IllegalArgumentException("Tujuan Pembuatan Rekening '" + text + "' tidak valid.");
    }
}