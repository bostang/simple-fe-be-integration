# Validator

## Uji Coba

### Add dummy data (POST)

```bash
curl --location --request POST 'http://localhost:8082/api/validator/add-dummy-data'
```

### Cek if nik exist (GET)

```bash
curl --location --request POST 'http://localhost:8082/api/validator/validate/nik-exists' \
--header 'Content-Type: application/json' \
--data '{
    "nik": "3201010000000001"
}'

curl --location --request POST 'https://bracelets-marilyn-ing-charging.trycloudflare.com/api/validator/validate/nik-exists' \
--header 'Content-Type: application/json' \
--data '{
    "nik": "3201010000000001"
}'
```

### Detail dari suatu penduduk

```bash
curl --location 'http://localhost:8082/api/validator/get-details/1234567890123456'
```

### validasi

```bash
curl --location --request POST 'http://localhost:8082/api/validator/validate/nik-nama' \
--header 'Content-Type: application/json' \
--data '{
    "nik": "1234567890123456",
    "namaLengkap": "Budi Santoso"
}'
```
