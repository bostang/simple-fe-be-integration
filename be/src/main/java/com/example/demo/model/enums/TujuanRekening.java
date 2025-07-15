package com.example.demo.model.enums;

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
}