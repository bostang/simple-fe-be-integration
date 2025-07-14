import React, { useState } from 'react';
import './App.css'; // Anda bisa menyesuaikan atau menghapus CSS ini jika tidak diperlukan

function App() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault(); // Mencegah refresh halaman default

    setMessage('Mengirim data...');

    try {
      const response = await fetch('http://localhost:8081/api/login', { // Ganti dengan URL API Spring Boot Anda
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
      });

      const data = await response.json();

      if (response.ok) {
        setMessage(`Berhasil: ${data.message || 'Login sukses!'}`);
        // Anda bisa menambahkan logika navigasi atau penyimpanan token di sini
      } else {
        setMessage(`Gagal: ${data.message || 'Terjadi kesalahan.'}`);
      }
    } catch (error) {
      setMessage(`Kesalahan koneksi: ${error.message}`);
    }
  };

  return (
    <div className="App">
      <header className="App-header">
        <h2>Login Form</h2>
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="username">Nama Pengguna:</label>
            <input
              type="text"
              id="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div>
            <label htmlFor="password">Kata Sandi:</label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit">Login</button>
        </form>
        {message && <p>{message}</p>}
      </header>
    </div>
  );
}

export default App;

// import logo from './logo.svg';
// import './App.css';

// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.js</code> and save to reload.
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header>
//     </div>
//   );
// }

// export default App;
