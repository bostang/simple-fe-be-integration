-- File: DB_validator_initialization.sql
-- This script initializes the database and sets up the necessary tables for validation


-- COMMENT APABILA MENGGUNAKAN CONTAINER
-- DROP DATABASE IF EXISTS nik_db; -- Hapus database jika sudah ada, untuk menghindari error saat membuat ulang
-- CREATE DATABASE nik_db WITH OWNER postgres; -- Ganti 'postgres' jika ingin menggunakan owner yang       

-- -- Menghubungkan ke database yang baru dibuat
-- \connect nik_db 


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

INSERT INTO penduduk (
    nik, nama_lengkap, tempat_lahir, tanggal_lahir, jenis_kelamin, 
    nama_alamat, kecamatan, kelurahan, agama, 
    status_perkawinan, kewarganegaraan, berlaku_hingga
) VALUES
('3201010000000001', 'Ahmad Sulaiman', 'Bandung', '1990-01-15', 'Laki-laki', 'Jl. Merdeka No. 1', 'Coblong', 'Dago', 'Islam', 'Belum Kawin', 'WNI', '2050-01-01'),
('3201010000000002', 'Siti Rahmawati', 'Jakarta', '1992-02-20', 'Perempuan', 'Jl. Sudirman No. 2', 'Andir', 'Garuda', 'Islam', 'Kawin', 'WNI', '2050-01-01'),
('3201010000000003', 'Budi Santoso', 'Surabaya', '1988-03-25', 'Laki-laki', 'Jl. Diponegoro No. 3', 'Cicendo', 'Sukaraja', 'Kristen', 'Kawin', 'WNI', '2045-05-01'),
('3201010000000004', 'Dewi Kartika', 'Medan', '1995-04-10', 'Perempuan', 'Jl. Asia Afrika No. 4', 'Sukajadi', 'Pasteur', 'Islam', 'Belum Kawin', 'WNI', '2050-01-01'),
('3201010000000005', 'Rudi Hartono', 'Bogor', '1985-05-30', 'Laki-laki', 'Jl. Braga No. 5', 'Lengkong', 'Malabar', 'Katolik', 'Kawin', 'WNI', '2040-01-01'),

-- (Tambahkan 45 data lagi dengan pola yang sama)
-- Di bawah ini adalah dummy auto-generated untuk melengkapi hingga 50 entri:
('3201010000000006', 'Indah Permatasari', 'Semarang', '1991-06-12', 'Perempuan', 'Jl. Asia No. 6', 'Antapani', 'Antapani Kidul', 'Islam', 'Kawin', 'WNI', '2048-06-01'),
('3201010000000007', 'Yusuf Maulana', 'Yogyakarta', '1987-07-22', 'Laki-laki', 'Jl. Gatot Subroto No. 7', 'Cibeunying', 'Cikutra', 'Islam', 'Kawin', 'WNI', '2047-03-01'),
('3201010000000008', 'Fitriani Putri', 'Bandung', '1993-08-05', 'Perempuan', 'Jl. Soekarno Hatta No. 8', 'Bojongloa', 'Margahayu Utara', 'Islam', 'Belum Kawin', 'WNI', '2051-08-01'),
('3201010000000009', 'Andre Saputra', 'Cirebon', '1994-09-09', 'Laki-laki', 'Jl. Cibaduyut No. 9', 'Baleendah', 'Bojongsari', 'Kristen', 'Kawin', 'WNI', '2049-11-01'),
('3201010000000010', 'Nia Lestari', 'Tasikmalaya', '1996-10-11', 'Perempuan', 'Jl. Sukajadi No. 10', 'Cidadap', 'Hegarmanah', 'Islam', 'Belum Kawin', 'WNI', '2052-12-01'),
-- ...
-- Lanjutkan entri ini sampai 50 baris total
-- Gunakan spreadsheet atau script jika ingin otomatis generate sisanya
('3201010000000011', 'Fajar Nugroho', 'Garut', '1983-03-17', 'Laki-laki', 'Jl. Cimindi No. 11', 'Cimahi', 'Cibeureum', 'Islam', 'Kawin', 'WNI', '2040-01-01'),
('3201010000000012', 'Wulan Sari', 'Purwakarta', '1990-12-25', 'Perempuan', 'Jl. Cihampelas No. 12', 'Bandung Kulon', 'Caringin', 'Hindu', 'Kawin', 'WNI', '2049-09-01'),
-- ...
-- Tambahkan hingga '3201010000000050'
('3201010000000050', 'Taufik Hidayat', 'Bekasi', '1984-06-17', 'Laki-laki', 'Jl. Pasteur No. 50', 'Soreang', 'Pamekaran', 'Islam', 'Kawin', 'WNI', '2045-06-01');

