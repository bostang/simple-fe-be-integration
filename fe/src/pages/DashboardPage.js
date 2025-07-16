import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom'; // Untuk redirect setelah logout
// import axios from 'axios'; // Untuk memanggil API yang dilindungi
import axiosInstance from '../axiosConfig'; // Impor axiosInstance Anda

const DashboardPage = () => {
  const navigate = useNavigate();
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchProtectedData = async () => {
      const token = localStorage.getItem('token'); // Ambil token dari Local Storage
      if (!token) {
        navigate('/login'); // Jika tidak ada token, arahkan ke halaman login
        return;
      }

      try {
        // Ganti URL ini dengan endpoint API Spring Boot yang dilindungi
        // Contoh: endpoint yang membutuhkan autentikasi
        // const response = await axios.get('http://backend:8083/api/protected-resource', {
        //   headers: {
        //     'Authorization': `Bearer ${token}` // Kirim token di header Authorization
        //   }
        // });
        const response = await axiosInstance.get('/api/protected-resource'); // Path relatif karena baseURL sudah diatur
        setMessage(response.data); // Asumsi backend mengirim pesan selamat datang
      } catch (err) {
        console.error('Error fetching protected data:', err);
        setError('Gagal memuat data. Mungkin Anda perlu login kembali.');
        // Jika token tidak valid atau kadaluarsa, arahkan ke login
        if (err.response && (err.response.status === 401 || err.response.status === 403)) {
          localStorage.removeItem('token'); // Hapus token kadaluarsa
          navigate('/login');
        }
      } finally {
        setLoading(false);
      }
    };

    fetchProtectedData();
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem('token'); // Hapus token dari local storage
    navigate('/login'); // Arahkan ke halaman login
  };

  if (loading) {
    return <div style={styles.container}>Memuat data...</div>;
  }

  if (error) {
    return (
      <div style={styles.container}>
        <p style={{ color: 'red' }}>{error}</p>
        <button onClick={handleLogout} style={styles.logoutButton}>Login Ulang</button>
      </div>
    );
  }

  return (
    <div style={styles.container}>
      <h2>Selamat Datang di Dashboard!</h2>
      <p>{message}</p>
      <p>Ini adalah halaman yang hanya bisa diakses oleh pengguna terautentikasi.</p>
      <button onClick={handleLogout} style={styles.logoutButton}>Logout</button>
    </div>
  );
};

const styles = {
  container: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    minHeight: '80vh',
    textAlign: 'center',
    padding: '20px',
  },
  logoutButton: {
    marginTop: '30px',
    padding: '10px 20px',
    backgroundColor: '#dc3545',
    color: 'white',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
    fontSize: '16px',
    transition: 'background-color 0.3s ease',
  },
  logoutButtonHover: {
    backgroundColor: '#c82333',
  }
};

export default DashboardPage;