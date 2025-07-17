// DashboardPage.js
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

// Terima onLogout sebagai prop
const DashboardPage = ({ onLogout }) => {
  const navigate = useNavigate();
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchProtectedData = async () => {
      const token = localStorage.getItem('token');
      if (!token) {
        if (onLogout) onLogout(); // Panggil onLogout untuk memastikan state App.js sinkron
        navigate('/login');
        return;
      }

      try {
        const response = await axios.get('http://localhost:8083/api/protected-resource', {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        setMessage(response.data);
      } catch (err) {
        console.error('Error fetching protected data:', err);
        setError('Gagal memuat data. Mungkin Anda perlu login kembali.');
        if (err.response && (err.response.status === 401 || err.response.status === 403)) {
          localStorage.removeItem('token');
          if (onLogout) onLogout(); // Panggil onLogout jika token tidak valid
          navigate('/login');
        }
      } finally {
        setLoading(false);
      }
    };

    fetchProtectedData();
  }, [navigate, onLogout]); // Tambahkan onLogout ke dependency array

  const handleLogout = () => {
    localStorage.removeItem('token');
    if (onLogout) onLogout(); // Panggil onLogout saat logout
    navigate('/login');
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