// src/components/AuthForm.js
import React, { useState } from 'react';
import axios from 'axios';

function AuthForm() {
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    namaLengkap: '',
    NIK: '',
    namaIbuKandung: '',
    nomorTelepon: '',
    email: '',
    tipeAkun: '', // Akan jadi string yang sama dengan nama enum (e.g., 'BNI_TAPLUS')
    tempatLahir: '',
    tanggalLahir: '',
    jenisKelamin: '', // Akan jadi string nama enum (e.g., 'LAKI_LAKI')
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
  const [isRegistering, setIsRegistering] = useState(true);

  const API_BASE_URL = 'http://localhost:8081/api/auth'; // Sesuaikan port

  // Opsi Dropdown
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

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevData => ({
      ...prevData,
      [name]: value
    }));
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

  const handleSubmit = async (event) => {
    event.preventDefault();
    setMessage('');

    const endpoint = isRegistering ? '/register' : '/login';

    // Pastikan kodeRekening diubah menjadi number jika ada isinya, kalau tidak null
    const dataToSend = {
      ...formData,
      kodeRekening: formData.kodeRekening ? parseInt(formData.kodeRekening, 10) : null
    };

    try {
      const response = await axios.post(`${API_BASE_URL}${endpoint}`, dataToSend);
      setMessage(response.data);
      console.log('Response:', response.data);
    } catch (error) {
      if (error.response) {
        setMessage(`Error: ${error.response.data}`);
        console.error('Error response data:', error.response.data);
      } else if (error.request) {
        setMessage('Error: Tidak ada respons dari server. Pastikan backend berjalan.');
        console.error('Error request:', error.request);
      } else {
        setMessage(`Error: ${error.message}`);
        console.error('Error message:', error.message);
      }
    }
  };

  return (
    <div style={{ padding: '20px', maxWidth: '800px', margin: '50px auto', border: '1px solid #ccc', borderRadius: '8px', boxShadow: '0 2px 4px rgba(0,0,0,0.1)' }}>
      <h2>{isRegistering ? 'Register Akun Baru' : 'Login'}</h2>
      <form onSubmit={handleSubmit}>
        {/* Username & Password */}
        <div style={{ marginBottom: '15px' }}>
          <label htmlFor="username" style={{ display: 'block', marginBottom: '5px' }}>Username:</label>
          <input type="text" id="username" name="username" value={formData.username} onChange={handleChange} required style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
        </div>
        <div style={{ marginBottom: '15px' }}>
          <label htmlFor="password" style={{ display: 'block', marginBottom: '5px' }}>Password:</label>
          <input type="password" id="password" name="password" value={formData.password} onChange={handleChange} required style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
        </div>

        {/* Field Identitas Diri (Hanya muncul saat Register) */}
        {isRegistering && (
          <>
            <h3>Data Diri</h3>
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="namaLengkap" style={{ display: 'block', marginBottom: '5px' }}>Nama Lengkap:</label>
              <input type="text" id="namaLengkap" name="namaLengkap" value={formData.namaLengkap} onChange={handleChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="NIK" style={{ display: 'block', marginBottom: '5px' }}>NIK:</label>
              <input type="text" id="NIK" name="NIK" value={formData.NIK} onChange={handleChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="namaIbuKandung" style={{ display: 'block', marginBottom: '5px' }}>Nama Ibu Kandung:</label>
              <input type="text" id="namaIbuKandung" name="namaIbuKandung" value={formData.namaIbuKandung} onChange={handleChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="nomorTelepon" style={{ display: 'block', marginBottom: '5px' }}>Nomor Telepon:</label>
              <input type="text" id="nomorTelepon" name="nomorTelepon" value={formData.nomorTelepon} onChange={handleChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="email" style={{ display: 'block', marginBottom: '5px' }}>Email:</label>
              <input type="email" id="email" name="email" value={formData.email} onChange={handleChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>

            {/* Dropdown for Tipe Akun */}
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="tipeAkun" style={{ display: 'block', marginBottom: '5px' }}>Tipe Akun:</label>
              <select id="tipeAkun" name="tipeAkun" value={formData.tipeAkun} onChange={handleChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }}>
                <option value="">Pilih Tipe Akun</option>
                {tipeAkunOptions.map(option => (
                  <option key={option.value} value={option.value}>{option.label}</option>
                ))}
              </select>
            </div>

            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="tempatLahir" style={{ display: 'block', marginBottom: '5px' }}>Tempat Lahir:</label>
              <input type="text" id="tempatLahir" name="tempatLahir" value={formData.tempatLahir} onChange={handleChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="tanggalLahir" style={{ display: 'block', marginBottom: '5px' }}>Tanggal Lahir (YYYY-MM-DD):</label>
              <input type="date" id="tanggalLahir" name="tanggalLahir" value={formData.tanggalLahir} onChange={handleChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>

            {/* Dropdown for Jenis Kelamin */}
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="jenisKelamin" style={{ display: 'block', marginBottom: '5px' }}>Jenis Kelamin:</label>
              <select id="jenisKelamin" name="jenisKelamin" value={formData.jenisKelamin} onChange={handleChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }}>
                <option value="">Pilih Jenis Kelamin</option>
                {jenisKelaminOptions.map(option => (
                  <option key={option.value} value={option.value}>{option.label}</option>
                ))}
              </select>
            </div>

            {/* Dropdown for Agama */}
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="agama" style={{ display: 'block', marginBottom: '5px' }}>Agama:</label>
              <select id="agama" name="agama" value={formData.agama} onChange={handleChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }}>
                <option value="">Pilih Agama</option>
                {agamaOptions.map(option => (
                  <option key={option.value} value={option.value}>{option.label}</option>
                ))}
              </select>
            </div>

            {/* Dropdown for Status Pernikahan */}
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="statusPernikahan" style={{ display: 'block', marginBottom: '5px' }}>Status Pernikahan:</label>
              <select id="statusPernikahan" name="statusPernikahan" value={formData.statusPernikahan} onChange={handleChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }}>
                <option value="">Pilih Status Pernikahan</option>
                {statusPernikahanOptions.map(option => (
                  <option key={option.value} value={option.value}>{option.label}</option>
                ))}
              </select>
            </div>

            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="pekerjaan" style={{ display: 'block', marginBottom: '5px' }}>Pekerjaan:</label>
              <input type="text" id="pekerjaan" name="pekerjaan" value={formData.pekerjaan} onChange={handleChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>

            {/* Dropdown for Sumber Penghasilan */}
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="sumberPenghasilan" style={{ display: 'block', marginBottom: '5px' }}>Sumber Penghasilan:</label>
              <select id="sumberPenghasilan" name="sumberPenghasilan" value={formData.sumberPenghasilan} onChange={handleChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }}>
                <option value="">Pilih Sumber Penghasilan</option>
                {sumberPenghasilanOptions.map(option => (
                  <option key={option.value} value={option.value}>{option.label}</option>
                ))}
              </select>
            </div>

            {/* Dropdown for Rentang Gaji */}
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="rentangGaji" style={{ display: 'block', marginBottom: '5px' }}>Rentang Gaji:</label>
              <select id="rentangGaji" name="rentangGaji" value={formData.rentangGaji} onChange={handleChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }}>
                <option value="">Pilih Rentang Gaji</option>
                {rentangGajiOptions.map(option => (
                  <option key={option.value} value={option.value}>{option.label}</option>
                ))}
              </select>
            </div>

            {/* Dropdown for Tujuan Pembuatan Rekening */}
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="tujuanPembuatanRekening" style={{ display: 'block', marginBottom: '5px' }}>Tujuan Pembuatan Rekening:</label>
              <select id="tujuanPembuatanRekening" name="tujuanPembuatanRekening" value={formData.tujuanPembuatanRekening} onChange={handleChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }}>
                <option value="">Pilih Tujuan Rekening</option>
                {tujuanRekeningOptions.map(option => (
                  <option key={option.value} value={option.value}>{option.label}</option>
                ))}
              </select>
            </div>

            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="kodeRekening" style={{ display: 'block', marginBottom: '5px' }}>Kode Rekening:</label>
              <input type="number" id="kodeRekening" name="kodeRekening" value={formData.kodeRekening} onChange={handleChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>

            {/* Alamat */}
            <h3>Alamat</h3>
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="namaAlamat" style={{ display: 'block', marginBottom: '5px' }}>Nama Alamat:</label>
              <input type="text" id="namaAlamat" name="namaAlamat" value={formData.alamat.namaAlamat} onChange={(e) => handleNestedChange(e, 'alamat')} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="provinsi" style={{ display: 'block', marginBottom: '5px' }}>Provinsi:</label>
              <input type="text" id="provinsi" name="provinsi" value={formData.alamat.provinsi} onChange={(e) => handleNestedChange(e, 'alamat')} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="kota" style={{ display: 'block', marginBottom: '5px' }}>Kota:</label>
              <input type="text" id="kota" name="kota" value={formData.alamat.kota} onChange={(e) => handleNestedChange(e, 'alamat')} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="kecamatan" style={{ display: 'block', marginBottom: '5px' }}>Kecamatan:</label>
              <input type="text" id="kecamatan" name="kecamatan" value={formData.alamat.kecamatan} onChange={(e) => handleNestedChange(e, 'alamat')} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="kelurahan" style={{ display: 'block', marginBottom: '5px' }}>Kelurahan:</label>
              <input type="text" id="kelurahan" name="kelurahan" value={formData.alamat.kelurahan} onChange={(e) => handleNestedChange(e, 'alamat')} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="kodePos" style={{ display: 'block', marginBottom: '5px' }}>Kode Pos:</label>
              <input type="text" id="kodePos" name="kodePos" value={formData.alamat.kodePos} onChange={(e) => handleNestedChange(e, 'alamat')} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>

            {/* Wali */}
            <h3>Wali</h3>
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="jenisWali" style={{ display: 'block', marginBottom: '5px' }}>Jenis Wali:</label>
              <select id="jenisWali" name="jenisWali" value={formData.wali.jenisWali} onChange={(e) => handleNestedChange(e, 'wali')} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }}>
                <option value="">Pilih Jenis Wali</option>
                {jenisWaliOptions.map(option => (
                  <option key={option.value} value={option.value}>{option.label}</option>
                ))}
              </select>
            </div>
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="namaLengkapWali" style={{ display: 'block', marginBottom: '5px' }}>Nama Lengkap Wali:</label>
              <input type="text" id="namaLengkapWali" name="namaLengkapWali" value={formData.wali.namaLengkapWali} onChange={(e) => handleNestedChange(e, 'wali')} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="pekerjaanWali" style={{ display: 'block', marginBottom: '5px' }}>Pekerjaan Wali:</label>
              <input type="text" id="pekerjaanWali" name="pekerjaanWali" value={formData.wali.pekerjaanWali} onChange={(e) => handleNestedChange(e, 'wali')} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="alamatWali" style={{ display: 'block', marginBottom: '5px' }}>Alamat Wali:</label>
              <input type="text" id="alamatWali" name="alamatWali" value={formData.wali.alamatWali} onChange={(e) => handleNestedChange(e, 'wali')} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>
            <div style={{ marginBottom: '15px' }}>
              <label htmlFor="nomorTeleponWali" style={{ display: 'block', marginBottom: '5px' }}>Nomor Telepon Wali:</label>
              <input type="text" id="nomorTeleponWali" name="nomorTeleponWali" value={formData.wali.nomorTeleponWali} onChange={(e) => handleNestedChange(e, 'wali')} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }} />
            </div>
          </>
        )}

        <button type="submit" style={{ width: '100%', padding: '10px', backgroundColor: '#007bff', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer', fontSize: '16px' }}>
          {isRegistering ? 'Register Akun & Identitas' : 'Login'}
        </button>
      </form>

      {message && (
        <p style={{ marginTop: '20px', padding: '10px', backgroundColor: '#e0f7fa', borderLeft: '5px solid #2196f3', color: '#333' }}>
          {message}
        </p>
      )}

      <button onClick={() => setIsRegistering(!isRegistering)} style={{ marginTop: '15px', padding: '8px 15px', backgroundColor: '#6c757d', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer', fontSize: '14px' }}>
        {isRegistering ? 'Sudah punya akun? Login' : 'Belum punya akun? Register'}
      </button>
    </div>
  );
}

export default AuthForm;