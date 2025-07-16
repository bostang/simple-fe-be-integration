# 🚀 Aplikasi Klien Verifikator NIK

Aplikasi Spring Boot ini berfungsi sebagai **klien** untuk mengonsumsi API dari layanan verifikator NIK eksternal. Dibangun menggunakan **Spring WebFlux (WebClient)** untuk komunikasi non-blocking dan reaktif.

-----

## 📖 Ringkasan

Klien ini menyediakan endpoint RESTful yang dapat dipanggil oleh aplikasi lain (misalnya, frontend, aplikasi mobile) untuk melakukan validasi NIK dan mengambil detail penduduk, tanpa perlu berinteraksi langsung dengan layanan verifikator NIK eksternal.

-----

## ✨ Fitur Utama

- **Validasi Keberadaan NIK**: Memeriksa apakah NIK terdaftar di sistem eksternal.
- **Validasi NIK dan Nama Lengkap**: Memverifikasi kombinasi NIK dan nama lengkap.
- **Detail Penduduk**: Mengambil informasi lengkap seorang penduduk berdasarkan NIK.
- **Komunikasi Reaktif**: Menggunakan `WebClient` untuk panggilan API yang efisien dan non-blocking.

-----

## ⚙️ Persyaratan Sistem

- **Java 17** (atau versi yang lebih tinggi)
- **Maven**
- **Aplikasi Verifikator NIK (Server)** yang berjalan di `http://localhost:8082`

-----

## 🛠️ Cara Menjalankan

1. **Kloning Repositori**:

    ```bash
    git clone <URL_REPOSITORI_KLIEN_ANDA>
    cd nik-client
    ```

2. **Pastikan Server Berjalan**:
    Pastikan aplikasi **Verifikator NIK (Server)** Anda sudah berjalan dan dapat diakses di `http://localhost:8082`.

3. **Konfigurasi Proyek (Opsional, jika ada perubahan)**:
    Periksa file `src/main/resources/application.properties` untuk memastikan port aplikasi klien (default: `8083`) dan base URL server verifikator (default: `http://localhost:8082/api/validator`) sudah benar.

    ```properties
    server.port=8083 # Port aplikasi klien ini
    ```

    Dan di `src/main/java/com/client/demo/config/WebClientConfig.java`:

    ```java
    // ...
    @Bean
    public WebClient externalValidatorWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("http://localhost:8082/api/validator") // SESUAIKAN DENGAN PORT SERVER ANDA
                .build();
    }
    // ...
    ```

4. **Bangun dan Jalankan Aplikasi**:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

    Aplikasi klien akan berjalan di `http://localhost:8083`.

-----

## 🎯 Dokumentasi API (Endpoint Klien)

Semua endpoint berikut harus dipanggil ke aplikasi klien ini (port `8083`), yang kemudian akan meneruskan permintaan ke layanan verifikator NIK eksternal.

**Base URL Klien:** `http://localhost:8083/client`

-----

### 1\. Memeriksa Keberadaan NIK

Memeriksa apakah NIK yang diberikan terdaftar dalam sistem verifikator eksternal.

- **URL**: `/check-nik-exists`

- **Method**: `POST`

- **Header**:

  - `Content-Type`: `application/json`

- **Request Body**:

    ```json
    {
        "nik": "string"
    }
    ```

  - `nik` (string, **required**): Nomor Induk Kependudukan yang akan divalidasi.

- **Response Sukses (`200 OK`)**:

    ```json
    {
        "nikExistsInExternal": true
    }
    ```

  - `nikExistsInExternal` (boolean): `true` jika NIK ditemukan di layanan eksternal, `false` jika tidak.

- **Contoh cURL**:

```bash
# False (nik tidak ada)
curl -X POST \
http://localhost:8083/client/check-nik-exists \
-H 'Content-Type: application/json' \
-d '{
  "nik": "1234567890123456"
}'

# true (nik ada)
curl -X POST \
http://localhost:8083/client/check-nik-exists \
-H 'Content-Type: application/json' \
-d '{
  "nik": "3201010000000001"
}'
```

-----

### 2\. Memvalidasi NIK dan Nama Lengkap

Memeriksa apakah kombinasi NIK dan nama lengkap sesuai dengan data yang terdaftar di layanan verifikator eksternal.

- **URL**: `/check-nik-nama-matches`

- **Method**: `POST`

- **Header**:

  - `Content-Type`: `application/json`

- **Request Body**:

    ```json
    {
        "nik": "string",
        "namaLengkap": "string"
    }
    ```

  - `nik` (string, **required**): Nomor Induk Kependudukan.
  - `namaLengkap` (string, **required**): Nama lengkap yang akan divalidasi bersama NIK.

- **Response Sukses (`200 OK`)**:

    ```json
    {
        "nikNamaMatchesInExternal": true
    }
    ```

  - `nikNamaMatchesInExternal` (boolean): `true` jika kombinasi NIK dan nama lengkap cocok, `false` jika tidak.

- **Contoh cURL**:

    ```bash
    curl -X POST \
      http://localhost:8083/client/check-nik-nama-matches \
      -H 'Content-Type: application/json' \
      -d '{
        "nik": "3201010000000001",
        "namaLengkap": "Ahmad Sulaiman"
      }'
    ```

-----

### 3\. Mendapatkan Detail Penduduk

Mengambil seluruh detail data penduduk dari layanan eksternal berdasarkan NIK.

- **URL**: `/get-penduduk-details/{nik}`

- **Method**: `GET`

- **Path Parameters**:

  - `nik` (string, **required**): Nomor Induk Kependudukan dari penduduk yang detailnya ingin diambil.

- **Response Sukses (`200 OK`)**:

    ```json
    {
        "nik": "string",
        "namaLengkap": "string",
        "tempatLahir": "string",
        "tanggalLahir": "YYYY-MM-DD",
        "jenisKelamin": "string",
        "namaAlamat": "string",
        "kecamatan": "string",
        "kelurahan": "string",
        "agama": "string",
        "statusPerkawinan": "string",
        "pekerjaan": "string",
        "kewarganegaraan": "string",
        "berlakuHingga": "YYYY-MM-DD"
    }
    ```

    *(Struktur ini mencerminkan `PendudukDetailResponse` di sisi klien, yang disesuaikan agar cocok dengan respons dari server)*

- **Response Error (`404 Not Found`)**:

    ```json
    {
        "message": "Penduduk with NIK <NIK_YANG_DIMINTA> not found or error occurred."
    }
    ```

  - Terjadi jika NIK tidak ditemukan di layanan verifikator eksternal atau ada masalah komunikasi.

- **Contoh cURL**:

    ```bash
    curl -X GET \
      http://localhost:8083/client/get-penduduk-details/1234567890123456
    ```
