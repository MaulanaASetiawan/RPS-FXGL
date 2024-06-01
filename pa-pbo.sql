-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 01 Jun 2024 pada 16.12
-- Versi server: 10.4.28-MariaDB
-- Versi PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pa-pbo`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `com`
--

CREATE TABLE `com` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `health` int(100) NOT NULL,
  `damage` int(100) NOT NULL,
  `exp` int(100) NOT NULL,
  `stage` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `com`
--

INSERT INTO `com` (`id`, `name`, `health`, `damage`, `exp`, `stage`) VALUES
(1, 'qwe', 100, 100, 10, 1),
(2, 'damn', 100, 10, 10, 1),
(3, 'BOSS', 100, 10, 10, 2),
(4, 'Absolute Being', 5, 10000, 100, 8);

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `Id_User` int(100) NOT NULL,
  `Username` text NOT NULL,
  `Password` text NOT NULL,
  `Health` int(100) NOT NULL,
  `Xp` int(100) NOT NULL,
  `Level` int(100) NOT NULL,
  `Damage` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`Id_User`, `Username`, `Password`, `Health`, `Xp`, `Level`, `Damage`) VALUES
(1, 'qwe', 'qwe', 270, 10, 18, 10),
(2, 'Agus', '024', 130, 20, 4, 10),
(3, 'Saragih', '123', 100, 0, 1, 10),
(4, 'Wilson', '321', 100, 0, 1, 10);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`Id_User`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `Id_User` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
