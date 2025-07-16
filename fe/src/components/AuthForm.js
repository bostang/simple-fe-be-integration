// src/components/AuthForm.js
import React, { useState, useEffect } from 'react';
// import axios from 'axios';
import axiosInstance from '../axiosConfig';
import { useNavigate } from 'react-router-dom';

// Perhatikan AuthForm sekarang menerima 'type' prop dari App.js
function AuthForm({ type }) {
  const navigate = useNavigate(); // Inisialisasi useNavigate hook

  // State untuk data form, sesuai dengan yang Anda berikan
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    namaLengkap: '',
    NIK: '',
    namaIbuKandung: '',
    nomorTelepon: '',
    email: '',
    tipeAkun: '',
    tempatLahir: '',
    tanggalLahir: '',
    jenisKelamin: '',
    agama: '',
    statusPernikahan: '',
    pekerjaan: '',
    sumberPenghasilan: '',
    rentangGaji: '',
    tujuanPembuatanRekening: '',
    kodeRekening: '',
    alamat: {
      namaAlamat: '',
      provinsi: '',
      kota: '',
      kecamatan: '',
      kelurahan: '',
      kodePos: ''
    },
    wali: {
      jenisWali: '',
      namaLengkapWali: '',
      pekerjaanWali: '',
      alamatWali: '',
      nomorTeleponWali: ''
    }
  });

  const [message, setMessage] = useState('');
  const [error, setError] = useState(''); // State terpisah untuk error
  const [nikValidationMessage, setNikValidationMessage] = useState('');

  // Tentukan apakah mode saat ini adalah register atau login berdasarkan 'type' prop
  const isRegisterMode = type === 'register';

  // Base URL untuk API autentikasi Anda
  const API_AUTH_BASE_URL = 'http://backend:8083/api/auth';
  // URL untuk validasi NIK (pastikan ini sesuai dengan backend verifikasi NIK Anda)
  const API_CLIENT_BASE_URL = 'http://backend:8083/client';

  // Opsi Dropdown (tetap sama seperti sebelumnya)
  const tipeAkunOptions = [
    { value: 'BNI_TAPLUS', label: 'BNI Taplus' },
    { value: 'BNI_TAPLUS_MUDA', label: 'BNI Taplus Muda' }
  ];
  const jenisKelaminOptions = [
    { value: 'LAKI_LAKI', label: 'Laki-laki' },
    { value: 'PEREMPUAN', label: 'Perempuan' }
  ];
  const agamaOptions = [
    { value: 'ISLAM', label: 'Islam' },
    { value: 'KRISTEN', label: 'Kristen' },
    { value: 'BUDDHA', label: 'Buddha' },
    { value: 'HINDU', label: 'Hindu' },
    { value: 'KONGHUCU', label: 'Konghucu' },
    { value: 'LAINNYA', label: 'Lainnya' }
  ];
  const statusPernikahanOptions = [
    { value: 'SINGLE', label: 'Single' },
    { value: 'MENIKAH', label: 'Menikah' },
    { value: 'DUDA', label: 'Duda' },
    { value: 'JANDA', label: 'Janda' }
  ];
  const sumberPenghasilanOptions = [
    { value: 'GAJI', label: 'Gaji' },
    { value: 'HASIL_INVESTASI', label: 'Hasil Investasi' },
    { value: 'HASIL_USAHA', label: 'Hasil Usaha' },
    { value: 'WARISAN_HIBAH', label: 'Warisan/Hibah' }
  ];
  const rentangGajiOptions = [
    { value: 'KURANG_DARI_3_JUTA', label: 'Kurang dari Rp3 juta' },
    { value: 'ANTARA_3_5_JUTA', label: '>Rp3 - 5 juta' },
    { value: 'ANTARA_5_10_JUTA', label: '>Rp5 - 10 juta' },
    { value: 'ANTARA_10_20_JUTA', label: '>Rp10 - 20 juta' },
    { value: 'ANTARA_20_50_JUTA', label: '>Rp20 - 50 juta' },
    { value: 'ANTARA_50_100_JUTA', label: '>Rp50 - 100 juta' },
    { value: 'LEBIH_DARI_100_JUTA', label: '>Rp100 juta' }
  ];
  const tujuanRekeningOptions = [
    { value: 'INVESTASI', label: 'Investasi' },
    { value: 'TABUNGAN', label: 'Tabungan' },
    { value: 'TRANSAKSI', label: 'Transaksi' }
  ];
  const jenisWaliOptions = [
    { value: 'AYAH', label: 'Ayah' },
    { value: 'IBU', label: 'Ibu' }
  ];

  // Efek untuk mereset form saat mode berubah (misal dari register ke login)
  useEffect(() => {
    // Reset hanya field yang spesifik untuk register
    setFormData(prevData => ({
      ...prevData,
      namaLengkap: '',
      NIK: '',
      namaIbuKandung: '',
      nomorTelepon: '',
      email: '',
      tipeAkun: '',
      tempatLahir: '',
      tanggalLahir: '',
      jenisKelamin: '',
      agama: '',
      statusPernikahan: '',
      pekerjaan: '',
      sumberPenghasilan: '',
      rentangGaji: '',
      tujuanPembuatanRekening: '',
      kodeRekening: '',
      alamat: {
        namaAlamat: '',
        provinsi: '',
        kota: '',
        kecamatan: '',
        kelurahan: '',
        kodePos: ''
      },
      wali: {
        jenisWali: '',
        namaLengkapWali: '',
        pekerjaanWali: '',
        alamatWali: '',
        nomorTeleponWali: ''
      }
    }));
    setMessage('');
    setError('');
    setNikValidationMessage('');
  }, [type]); // Dependency array: jalankan ulang efek saat 'type' berubah


  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevData => ({
      ...prevData,
      [name]: value
    }));

    // Reset validasi NIK saat NIK atau Nama Lengkap berubah
    if (name === 'NIK' || name === 'namaLengkap') {
      setNikValidationMessage('');
    }
  };

  const handleNestedChange = (e, parentKey) => {
    const { name, value } = e.target;
    setFormData(prevData => ({
      ...prevData,
      [parentKey]: {
        ...prevData[parentKey],
        [name]: value
      }
    }));
  };

  const validateNikAndNama = async (nik, namaLengkap) => {
    if (!nik || !namaLengkap) {
      setNikValidationMessage('NIK dan Nama Lengkap diperlukan untuk validasi.');
      return false;
    }

    try {
      // const response = await axios.post(`${API_CLIENT_BASE_URL}/check-nik-nama-matches`, {
        const response = await axiosInstance.post(`${API_CLIENT_BASE_URL}/check-nik-nama-matches`, {
        nik: nik,
        namaLengkap: namaLengkap
      });

      if (response.data.nikNamaMatchesInExternal) {
        setNikValidationMessage('Validasi NIK dan Nama Lengkap berhasil.');
        return true;
      } else {
        setNikValidationMessage('NIK atau Nama Lengkap tidak cocok dengan data terdaftar.');
        return false;
      }
    } catch (error) {
      console.error('Error saat memvalidasi NIK:', error);
      if (error.response && error.response.data && typeof error.response.data === 'string') {
          setNikValidationMessage(`Terjadi kesalahan validasi NIK: ${error.response.data}`);
      } else {
          setNikValidationMessage('Terjadi kesalahan saat memvalidasi NIK. Coba lagi nanti.');
      }
      return false;
    }
  };


  const handleSubmit = async (event) => {
    event.preventDefault();
    setMessage('');
    setError('');
    setNikValidationMessage('');

    const endpoint = isRegisterMode ? '/register' : '/login';

    let dataToSend = {};

    if (isRegisterMode) {
      const isNikValid = await validateNikAndNama(formData.NIK, formData.namaLengkap);

      if (!isNikValid) {
        return;
      }
      dataToSend = {
        ...formData,
        kodeRekening: formData.kodeRekening ? parseInt(formData.kodeRekening, 10) : null
      };
    } else {
      dataToSend = {
        username: formData.username,
        password: formData.password
      };
    }

    try {
      // const response = await axios.post(`${API_AUTH_BASE_URL}${endpoint}`, dataToSend);
      const response = await axiosInstance.post(`${API_AUTH_BASE_URL}${endpoint}`, dataToSend);

      
      if (!isRegisterMode) { // Jika ini adalah proses login
        const token = response.data.token || response.data.jwt || response.data.accessToken; 
        
        if (token) {
          localStorage.setItem('token', token);
          console.log('Token tersimpan:', token);
          // Set pesan string yang bisa dirender
          setMessage('Login berhasil! Mengarahkan ke Dashboard...'); 
          setTimeout(() => {
            navigate('/dashboard'); 
          }, 500); // Tunda sedikit sebelum redirect
        } else {
          setError("Login berhasil, tetapi token tidak ditemukan di respons server.");
        }
      } else { // Jika ini adalah proses register
          // Asumsi respons register adalah string atau objek dengan pesan
          setMessage(response.data.message || "Registrasi berhasil! Silakan login."); 
          setTimeout(() => {
            navigate('/login');
          }, 500); // Tunda sedikit sebelum redirect
      }

    } catch (err) {
      console.error('Error during authentication:', err);
      if (err.response) {
        setError(err.response.data.message || err.response.data.error || 'Terjadi kesalahan pada server. Coba lagi.');
        console.error('Error response data:', err.response.data);
        console.error('Error response status:', err.response.status);
      } else if (err.request) {
        setError('Tidak ada respons dari server. Pastikan backend berjalan.');
        console.error('Error request:', err.request);
      } else {
        setError(`Terjadi kesalahan: ${err.message}`);
        console.error('Error message:', err.message);
      }
    }
  };

  return (
    <div style={formStyles.container}>
      <h2>{isRegisterMode ? 'Register Akun Baru' : 'Login'}</h2>
      <form onSubmit={handleSubmit}>
        {/* Username & Password selalu ada */}
        <div style={formStyles.inputGroup}>
          <label htmlFor="username" style={formStyles.label}>Username:</label>
          <input type="text" id="username" name="username" value={formData.username} onChange={handleChange} required={true} style={formStyles.input} />
        </div>
        <div style={formStyles.inputGroup}>
          <label htmlFor="password" style={formStyles.label}>Password:</label>
          <input type="password" id="password" name="password" value={formData.password} onChange={handleChange} required={true} style={formStyles.input} />
        </div>

        {/* Field Identitas Diri (Hanya muncul saat Register Mode) */}
        {isRegisterMode && (
          <>
            <h3>Data Diri</h3>
            <div style={formStyles.inputGroup}>
              <label htmlFor="namaLengkap" style={formStyles.label}>Nama Lengkap:</label>
              <input type="text" id="namaLengkap" name="namaLengkap" value={formData.namaLengkap} onChange={handleChange} required={isRegisterMode} style={formStyles.input} />
            </div>
            <div style={formStyles.inputGroup}>
              <label htmlFor="NIK" style={formStyles.label}>NIK:</label>
              <input type="text" id="NIK" name="NIK" value={formData.NIK} onChange={handleChange} required={isRegisterMode} style={formStyles.input} />
            </div>

            {/* Pesan validasi NIK */}
            {nikValidationMessage && (
              <p style={{ ...formStyles.validationMessage, color: nikValidationMessage.includes('berhasil') ? 'green' : 'red' }}>
                {nikValidationMessage}
              </p>
            )}

            <div style={formStyles.inputGroup}>
              <label htmlFor="namaIbuKandung" style={formStyles.label}>Nama Ibu Kandung:</label>
              <input type="text" id="namaIbuKandung" name="namaIbuKandung" value={formData.namaIbuKandung} onChange={handleChange} required={isRegisterMode} style={formStyles.input} />
            </div>
            <div style={formStyles.inputGroup}>
              <label htmlFor="nomorTelepon" style={formStyles.label}>Nomor Telepon:</label>
              <input type="text" id="nomorTelepon" name="nomorTelepon" value={formData.nomorTelepon} onChange={handleChange} required={isRegisterMode} style={formStyles.input} />
            </div>
            <div style={formStyles.inputGroup}>
              <label htmlFor="email" style={formStyles.label}>Email:</label>
              <input type="email" id="email" name="email" value={formData.email} onChange={handleChange} required={isRegisterMode} style={formStyles.input} />
            </div>

            {/* Dropdown for Tipe Akun */}
            <div style={formStyles.inputGroup}>
              <label htmlFor="tipeAkun" style={formStyles.label}>Tipe Akun:</label>
              <select id="tipeAkun" name="tipeAkun" value={formData.tipeAkun} onChange={handleChange} required={isRegisterMode} style={formStyles.input}>
                <option value="">Pilih Tipe Akun</option>
                {tipeAkunOptions.map(option => (
                  <option key={option.value} value={option.value}>{option.label}</option>
                ))}
              </select>
            </div>

            <div style={formStyles.inputGroup}>
              <label htmlFor="tempatLahir" style={formStyles.label}>Tempat Lahir:</label>
              <input type="text" id="tempatLahir" name="tempatLahir" value={formData.tempatLahir} onChange={handleChange} required={isRegisterMode} style={formStyles.input} />
            </div>
            <div style={formStyles.inputGroup}>
              <label htmlFor="tanggalLahir" style={formStyles.label}>Tanggal Lahir (YYYY-MM-DD):</label>
              <input type="date" id="tanggalLahir" name="tanggalLahir" value={formData.tanggalLahir} onChange={handleChange} required={isRegisterMode} style={formStyles.input} />
            </div>

            {/* Dropdown for Jenis Kelamin */}
            <div style={formStyles.inputGroup}>
              <label htmlFor="jenisKelamin" style={formStyles.label}>Jenis Kelamin:</label>
              <select id="jenisKelamin" name="jenisKelamin" value={formData.jenisKelamin} onChange={handleChange} required={isRegisterMode} style={formStyles.input}>
                <option value="">Pilih Jenis Kelamin</option>
                {jenisKelaminOptions.map(option => (
                  <option key={option.value} value={option.value}>{option.label}</option>
                ))}
              </select>
            </div>

            {/* Dropdown for Agama */}
            <div style={formStyles.inputGroup}>
              <label htmlFor="agama" style={formStyles.label}>Agama:</label>
              <select id="agama" name="agama" value={formData.agama} onChange={handleChange} required={isRegisterMode} style={formStyles.input}>
                <option value="">Pilih Agama</option>
                {agamaOptions.map(option => (
                  <option key={option.value} value={option.value}>{option.label}</option>
                ))}
              </select>
            </div>

            {/* Dropdown for Status Pernikahan */}
            <div style={formStyles.inputGroup}>
              <label htmlFor="statusPernikahan" style={formStyles.label}>Status Pernikahan:</label>
              <select id="statusPernikahan" name="statusPernikahan" value={formData.statusPernikahan} onChange={handleChange} required={isRegisterMode} style={formStyles.input}>
                <option value="">Pilih Status Pernikahan</option>
                {statusPernikahanOptions.map(option => (
                  <option key={option.value} value={option.value}>{option.label}</option>
                ))}
              </select>
            </div>

            <div style={formStyles.inputGroup}>
              <label htmlFor="pekerjaan" style={formStyles.label}>Pekerjaan:</label>
              <input type="text" id="pekerjaan" name="pekerjaan" value={formData.pekerjaan} onChange={handleChange} required={isRegisterMode} style={formStyles.input} />
            </div>

            {/* Dropdown for Sumber Penghasilan */}
            <div style={formStyles.inputGroup}>
              <label htmlFor="sumberPenghasilan" style={formStyles.label}>Sumber Penghasilan:</label>
              <select id="sumberPenghasilan" name="sumberPenghasilan" value={formData.sumberPenghasilan} onChange={handleChange} required={isRegisterMode} style={formStyles.input}>
                <option value="">Pilih Sumber Penghasilan</option>
                {sumberPenghasilanOptions.map(option => (
                  <option key={option.value} value={option.value}>{option.label}</option>
                ))}
              </select>
            </div>

            {/* Dropdown for Rentang Gaji */}
            <div style={formStyles.inputGroup}>
              <label htmlFor="rentangGaji" style={formStyles.label}>Rentang Gaji:</label>
              <select id="rentangGaji" name="rentangGaji" value={formData.rentangGaji} onChange={handleChange} required={isRegisterMode} style={formStyles.input}>
                <option value="">Pilih Rentang Gaji</option>
                {rentangGajiOptions.map(option => (
                  <option key={option.value} value={option.value}>{option.label}</option>
                ))}
              </select>
            </div>

            {/* Dropdown for Tujuan Pembuatan Rekening */}
            <div style={formStyles.inputGroup}>
              <label htmlFor="tujuanPembuatanRekening" style={formStyles.label}>Tujuan Pembuatan Rekening:</label>
              <select id="tujuanPembuatanRekening" name="tujuanPembuatanRekening" value={formData.tujuanPembuatanRekening} onChange={handleChange} required={isRegisterMode} style={formStyles.input}>
                <option value="">Pilih Tujuan Rekening</option>
                {tujuanRekeningOptions.map(option => (
                  <option key={option.value} value={option.value}>{option.label}</option>
                ))}
              </select>
            </div>

            <div style={formStyles.inputGroup}>
              <label htmlFor="kodeRekening" style={formStyles.label}>Kode Rekening:</label>
              <input type="number" id="kodeRekening" name="kodeRekening" value={formData.kodeRekening} onChange={handleChange} required={isRegisterMode} style={formStyles.input} />
            </div>

            {/* Alamat */}
            <h3>Alamat</h3>
            <div style={formStyles.inputGroup}>
              <label htmlFor="namaAlamat" style={formStyles.label}>Nama Alamat:</label>
              <input type="text" id="namaAlamat" name="namaAlamat" value={formData.alamat.namaAlamat} onChange={(e) => handleNestedChange(e, 'alamat')} required={isRegisterMode} style={formStyles.input} />
            </div>
            <div style={formStyles.inputGroup}>
              <label htmlFor="provinsi" style={formStyles.label}>Provinsi:</label>
              <input type="text" id="provinsi" name="provinsi" value={formData.alamat.provinsi} onChange={(e) => handleNestedChange(e, 'alamat')} required={isRegisterMode} style={formStyles.input} />
            </div>
            <div style={formStyles.inputGroup}>
              <label htmlFor="kota" style={formStyles.label}>Kota:</label>
              <input type="text" id="kota" name="kota" value={formData.alamat.kota} onChange={(e) => handleNestedChange(e, 'alamat')} required={isRegisterMode} style={formStyles.input} />
            </div>
            <div style={formStyles.inputGroup}>
              <label htmlFor="kecamatan" style={formStyles.label}>Kecamatan:</label>
              <input type="text" id="kecamatan" name="kecamatan" value={formData.alamat.kecamatan} onChange={(e) => handleNestedChange(e, 'alamat')} required={isRegisterMode} style={formStyles.input} />
            </div>
            <div style={formStyles.inputGroup}>
              <label htmlFor="kelurahan" style={formStyles.label}>Kelurahan:</label>
              <input type="text" id="kelurahan" name="kelurahan" value={formData.alamat.kelurahan} onChange={(e) => handleNestedChange(e, 'alamat')} required={isRegisterMode} style={formStyles.input} />
            </div>
            <div style={formStyles.inputGroup}>
              <label htmlFor="kodePos" style={formStyles.label}>Kode Pos:</label>
              <input type="text" id="kodePos" name="kodePos" value={formData.alamat.kodePos} onChange={(e) => handleNestedChange(e, 'alamat')} required={isRegisterMode} style={formStyles.input} />
            </div>

            {/* Wali */}
            <h3>Wali (Jika diperlukan)</h3>
            <div style={formStyles.inputGroup}>
              <label htmlFor="jenisWali" style={formStyles.label}>Jenis Wali:</label>
              <select id="jenisWali" name="jenisWali" value={formData.wali.jenisWali} onChange={(e) => handleNestedChange(e, 'wali')} style={formStyles.input}>
                <option value="">Pilih Jenis Wali</option>
                {jenisWaliOptions.map(option => (
                  <option key={option.value} value={option.value}>{option.label}</option>
                ))}
              </select>
            </div>
            <div style={formStyles.inputGroup}>
              <label htmlFor="namaLengkapWali" style={formStyles.label}>Nama Lengkap Wali:</label>
              <input type="text" id="namaLengkapWali" name="namaLengkapWali" value={formData.wali.namaLengkapWali} onChange={(e) => handleNestedChange(e, 'wali')} style={formStyles.input} />
            </div>
            <div style={formStyles.inputGroup}>
              <label htmlFor="pekerjaanWali" style={formStyles.label}>Pekerjaan Wali:</label>
              <input type="text" id="pekerjaanWali" name="pekerjaanWali" value={formData.wali.pekerjaanWali} onChange={(e) => handleNestedChange(e, 'wali')} style={formStyles.input} />
            </div>
            <div style={formStyles.inputGroup}>
              <label htmlFor="alamatWali" style={formStyles.label}>Alamat Wali:</label>
              <input type="text" id="alamatWali" name="alamatWali" value={formData.wali.alamatWali} onChange={(e) => handleNestedChange(e, 'wali')} style={formStyles.input} />
            </div>
            <div style={formStyles.inputGroup}>
              <label htmlFor="nomorTeleponWali" style={formStyles.label}>Nomor Telepon Wali:</label>
              <input type="text" id="nomorTeleponWali" name="nomorTeleponWali" value={formData.wali.nomorTeleponWali} onChange={(e) => handleNestedChange(e, 'wali')} style={formStyles.input} />
            </div>
          </>
        )}

        <button type="submit" style={formStyles.submitButton}>
          {isRegisterMode ? 'Register Akun & Identitas' : 'Login'}
        </button>
      </form>

      {/* Tampilkan pesan sukses */}
      {message && !error && (
        <p style={{ ...formStyles.messageBox, backgroundColor: '#e0f7fa', borderLeftColor: '#2196f3' }}>
          {message}
        </p>
      )}

      {/* Tampilkan pesan error */}
      {error && (
        <p style={{ ...formStyles.messageBox, backgroundColor: '#ffebee', borderLeftColor: '#f44336', color: '#f44336' }}>
          {error}
        </p>
      )}
    </div>
  );
}

