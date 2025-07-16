import axios from 'axios';

// Create an Axios instance
const axiosInstance = axios.create({
  // baseURL: 'http://localhost:8083/', // Sesuaikan dengan base URL backend Anda
  baseURL: 'http://backend:8083/', // Sesuaikan dengan base URL backend Anda
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor untuk menambahkan token dari localStorage
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token'); // Ambil token dari localStorage
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`; // Tambahkan ke header
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default axiosInstance;