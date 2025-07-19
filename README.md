**Nama proyek **

Nama proyek ini adalah Aplikasi Informasi Raja.

**Deskripsi proyek **

Aplikasi Informasi Raja adalah sebuah aplikasi Android berbasis Java yang berfungsi sebagai platform informatif mengenai raja-raja dan tokoh monarki bersejarah. Proyek ini dirancang untuk menggabungkan dua sumber data. data yang dikelola secara lokal oleh pengguna dan data dinamis yang diambil dari internet melalui REST API.

**Tujuan Proyek**

Mengembangkan aplikasi mobile yang dapat mengelola, menampilkan, dan memperkaya informasi tentang tokoh-tokoh monarki bersejarah dengan mengintegrasikan database lokal SQLite dan REST API eksternal.

**Teknologi atau Bahasa Pemograman**

Meningkatkan kemampuan pemrograman mobile dengan menggunakan bahasa pemrograman Java dan platform Android native untuk mengimplementasikan berbagai konsep, seperti:
1.Manajemen Database Lokal: Menggunakan SQLite untuk operasi CRUD (Create, Read, Update, Delete) secara penuh.
2.Komunikasi Jaringan: Mengintegrasikan REST API publik menggunakan library Volley untuk mengambil dan mengolah data berformat JSON dari internet.
3.Antarmuka Pengguna Dinamis: Membangun tampilan daftar yang efisien menggunakan RecyclerView dan CardView.
4.Manajemen Sesi dan Navigasi: Mengelola sesi login pengguna dengan SharedPreferences dan mengatur alur aplikasi multi-halaman menggunakan Intent.
5.Desain Antarmuka Kustom: Menerapkan tema dan style kustom untuk menciptakan identitas visual yang konsisten dan menarik.

===================================

**FITUR PROYEK**

**Sistem Otentikasi dan Manajemen Pengguna**

1.Registrasi Pengguna: Pengguna dapat membuat akun baru dengan mendaftarkan username dan password.
2.Login Pengguna: Pengguna yang sudah terdaftar dapat masuk ke dalam aplikasi untuk mendapatkan hak akses penuh.
3.Logout: Pengguna dapat keluar dari sesi login mereka, yang akan menghapus status login dan mengembalikan mereka ke halaman awal.
4.Mode Tamu (Guest Mode): Pengguna yang tidak ingin login dapat masuk sebagai tamu dengan hak akses terbatas (hanya bisa melihat data).

**Manajemen Data Raja (CRUD)**

1.Create (Tambah Data): Pengguna terdaftar dapat menambahkan data raja baru ke dalam database lokal.
2.Read (Lihat Data): Semua pengguna (baik terdaftar maupun tamu) dapat melihat daftar raja yang tersimpan di database lokal. Pengguna juga bisa mengklik item untuk melihat halaman detail.
3.Update (Edit Data): Pengguna terdaftar dapat mengubah informasi data raja yang sudah ada, termasuk nama, periode, deskripsi, dan gambar.
4.Delete (Hapus Data): Pengguna terdaftar dapat menghapus data raja dari database lokal.

**Integrasi API dan Pencarian Online**

1.Pencarian Data Eksternal: Terdapat fitur pencarian di halaman Tambah/Edit untuk mencari tokoh monarki dari internet menggunakan REST API.
2.Auto-fill Form: Hasil pencarian dari API dapat dipilih, dan data yang relevan (nama, gelar, deskripsi) akan secara otomatis dimasukkan ke dalam kolom-kolom form, sehingga mempercepat proses entri data.

**Fungsionalitas Gambar**

1.Pemilihan Gambar dari Galeri: Saat menambah atau mengedit data, pengguna dapat memilih gambar dari galeri perangkat untuk diunggah sebagai foto raja.
2.Tampilan Gambar: Gambar ditampilkan baik di daftar utama maupun di halaman detail.

===================================

**TEKNOLOGI YANG DIGUNAKAN**

**Bahasa Pemrograman**

Java: Digunakan sebagai bahasa logika utama untuk membangun fungsionalitas aplikasi.

**Framework**

Android SDK (Native): Aplikasi ini dibangun secara native untuk platform Android, bukan menggunakan framework cross-platform.

**Database**

SQLite: Digunakan sebagai sistem database lokal yang tertanam di dalam aplikasi untuk menyimpan data pengguna dan data raja.

**Desain Antarmuka Pengguna (UI)**

XML: Digunakan untuk mendefinisikan dan merancang seluruh tata letak antarmuka pengguna (UI) aplikasi.

**Komunikasi Jaringan**

Volley: Sebuah library dari Google yang digunakan untuk mempermudah dan mengelola permintaan jaringan (HTTP request) ke REST API.

**Manajemen Gambar**

Glide: Library pihak ketiga yang sangat efisien untuk memuat, meng-cache, dan menampilkan gambar dari berbagai sumber, termasuk dari galeri perangkat

**Arsitektur Komunikasi Data**

REST API: Arsitektur yang digunakan untuk berkomunikasi dengan server eksternal (API-Ninjas) dan mengambil data tokoh bersejarah.

**Format Pertukaran Data**

JSON (JavaScript Object Notation): Format standar yang digunakan untuk mentransfer data antara aplikasi dan server API.

===================================

