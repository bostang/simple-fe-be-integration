package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.model.enums.*; // Import semua enum
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegisterRequest registerRequest) {
        // 1. Cek apakah email sudah ada
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email sudah terdaftar!");
        }

        // 2. Cek apakah NIK sudah ada
        if (userRepository.findByNik(registerRequest.getNik()).isPresent()) {
            throw new IllegalArgumentException("NIK sudah terdaftar!");
        }

        // 3. Buat objek User baru dan salin data dari RegisterRequest
        User newUser = new User();
        newUser.setNamaLengkap(registerRequest.getNamaLengkap());
        newUser.setNik(registerRequest.getNik()); // Gunakan nik
        newUser.setNamaIbuKandung(registerRequest.getNamaIbuKandung());
        newUser.setNomorTelepon(registerRequest.getNomorTelepon());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // Konversi string ke enum menggunakan method fromString yang akan kita tambahkan di setiap enum
        newUser.setTipeAkun(TipeAkun.fromString(registerRequest.getTipeAkun()));
        newUser.setTempatLahir(registerRequest.getTempatLahir());
        newUser.setTanggalLahir(registerRequest.getTanggalLahir());
        newUser.setJenisKelamin(JenisKelamin.fromString(registerRequest.getJenisKelamin()));
        newUser.setAgama(Agama.fromString(registerRequest.getAgama()));
        newUser.setStatusPernikahan(StatusPernikahan.fromString(registerRequest.getStatusPernikahan()));
        newUser.setPekerjaan(registerRequest.getPekerjaan());
        newUser.setSumberPenghasilan(SumberPenghasilan.fromString(registerRequest.getSumberPenghasilan()));
        newUser.setRentangGaji(RentangGaji.fromString(registerRequest.getRentangGaji()));
        newUser.setTujuanPembuatanRekening(TujuanRekening.fromString(registerRequest.getTujuanPembuatanRekening()));
        newUser.setKodeRekening(registerRequest.getKodeRekening());

        // Set alamat dan wali
        newUser.setAlamat(registerRequest.getAlamat());
        newUser.setWali(registerRequest.getWali());

        // 4. Simpan data user beserta alamat dan wali
        return userRepository.save(newUser);
    }

    @Override
    public User loginUser(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail()); // Cari berdasarkan email

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return user; // Login berhasil
            } else {
                throw new IllegalArgumentException("Password salah!");
            }
        } else {
            throw new IllegalArgumentException("Email tidak ditemukan!"); // Ubah pesan error
        }
    }

    @Override
    public User getUserProfile(String email) { // Ubah parameter ke email
        Optional<User> userOptional = userRepository.findByEmail(email); // Cari berdasarkan email
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(null); // Penting: Jangan kirim password kembali ke frontend
            return user;
        } else {
            return null; // Atau throw NoSuchElementException
        }
    }
}