-- File: DB_initialization.sql
-- This script initializes the database and sets up the necessary tables

-- jalankan dengan :
    -- psql -U postgres -f DB_initialization.sql

-- Membuat Database
-- Pastikan terkoneksi ke database default seperti 'postgres' saat menjalankan perintah ini.
-- Ganti 'user_db' jika  ingin nama database yang berbeda.

DROP DATABASE IF EXISTS user_db; -- Hapus database jika sudah ada, untuk menghindari error saat membuat ulang
CREATE DATABASE user_db WITH OWNER postgres; -- Ganti 'postgres' jika ingin menggunakan owner yang berbeda
-- Menghubungkan ke database yang baru dibuat (jika menggunakan psql atau client lain)
\c user_db;
-- Jika  menjalankan ini dari tools seperti DBeaver atau pgAdmin,
--  biasanya akan memilih database 'user_db' terlebih dahulu.

-- Membuat Tabel Users
-- Jalankan perintah ini setelah  terhubung ke database 'user_db'.

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY, -- BIGSERIAL otomatis akan menjadi auto-incrementing big integer
    username VARCHAR(255) NOT NULL UNIQUE, -- Username tidak boleh kosong dan harus unik
    password VARCHAR(255) NOT NULL -- Password tidak boleh kosong
);

-- Opsional: Menambahkan indeks pada kolom username untuk performa pencarian yang lebih cepat
CREATE INDEX idx_username ON users (username);

---

-- Contoh Data (Opsional: Hanya untuk pengujian)
-- Catatan: Password di sini masih dalam plain text.
-- Di aplikasi nyata,  HARUS menggunakan BCryptPasswordEncoder di Spring Boot
-- untuk menghash password sebelum menyimpannya ke database.
-- Contoh password 'password123' yang sudah di-hash oleh BCrypt akan terlihat seperti '$2a$10$xyz...'
-- Jadi, data di bawah ini hanya untuk ilustrasi struktur tabel.

-- INSERT INTO users (username, password) VALUES
-- ('testuser', 'password123'); -- Ini adalah password plain text, HINDARI di produksi!

-- INSERT INTO users (username, password) VALUES
-- ('admin', '$2a$10$yA9Zp.D.L.cZ.j.n0V0c.O/U.c.p.Z.c.t.f.R.g.H.K.L.M.N.O.P.Q.R.S.T.U.V.W.X.Y.Z.');
-- Contoh password 'secret123' yang di-hash menggunakan BCrypt