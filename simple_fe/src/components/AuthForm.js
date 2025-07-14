// src/components/AuthForm.js
import React, { useState } from 'react';
import axios from 'axios';

function AuthForm() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState(''); // Untuk menampilkan pesan sukses/error
  const [isRegistering, setIsRegistering] = useState(true); // Toggle antara Register dan Login

  // URL dasar API backend Anda
  // Penting: Sesuaikan ini dengan URL backend Spring Boot Anda
  // Di lingkungan produksi, Anda bisa menggunakan variabel lingkungan seperti process.env.REACT_APP_API_URL
  const API_BASE_URL = 'http://localhost:8081/api/auth';

  const handleSubmit = async (event) => {
    event.preventDefault(); // Mencegah reload halaman

    setMessage(''); // Reset pesan sebelumnya

    const endpoint = isRegistering ? '/register' : '/login';
    const requestBody = { username, password };

    try {
      const response = await axios.post(`${API_BASE_URL}${endpoint}`, requestBody);
      setMessage(response.data); // Menampilkan pesan sukses dari backend
      console.log('Response:', response.data);
      // Anda bisa menambahkan logika lain di sini, misalnya redirect setelah login sukses
    } catch (error) {
      if (error.response) {
        // Error dari server (misalnya 400 Bad Request, 401 Unauthorized, 404 Not Found)
        setMessage(`Error: ${error.response.data}`);
        console.error('Error response data:', error.response.data);
      } else if (error.request) {
        // Request dibuat tapi tidak ada respon (misalnya server mati)
        setMessage('Error: Tidak ada respons dari server. Pastikan backend berjalan.');
        console.error('Error request:', error.request);
      } else {
        // Error lainnya
        setMessage(`Error: ${error.message}`);
        console.error('Error message:', error.message);
      }
    }
  };

  return (
    <div style={{ padding: '20px', maxWidth: '400px', margin: '50px auto', border: '1px solid #ccc', borderRadius: '8px', boxShadow: '0 2px 4px rgba(0,0,0,0.1)' }}>
      <h2>{isRegistering ? 'Register' : 'Login'}</h2>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '15px' }}>
          <label htmlFor="username" style={{ display: 'block', marginBottom: '5px' }}>Username:</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
            style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }}
          />
        </div>
        <div style={{ marginBottom: '15px' }}>
          <label htmlFor="password" style={{ display: 'block', marginBottom: '5px' }}>Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ddd' }}
          />
        </div>
        <button
          type="submit"
          style={{
            width: '100%',
            padding: '10px',
            backgroundColor: '#007bff',
            color: 'white',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer',
            fontSize: '16px'
          }}
        >
          {isRegistering ? 'Register' : 'Login'}
        </button>
      </form>

      {message && (
        <p style={{ marginTop: '20px', padding: '10px', backgroundColor: '#e0f7fa', borderLeft: '5px solid #2196f3', color: '#333' }}>
          {message}
        </p>
      )}

      <button
        onClick={() => setIsRegistering(!isRegistering)}
        style={{
          marginTop: '15px',
          padding: '8px 15px',
          backgroundColor: '#6c757d',
          color: 'white',
          border: 'none',
          borderRadius: '4px',
          cursor: 'pointer',
          fontSize: '14px'
        }}
      >
        {isRegistering ? 'Sudah punya akun? Login' : 'Belum punya akun? Register'}
      </button>
    </div>
  );
}

export default AuthForm;