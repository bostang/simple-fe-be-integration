# Simple Login Form

sebuah frontend sederhana untuk testing connect frontend (berbasis JS React) ke backend.

## Cara Menjalankan

### Langkah 0 : Inisiasi database

```bash
# jalankan DB_initialization.sql
psql -U postgres -f DB_initialization.sql
```

### Langkah 1 : Buat & Jalankan Backend

```bash
# LANGKAH 1 : buat project dgn Spring Initalizr
# dependencies:
    # - Spring Web
    # - Spring Boot DevTools

# LANGKAH 2 : buat seluruh file di src

# LANGKAH 3: JALANKAN APLIKASI SPRINGBOOT
mvn spring-boot:run
```

### Langkah 2 : Buat & Jalankan Frontend

```bash
# CATATAN : buat dari folder simple_fe kosong! jangan pakai yang ada di github repo, karena node_modules ter-ignore.

# LANGKAH 1 : BUAT TEMPLATE KOSONG PROJECT REACT
# sintaks:
    # npx create-react-app <nama_folder>
npx create-react-app simple_fe  

# LANGKAH 2 : INSTALL DEPENDENCY
cd simple_fe # masuk ke dalam folder proyek

npm install react-bootstrap bootstrap
# untuk interaksi API (lebih baik dari fetch)
npm install axios

# LANGKAH 3 : ubah src/App.js , src/components/AuthForm.js 

# LANGKAH 4 : jalankan app react
npm start

# LANGKAH 5 : akses via web localhost
localhost:3000
```

## Demonstrasi

tampilan frontend (password salah) :

![tampilan-frontend-demo-1](./img/tampilan-frontend-demo-1.png)

tampilan frontend (password benar) :

![tampilan-frontend-demo-2](./img/tampilan-frontend-demo-2.png)

tampilan pada log backend:

![tampilan-backend-demo](./img/tampilan-backend-demo.png)

tampilan DB :

![tampilan-db-demo](./img/tampilan-db-demo.png)
