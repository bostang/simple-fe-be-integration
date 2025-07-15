import React from 'react';
import { Link } from 'react-router-dom'; // Untuk navigasi

const LandingPage = () => {
  return (
    <div style={styles.container}>
      <h1>Selamat Datang!</h1>
      <p>Silakan masuk atau daftar untuk melanjutkan.</p>
      <div style={styles.buttonGroup}>
        <Link to="/login" style={styles.button}>Login</Link>
        <Link to="/register" style={styles.button}>Daftar</Link>
      </div>
    </div>
  );
};

const styles = {
  container: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    minHeight: '80vh', // Minimal tinggi viewport
    textAlign: 'center',
    padding: '20px',
  },
  buttonGroup: {
    marginTop: '30px',
    display: 'flex',
    gap: '20px', // Jarak antar tombol
  },
  button: {
    padding: '12px 25px',
    backgroundColor: '#007bff',
    color: 'white',
    textDecoration: 'none',
    borderRadius: '8px',
    fontSize: '18px',
    fontWeight: 'bold',
    transition: 'background-color 0.3s ease',
    boxShadow: '0 4px 8px rgba(0,0,0,0.1)',
  },
  buttonHover: {
    backgroundColor: '#0056b3',
  }
};

export default LandingPage;