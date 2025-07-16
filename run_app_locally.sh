#!/bin/bash

# Ambil direktori saat ini sebagai root project
ROOT_DIR="$(pwd)"

# Jalankan Verifikator di tab pertama
gnome-terminal --tab -- bash -c "
cd \"$ROOT_DIR/eksternal_dukcapil/demo\";
echo 'Menjalankan Verifikator...';
./mvnw spring-boot:run;
exec bash"

# Jalankan Backend di tab kedua
gnome-terminal --tab -- bash -c "
cd \"$ROOT_DIR/be\";
echo 'Menjalankan Backend...';
./mvnw spring-boot:run;
exec bash"

# Jalankan Frontend di tab ketiga
gnome-terminal --tab -- bash -c "
cd \"$ROOT_DIR/fe\";
echo 'Menjalankan Frontend...';
npm start;
exec bash"

