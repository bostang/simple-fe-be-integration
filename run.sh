#!/bin/bash

# Ambil direktori saat ini sebagai root project
ROOT_DIR="$(pwd)"

# Inisialisasi variabel untuk flag
RUN_VERIF=false
RUN_BE=false
RUN_FE=false

# Fungsi untuk menampilkan penggunaan script
usage() {
  echo "Penggunaan: $0 [--be] [--fe] [--verif]"
  echo "  --be    : Jalankan Backend"
  echo "  --fe    : Jalankan Frontend"
  echo "  --verif : Jalankan Verifikator"
  echo "Jika tidak ada flag yang diberikan, semua komponen akan dijalankan."
  exit 1
}

# Parsing argumen baris perintah
while [[ "$#" -gt 0 ]]; do
  case "$1" in
    --be)
      RUN_BE=true
      ;;
    --fe)
      RUN_FE=true
      ;;
    --verif)
      RUN_VERIF=true
      ;;
    -h|--help)
      usage
      ;;
    *)
      echo "Argumen tidak valid: $1"
      usage
      ;;
  esac
  shift
done

# Jika tidak ada flag yang diberikan, jalankan semua
if ! $RUN_BE && ! $RUN_FE && ! $RUN_VERIF; then
  RUN_VERIF=true
  RUN_BE=true
  RUN_FE=true
fi

# Jalankan Verifikator
if $RUN_VERIF; then
  echo "---"
  echo "Menjalankan Verifikator..."
  gnome-terminal --tab -- bash -c "
  cd \"$ROOT_DIR/eksternal_dukcapil/demo\";
  echo 'Menjalankan Verifikator...';
  ./mvnw spring-boot:run;
  exec bash" &
fi

# Jalankan Backend
if $RUN_BE; then
  echo "---"
  echo "Menjalankan Backend..."
  gnome-terminal --tab -- bash -c "
  cd \"$ROOT_DIR/be\";
  echo 'Menjalankan Backend...';
  ./mvnw spring-boot:run;
  exec bash" &
fi

# Jalankan Frontend
if $RUN_FE; then
  echo "---"
  echo "Menjalankan Frontend..."
  gnome-terminal --tab -- bash -c "
  cd \"$ROOT_DIR/fe\";
  echo 'Menjalankan Frontend...';
  npm start;
  exec bash" &
fi

echo "---"
echo "Proses dimulai. Tutup jendela terminal untuk menghentikan aplikasi."
wait # Tunggu hingga semua proses di child terminal selesai