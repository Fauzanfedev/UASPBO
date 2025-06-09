-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 08 Jun 2025 pada 14.43
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `perpus`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `anggota`
--

CREATE TABLE `anggota` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `nim` varchar(30) DEFAULT NULL,
  `jurusan` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `anggota`
--

INSERT INTO `anggota` (`id`, `username`, `nama`, `nim`, `jurusan`, `email`, `password`) VALUES
(1, 'Budi', 'Budi Santoso', 'A123456', 'Teknik Informatika', 'budi@gmail.com', 'budi123'),
(2, 'Ani', 'Ani Lestari', 'A123457', 'Sistem Informasi', 'ani@gmail.com', 'ani123'),
(3, 'Dina', 'Dina Kurnia', 'A123458', 'Teknik Komputer', 'dina@gmail.com', 'dina123'),
(4, 'Rian', 'Rian Saputra', 'A123459', 'Teknik Elektro', 'rian@gmail.com', 'rian123'),
(5, 'Siti', 'Siti Nurhaliza', 'A123460', 'Manajemen Informatika', 'siti@gmail.com', 'siti123');

-- --------------------------------------------------------

--
-- Struktur dari tabel `buku`
--

CREATE TABLE `buku` (
  `id` int(11) NOT NULL,
  `judul` varchar(200) NOT NULL,
  `penulis` varchar(100) DEFAULT NULL,
  `penerbit` varchar(100) DEFAULT NULL,
  `tahun_terbit` int(11) DEFAULT NULL,
  `stok` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `buku`
--

INSERT INTO `buku` (`id`, `judul`, `penulis`, `penerbit`, `tahun_terbit`, `stok`) VALUES
(1, 'Jaringan Komputer Dasar', 'Agus Setiawan', 'Informatika', 2021, 3),
(2, 'Pemrograman Python untuk Data Science', 'Lisa Andini', 'Gramedia', 2022, 5),
(3, 'Kecerdasan Buatan dan Aplikasinya', 'Taufik Hidayat', 'Elex Media', 2023, 4),
(4, 'Desain UI/UX Modern', 'Nina Kartika', 'Deepublish', 2021, 2),
(5, 'Laskar Pelangi', 'Andrea Hirata', 'Bentang Pustaka', 2005, 7),
(6, 'Bumi', 'Tere Liye', 'Gramedia Pustaka Utama', 2014, 5),
(7, 'Pulang', 'Leila S. Chudori', 'Kepustakaan Populer Gramedia', 2012, 3),
(8, 'Negeri 5 Menara', 'Ahmad Fuadi', 'Gramedia', 2009, 4),
(9, 'Habibie & Ainun', 'B.J. Habibie', 'THC Mandiri', 2010, 2),
(10, 'Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', 'Harper', 2014, 2),
(11, 'Atomic Habits', 'James Clear', 'Avery', 2018, 2),
(12, 'Tafsir Al-Misbah', 'M. Quraish Shihab', 'Lentera Hati', 2017, 3),
(13, 'Fiqih Sunnah', 'Sayyid Sabiq', 'Al-Maarif', 2002, 4),
(14, 'Sirah Nabawiyah', 'Ibnu Hisyam', 'Pustaka Al-Kautsar', 2015, 2),
(15, 'Sejarah Indonesia Modern 1200–2008', 'M.C. Ricklefs', 'Serambi', 2008, 2),
(16, 'Jejak Islam di Nusantara', 'Ahmad Mansur Suryanegara', 'Rosda', 2016, 1),
(17, 'Revolusi Indonesia', 'Soekarno', 'Balai Pustaka', 1951, 0);

-- --------------------------------------------------------

--
-- Struktur dari tabel `peminjaman`
--

CREATE TABLE `peminjaman` (
  `id` int(11) NOT NULL,
  `id_anggota` int(11) NOT NULL,
  `id_buku` int(11) NOT NULL,
  `tanggal_pinjam` date NOT NULL,
  `tanggal_kembali` date DEFAULT NULL,
  `status` enum('dipinjam','dikembalikan') DEFAULT 'dipinjam',
  `status_pembayaran_denda` enum('Belum Bayar','Lunas') DEFAULT 'Belum Bayar'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `peminjaman`
--

INSERT INTO `peminjaman` (`id`, `id_anggota`, `id_buku`, `tanggal_pinjam`, `tanggal_kembali`, `status`) VALUES
(1, 1, 1, '2025-06-01', NULL, 'dikembalikan'),
(2, 2, 2, '2025-06-02', '2025-06-05', 'dikembalikan'),
(3, 3, 3, '2025-06-03', NULL, 'dikembalikan'),
(4, 4, 4, '2025-05-30', '2025-06-06', 'dikembalikan'),
(5, 5, 5, '2025-06-05', NULL, 'dikembalikan'),
(6, 1, 1, '2025-06-07', '2025-06-14', 'dikembalikan');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` enum('pustakawan','pengunjung') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `role`) VALUES
(1, 'ojan', 'ojan123', 'pustakawan'),
(2, 'sasa', 'sasa123', 'pustakawan'),
(3, 'vella', 'vella123', 'pustakawan'),
(4, 'dila', 'dila123', 'pustakawan'),
(5, 'budi', 'budi123', 'pengunjung');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `anggota`
--
ALTER TABLE `anggota`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `nim` (`nim`);

--
-- Indeks untuk tabel `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_anggota` (`id_anggota`),
  ADD KEY `id_buku` (`id_buku`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `anggota`
--
ALTER TABLE `anggota`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT untuk tabel `buku`
--
ALTER TABLE `buku`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT untuk tabel `peminjaman`
--
ALTER TABLE `peminjaman`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD CONSTRAINT `peminjaman_ibfk_1` FOREIGN KEY (`id_anggota`) REFERENCES `anggota` (`id`),
  ADD CONSTRAINT `peminjaman_ibfk_2` FOREIGN KEY (`id_buku`) REFERENCES `buku` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
