// App.js
import React, { useState, useEffect } from 'react'; // Tambahkan useEffect
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LandingPage from './pages/LandingPage';
import AuthForm from './components/AuthForm';
import DashboardPage from './pages/DashboardPage';

const App = () => {
  // Gunakan state untuk melacak status autentikasi
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // Periksa status autentikasi saat komponen mount
  useEffect(() => {
    if (localStorage.getItem('token')) {
      setIsLoggedIn(true);
    }
  }, []); // [] agar hanya berjalan sekali saat mount

  // Fungsi untuk memperbarui status login
  const handleLoginSuccess = () => {
    setIsLoggedIn(true);
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    setIsLoggedIn(false);
  };

  return (
    <Router>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/register" element={<AuthForm type="register" />} />

        {/* Kirim fungsi handleLoginSuccess ke AuthForm */}
        <Route
          path="/login"
          element={
            isLoggedIn ? (
              <Navigate to="/dashboard" replace />
            ) : (
              <AuthForm type="login" onLoginSuccess={handleLoginSuccess} />
            )
          }
        />

        {/* Halaman Dashboard (Protected Route) */}
        <Route
          path="/dashboard"
          element={
            isLoggedIn ? (
              <DashboardPage onLogout={handleLogout} /> // Kirim juga onLogout ke DashboardPage
            ) : (
              <Navigate to="/login" replace />
            )
          }
        />

        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </Router>
  );
};

export default App;