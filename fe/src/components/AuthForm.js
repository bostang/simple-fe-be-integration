import React, { useState, useEffect } from 'react';
import axiosInstance from '../axiosConfig';
import { useNavigate } from 'react-router-dom';


// function AuthForm({ type }) {
function AuthForm({ type, onLoginSuccess }) {
const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: '',
    password: '',
    namaLengkap: '',
    nik: '',
    namaIbuKandung: '',
    nomorTelepon: '',
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
  const [error, setError] = useState('');
  const [nikValidationMessage, setNikValidationMessage] = useState('');

  const isRegisterMode = type === 'register';

  const API_AUTH_BASE_URL = '/api/auth';
  const API_CLIENT_BASE_URL = '/client';

  const tipeAkunOptions = [
    { value: 'BNI Taplus', label: 'BNI Taplus' },
    { value: 'BNI Taplus Muda', label: 'BNI Taplus Muda' }
  ];
  const jenisKelaminOptions = [
    { value: 'Laki-laki', label: 'Laki-laki' },
    { value: 'Perempuan', label: 'Perempuan' }
  ];
  const agamaOptions = [
    { value: 'Islam', label: 'Islam' },
    { value: 'Kristen', label: 'Kristen' },
    { value: 'Buddha', label: 'Buddha' },
    { value: 'Hindu', label: 'Hindu' },
    { value: 'Konghucu', label: 'Konghucu' },
    { value: 'Lainnya', label: 'Lainnya' }
  ];
  const statusPernikahanOptions = [
    { value: 'Single', label: 'Single' },
    { value: 'Menikah', label: 'Menikah' },
    { value: 'Duda', label: 'Duda' },
    { value: 'Janda', label: 'Janda' }
  ];
  const sumberPenghasilanOptions = [
    { value: 'Gaji', label: 'Gaji' },
    { value: 'Hasil Investasi', label: 'Hasil Investasi' },
    { value: 'Hasil Usaha', label: 'Hasil Usaha' },
    { value: 'Warisan/Hibah', label: 'Warisan/Hibah' }
  ];
  const rentangGajiOptions = [
    { value: 'Kurang dari Rp3 juta', label: 'Kurang dari Rp3 juta' },
    { value: '>Rp3 - 5 juta', label: '>Rp3 - 5 juta' },
    { value: '>Rp5 - 10 juta', label: '>Rp5 - 10 juta' },
    { value: '>Rp10 - 20 juta', label: '>Rp10 - 20 juta' },
    { value: '>Rp20 - 50 juta', label: '>Rp20 - 50 juta' },
    { value: '>Rp50 - 100 juta', label: '>Rp50 - 100 juta' },
    { value: '>Rp100 juta', label: '>Rp100 juta' }
  ];
  const tujuanRekeningOptions = [
    { value: 'Investasi', label: 'Investasi' },
    { value: 'Tabungan', label: 'Tabungan' },
    { value: 'Transaksi', label: 'Transaksi' }
  ];
  const jenisWaliOptions = [
    { value: 'Ayah', label: 'Ayah' },
    { value: 'Ibu', label: 'Ibu' }
  ];

  useEffect(() => {
    setFormData(prev => ({
      ...prev,
      namaLengkap: '',
      nik: '',
      namaIbuKandung: '',
      nomorTelepon: '',
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
  }, [type]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
    if (name === 'nik' || name === 'namaLengkap') {
      setNikValidationMessage('');
    }
  };

  const handleNestedChange = (e, parentKey) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [parentKey]: {
        ...prev[parentKey],
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
      const res = await axiosInstance.post(`${API_CLIENT_BASE_URL}/check-nik-nama-matches`, {
        nik, namaLengkap
      });
      if (res.data.nikNamaMatchesInExternal) {
        setNikValidationMessage('Validasi NIK dan Nama Lengkap berhasil.');
        return true;
      } else {
        setNikValidationMessage('NIK atau Nama tidak cocok.');
        return false;
      }
    } catch (err) {
      setNikValidationMessage('Gagal validasi NIK. Periksa koneksi/backend.');
      return false;
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');
    setError('');
    const endpoint = isRegisterMode ? '/register' : '/login';
    let dataToSend = {};

    if (isRegisterMode) {
      const isValid = await validateNikAndNama(formData.nik, formData.namaLengkap);
      if (!isValid) return;
      dataToSend = {
        ...formData,
        kodeRekening: formData.kodeRekening ? parseInt(formData.kodeRekening, 10) : null
      };
    } else {
      dataToSend = {
        email: formData.email,
        password: formData.password
      };
    }

    try {
      const res = await axiosInstance.post(`${API_AUTH_BASE_URL}${endpoint}`, dataToSend);
      if (isRegisterMode) {
        setMessage(res.data.message || 'Registrasi berhasil!');
        setTimeout(() => navigate('/login'), 700);
      } else { // Ini adalah blok sukses login
        const token = res.data.token || res.data.jwt || res.data.accessToken;
        if (token) {
          localStorage.setItem('token', token);
          setMessage('Login berhasil!');
          console.log('AuthForm: Token berhasil disimpan. Memanggil onLoginSuccess.');

          // Panggil fungsi callback setelah token tersimpan
          if (onLoginSuccess) {
            onLoginSuccess();
          }

          // Navigasi setelah state di App.js diperbarui
          navigate('/dashboard'); // Tidak perlu setTimeout di sini lagi
        } else {
          setError('Login sukses, tapi token tidak ditemukan.');
        }
      }
    } catch (err) {
      if (err.response) {
        setError(err.response.data.message || 'Error dari server.');
      } else {
        setError('Terjadi kesalahan jaringan.');
      }
    }
  };

  return (
    <div style={styles.container}>
      <h2>{isRegisterMode ? 'Register Akun' : 'Login'}</h2>
      <form onSubmit={handleSubmit}>
        <FormInput label="Email" name="email" type="email" value={formData.email} onChange={handleChange} required />
        <FormInput label="Password" name="password" type="password" value={formData.password} onChange={handleChange} required />

        {isRegisterMode && (
          <>
            <FormInput label="Nama Lengkap" name="namaLengkap" value={formData.namaLengkap} onChange={handleChange} required />
            <FormInput label="NIK" name="nik" value={formData.nik} onChange={handleChange} required />
            {nikValidationMessage && (
              <p style={{ color: nikValidationMessage.includes('berhasil') ? 'green' : 'red' }}>{nikValidationMessage}</p>
            )}
            <FormInput label="Nama Ibu Kandung" name="namaIbuKandung" value={formData.namaIbuKandung} onChange={handleChange} required />
            <FormInput label="Nomor Telepon" name="nomorTelepon" value={formData.nomorTelepon} onChange={handleChange} required />
            <SelectInput label="Tipe Akun" name="tipeAkun" options={tipeAkunOptions} value={formData.tipeAkun} onChange={handleChange} />
            <FormInput label="Tempat Lahir" name="tempatLahir" value={formData.tempatLahir} onChange={handleChange} required />
            <FormInput label="Tanggal Lahir" name="tanggalLahir" type="date" value={formData.tanggalLahir} onChange={handleChange} required />
            <SelectInput label="Jenis Kelamin" name="jenisKelamin" options={jenisKelaminOptions} value={formData.jenisKelamin} onChange={handleChange} />
            <SelectInput label="Agama" name="agama" options={agamaOptions} value={formData.agama} onChange={handleChange} />
            <SelectInput label="Status Pernikahan" name="statusPernikahan" options={statusPernikahanOptions} value={formData.statusPernikahan} onChange={handleChange} />
            <FormInput label="Pekerjaan" name="pekerjaan" value={formData.pekerjaan} onChange={handleChange} required />
            <SelectInput label="Sumber Penghasilan" name="sumberPenghasilan" options={sumberPenghasilanOptions} value={formData.sumberPenghasilan} onChange={handleChange} />
            <SelectInput label="Rentang Gaji" name="rentangGaji" options={rentangGajiOptions} value={formData.rentangGaji} onChange={handleChange} />
            <SelectInput label="Tujuan Pembuatan Rekening" name="tujuanPembuatanRekening" options={tujuanRekeningOptions} value={formData.tujuanPembuatanRekening} onChange={handleChange} />
            <FormInput label="Kode Rekening" name="kodeRekening" type="number" value={formData.kodeRekening} onChange={handleChange} required />
            <h4>Alamat</h4>
            {['namaAlamat', 'provinsi', 'kota', 'kecamatan', 'kelurahan', 'kodePos'].map((f) => (
              <FormInput key={f} label={f} name={f} value={formData.alamat[f]} onChange={(e) => handleNestedChange(e, 'alamat')} required />
            ))}
            <h4>Wali (Opsional)</h4>
            <SelectInput label="Jenis Wali" name="jenisWali" options={jenisWaliOptions} value={formData.wali.jenisWali} onChange={(e) => handleNestedChange(e, 'wali')} />
            <FormInput label="Nama Wali" name="namaLengkapWali" value={formData.wali.namaLengkapWali} onChange={(e) => handleNestedChange(e, 'wali')} />
            <FormInput label="Pekerjaan Wali" name="pekerjaanWali" value={formData.wali.pekerjaanWali} onChange={(e) => handleNestedChange(e, 'wali')} />
            <FormInput label="Alamat Wali" name="alamatWali" value={formData.wali.alamatWali} onChange={(e) => handleNestedChange(e, 'wali')} />
            <FormInput label="No. Telepon Wali" name="nomorTeleponWali" value={formData.wali.nomorTeleponWali} onChange={(e) => handleNestedChange(e, 'wali')} />
          </>
        )}
        <button type="submit" style={styles.submitButton}>{isRegisterMode ? 'Daftar' : 'Login'}</button>
      </form>

      {message && <p style={{ color: 'green' }}>{message}</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
}

function FormInput({ label, ...props }) {
  return (
    <div style={{ marginBottom: '10px' }}>
      <label style={{ display: 'block' }}>{label}</label>
      <input style={styles.input} {...props} />
    </div>
  );
}

function SelectInput({ label, name, value, options, onChange }) {
  return (
    <div style={{ marginBottom: '10px' }}>
      <label style={{ display: 'block' }}>{label}</label>
      <select name={name} value={value} onChange={onChange} style={styles.input}>
        <option value="">Pilih {label}</option>
        {options.map((opt) => (
          <option key={opt.value} value={opt.value}>{opt.label}</option>
        ))}
      </select>
    </div>
  );
}

const styles = {
  container: {
    maxWidth: '800px',
    margin: 'auto',
    padding: '20px',
    background: '#f8f8f8',
    borderRadius: '8px',
    boxShadow: '0px 0px 8px rgba(0,0,0,0.1)'
  },
  input: {
    width: '100%',
    padding: '10px',
    fontSize: '14px',
    borderRadius: '4px',
    border: '1px solid #ccc'
  },
  submitButton: {
    marginTop: '20px',
    width: '100%',
    padding: '12px',
    backgroundColor: '#007bff',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    fontSize: '16px',
    cursor: 'pointer'
  }
};

export default AuthForm;