**DESAIN APLIKASI**

**Desain Antarmuka Pengguna (UI)**

Desain antarmuka pengguna aplikasi ini mengusung konsep tema kerajaan yang elegan dan modern. Tujuannya adalah untuk memberikan pengalaman visual yang konsisten dan menarik, yang selaras dengan konten aplikasi.
Palet Warna: Desain didominasi oleh skema warna gelap untuk menciptakan kesan premium.
Latar Belakang Utama: Biru tua (#0A1D37)
Warna Header & Kartu: Biru yang sedikit lebih cerah (#133B5C)
Warna Aksen: Emas (#FFC107) yang digunakan untuk tombol, ikon, judul, dan elemen penting lainnya untuk memberikan kontras dan menonjolkan interaksi.
Warna Teks: Putih pudar (#F8F8F8) untuk keterbacaan maksimal di latar belakang gelap.
Tata Letak (Layout):
Struktur Utama: Menggunakan CoordinatorLayout di halaman utama untuk mengelola interaksi antara Toolbar dan RecyclerView.
Formulir: Menggunakan ScrollView dan ConstraintLayout untuk halaman login, registrasi, dan tambah/edit agar tata letak tetap rapi dan dapat di-scroll pada layar kecil.
Daftar: Menggunakan CardView untuk setiap item dalam RecyclerView, memberikan efek melayang (elevasi) dan tampilan yang terstruktur.
Ikonografi: Menggunakan ikon kustom (vektor mahkota) sebagai ikon aplikasi.

**Desain Database**

Aplikasi ini menggunakan sistem database SQLite yang tertanam langsung di perangkat untuk penyimpanan data lokal yang persisten. Desain database ini sederhana namun fungsional, terdiri dari dua tabel utama untuk memisahkan data pengguna dan data konten.
Tabel Pengguna (users):
Tujuan: Menyimpan informasi kredensial untuk proses otentikasi.
Struktur Kolom:
ID (INTEGER, PRIMARY KEY, AUTOINCREMENT): Kunci unik untuk setiap pengguna.
USERNAME (TEXT): Menyimpan nama pengguna untuk login.
PASSWORD (TEXT): Menyimpan kata sandi pengguna.
Tabel Raja (kings):
Tujuan: Menyimpan data inti aplikasi, yaitu informasi mengenai raja atau tokoh monarki yang dikelola oleh pengguna.
Struktur Kolom:
ID (INTEGER, PRIMARY KEY, AUTOINCREMENT): Kunci unik untuk setiap data raja.
NAME (TEXT): Nama lengkap raja.
REIGN (TEXT): Gelar atau periode berkuasa.
DESCRIPTION (TEXT): Deskripsi atau informasi detail mengenai raja.
IMAGE_PATH (TEXT): Menyimpan path (URI) dari gambar yang dipilih dari galeri perangkat.

<img width="586" height="353" alt="image" src="https://github.com/user-attachments/assets/a274709b-15c4-4c8f-900a-ded057c034bc" />

**Desain JSON API**

Aplikasi ini menggunakan Rest API Public dari web https://api-ninjas.com/, dan yang di gunakan adalah https://api.api-ninjas.com/v1/historicalfigures.
dan contoh nya adalah https://api.api-ninjas.com/v1/historicalfigures?name=julius caesar

<img width="2048" height="3240" alt="carbon (3)" src="https://github.com/user-attachments/assets/ad2ad21e-8cb4-4076-a779-dc349a27cdc9" />

===================================

**Hasil Aplikasi**

**Halaman Login**

<img width="1919" height="1036" alt="image" src="https://github.com/user-attachments/assets/4601ca5e-ddde-40e0-9467-ff938a1dfc36" />

**Halaman Daftar Akun Baru**

<img width="1919" height="1032" alt="image" src="https://github.com/user-attachments/assets/069b2a4e-30a2-4b6a-8b07-aacb08481197" />

**Halaman Beranda**

<img width="1919" height="1037" alt="image" src="https://github.com/user-attachments/assets/f9f90fb8-d73b-43fa-b618-6ea7dd5449d9" />

**Halaman Beranda (Sebagai Tamu)**

<img width="1919" height="1034" alt="image" src="https://github.com/user-attachments/assets/5cf41eb9-f5f9-4199-bb0c-7aa750b84591" />

**Halaman Tambah**

<img width="1919" height="1034" alt="image" src="https://github.com/user-attachments/assets/111cca9f-d399-4f1d-ba4c-3bf60f276be9" />

**Halamana Detail**

<img width="1919" height="1031" alt="image" src="https://github.com/user-attachments/assets/f9ddc633-c8e6-4f8a-a66b-b3b7c75e1053" />

**Halaman Detail (Sebagai Tamu)**

<img width="1919" height="1036" alt="image" src="https://github.com/user-attachments/assets/6c7bb8a2-0546-4818-a75d-34a9c5dc376a" />


**Halaman Edit**

<img width="1919" height="1032" alt="image" src="https://github.com/user-attachments/assets/a5ac9d23-427b-4722-831f-563be98062c1" />

**Halaman Pencarian**

<img width="1919" height="1033" alt="image" src="https://github.com/user-attachments/assets/2fa42de7-dc7c-43ae-9e34-7bfe447acc2c" />




