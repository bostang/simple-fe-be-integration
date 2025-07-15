-- File: DB_validator_initialization.sql
-- This script initializes the database and sets up the necessary tables for validation

DROP DATABASE IF EXISTS nik_db; -- Hapus database jika sudah ada, untuk menghindari error saat membuat ulang
CREATE DATABASE nik_db WITH OWNER postgres; -- Ganti 'postgres' jika ingin menggunakan owner yang       

-- Menghubungkan ke database yang baru dibuat
\connect nik_db 


DROP TABLE IF EXISTS penduduk CASCADE;
CREATE TABLE penduduk (
    id BIGSERIAL PRIMARY KEY, -- BIGSERIAL otomatis akan menjadi auto-incrementing big integer
    NIK VARCHAR(255) NOT NULL,
    nama_lengkap VARCHAR(255) NOT NULL,
    tempat_lahir VARCHAR(255) NOT NULL,
    tanggal_lahir DATE NOT NULL,
    jenis_kelamin VARCHAR(255) NOT NULL,
    nama_alamat VARCHAR(255) NOT NULL,
    kecamatan VARCHAR(255) NOT NULL,
    kelurahan VARCHAR(255) NOT NULL,
    agama VARCHAR(255) NOT NULL,
    status_perkawinan VARCHAR(255) NOT NULL,
    kewarganegaraan VARCHAR(255) NOT NULL,
    berlaku_hingga DATE NOT NULL
);

ALTER TABLE penduduk ADD CONSTRAINT unique_nik UNIQUE (NIK);

CREATE INDEX idx_nik ON penduduk (NIK);