// Gaya terpisah agar kode lebih rapi
const formStyles = {
  container: {
    padding: '20px',
    maxWidth: '800px',
    margin: '50px auto',
    border: '1px solid #ccc',
    borderRadius: '8px',
    boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
    backgroundColor: '#fff',
  },
  inputGroup: {
    marginBottom: '15px',
  },
  label: {
    display: 'block',
    marginBottom: '5px',
    fontWeight: 'bold',
    color: '#555',
  },
  input: {
    width: '100%',
    padding: '10px',
    borderRadius: '4px',
    border: '1px solid #ddd',
    boxSizing: 'border-box', // Penting agar padding tidak menambah lebar
  },
  submitButton: {
    width: '100%',
    padding: '12px',
    backgroundColor: '#007bff',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '18px',
    fontWeight: 'bold',
    transition: 'background-color 0.3s ease',
  },
  submitButtonHover: {
    backgroundColor: '#0056b3',
  },
  messageBox: {
    marginTop: '20px',
    padding: '12px',
    borderRadius: '4px',
    borderLeft: '5px solid',
    color: '#333',
    fontSize: '0.95em',
  },
  validationMessage: {
    fontSize: '0.9em',
    marginTop: '-10px',
    marginBottom: '10px',
    paddingLeft: '5px', // Sedikit indentasi
  }
};

export default AuthForm;