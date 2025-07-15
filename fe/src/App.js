import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LandingPage from './pages/LandingPage';
import AuthForm from './components/AuthForm'; // Asumsi ini adalah komponen form register/login Anda
import DashboardPage from './pages/DashboardPage';

const App = () => {
  // Fungsi sederhana untuk memeriksa apakah pengguna sudah login (ada token di localStorage)
  const isAuthenticated = () => {
    return localStorage.getItem('token') !== null;
  };

  return (
    <Router>
      <Routes>
        {/* Landing Page sebagai halaman utama */}
        <Route path="/" element={<LandingPage />} />

        {/* Halaman Register */}
        <Route path="/register" element={<AuthForm type="register" />} />

        {/* Halaman Login */}
        <Route path="/login" element={<AuthForm type="login" />} />

        {/* Halaman Dashboard (Protected Route) */}
        <Route
          path="/dashboard"
          element={
            isAuthenticated() ? (
              <DashboardPage />
            ) : (
              <Navigate to="/login" replace /> // Redirect ke login jika belum login
            )
          }
        />

        {/* Redirect untuk path yang tidak dikenal, bisa ke landing page atau 404 */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </Router>
  );
};

export default App;