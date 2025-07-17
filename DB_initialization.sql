-- File: DB_initialization.sql
-- This script initializes the database and sets up the necessary tables

-- Run with:
    -- psql -U postgres -f DB_initialization.sql

-- Create Database
-- Ensure you are connected to a default database like 'postgres' when running this command.
-- Change 'user_db' if you want a different database name.

DROP DATABASE IF EXISTS user_db; -- Drop the database if it exists, to avoid errors when recreating
CREATE DATABASE user_db WITH OWNER postgres; -- Change 'postgres' if you want to use a different owner
-- Connect to the newly created database (if using psql or another client)
\c user_db;
-- If you are running this from tools like DBeaver or pgAdmin,
-- you will usually select the 'user_db' database first.

-- Create Users Table
-- Run this command after you have connected to the 'user_db' database.

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY, -- BIGSERIAL will automatically be an auto-incrementing big integer
    -- username VARCHAR(255) NOT NULL UNIQUE, -- Username column removed as per new requirements
    password VARCHAR(255) NOT NULL, -- Password cannot be null

    -- New columns as per the updated structure
    nama_lengkap VARCHAR(255) NOT NULL,
    nik VARCHAR(255) NOT NULL UNIQUE, -- NIK cannot be null and must be unique
    nama_ibu_kandung VARCHAR(255) NOT NULL,
    nomor_telepon VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE, -- Email cannot be null and must be unique, used for login
    tipe_akun VARCHAR(255) NOT NULL,
    tempat_lahir VARCHAR(255) NOT NULL,
    tanggal_lahir DATE NOT NULL,
    jenis_kelamin VARCHAR(255) NOT NULL,
    agama VARCHAR(255) NOT NULL,
    status_pernikahan VARCHAR(255) NOT NULL,
    pekerjaan VARCHAR(255) NOT NULL,
    sumber_penghasilan VARCHAR(255) NOT NULL,
    rentang_gaji VARCHAR(255) NOT NULL,
    tujuan_pembuatan_rekening VARCHAR(255) NOT NULL,
    kode_rekening INTEGER NOT NULL
);

-- Optional: Add indexes on email and nik columns for faster search performance
CREATE INDEX idx_email ON users (email);
CREATE INDEX idx_nik ON users (nik);

---

-- Create Addresses Table
DROP TABLE IF EXISTS addresses CASCADE;
CREATE TABLE addresses (
    id BIGSERIAL PRIMARY KEY,
    nama_alamat VARCHAR(255) NOT NULL,
    provinsi VARCHAR(255) NOT NULL,
    kota VARCHAR(255) NOT NULL,
    kecamatan VARCHAR(255) NOT NULL,
    kelurahan VARCHAR(255) NOT NULL,
    kode_pos VARCHAR(255) NOT NULL
);

-- Create Guardians Table
DROP TABLE IF EXISTS guardians CASCADE;
CREATE TABLE guardians (
    id BIGSERIAL PRIMARY KEY,
    jenis_wali VARCHAR(255),
    nama_lengkap_wali VARCHAR(255),
    pekerjaan_wali VARCHAR(255),
    alamat_wali VARCHAR(255),
    nomor_telepon_wali VARCHAR(255)
);

-- Update Users Table to add foreign keys
ALTER TABLE users
ADD COLUMN address_id BIGINT UNIQUE,
ADD COLUMN guardian_id BIGINT UNIQUE;

ALTER TABLE users
ADD CONSTRAINT fk_address
FOREIGN KEY (address_id)
REFERENCES addresses (id)
ON DELETE SET NULL; -- Or ON DELETE CASCADE if you want the address to be deleted along with the user

ALTER TABLE users
ADD CONSTRAINT fk_guardian
FOREIGN KEY (guardian_id)
REFERENCES guardians (id)
ON DELETE SET NULL; -- Or ON DELETE CASCADE if you want the guardian to be deleted along with the user

-- Example Data (Optional: For testing only)
-- Note: Passwords here are still in plain text.
-- In a real application, you MUST use BCryptPasswordEncoder in Spring Boot
-- to hash passwords before storing them in the database.
-- An example 'password123' hashed by BCrypt would look like '$2a$10$xyz...'
-- So, the data below is only for illustrating the table structure.

-- INSERT INTO addresses (nama_alamat, provinsi, kota, kecamatan, kelurahan, kode_pos) VALUES
-- ('Jl. Contoh No. 123, RT 001/RW 002', 'DKI Jakarta', 'Jakarta Pusat', 'Tanah Abang', 'Bendungan Hilir', '10210');

-- INSERT INTO guardians (jenis_wali, nama_lengkap_wali, pekerjaan_wali, alamat_wali, nomor_telepon_wali) VALUES
-- ('Ayah', 'Budi Santoso', 'Pensiunan', 'Jl. Contoh Wali No. 456', '081211223344');

-- INSERT INTO users (
--     email, password, nama_lengkap, nik, nama_ibu_kandung, nomor_telepon,
--     tipe_akun, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, status_pernikahan,
--     pekerjaan, sumber_penghasilan, rentang_gaji, tujuan_pembuatan_rekening, kode_rekening,
--     address_id, guardian_id
-- ) VALUES (
--     'john.doe@example.com', '$2a$10$yA9Zp.D.L.cZ.j.n0V0c.O/U.c.p.Z.c.t.f.R.g.H.K.L.M.N.O.P.Q.R.S.T.U.V.W.X.Y.Z.', -- Hashed password for 'password123!'
--     'John Doe', '3175031234567890', 'Mary Doe', '081234567890',
--     'BNI Taplus', 'Jakarta', '1990-05-15', 'Laki-laki', 'Islam', 'Belum Kawin',
--     'Software Engineer', 'Gaji', '5-10 juta', 'Tabungan', 1001,
--     1, -- Assuming address_id 1 exists from the previous insert
--     1  -- Assuming guardian_id 1 exists from the previous insert
-- );
