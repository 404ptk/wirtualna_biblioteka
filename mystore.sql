-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 04, 2024 at 10:50 PM
-- Wersja serwera: 10.4.28-MariaDB
-- Wersja PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mystore`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `book`
--

CREATE TABLE `book` (
  `book_name` varchar(200) NOT NULL,
  `book_author` varchar(200) NOT NULL,
  `book_amount` int(11) NOT NULL DEFAULT 0,
  `id` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`book_name`, `book_author`, `book_amount`, `id`) VALUES
('Lalka', 'Bolesław Prus', 9, 12),
('Pan Tadeusz', 'Adam Mickiewicz', 2, 13),
('Antygona', 'Sofokles', 33, 14),
('Cierpienia młodego Wertera', 'Johann Wolfgang von Goethe', 1, 15),
('Dziady', 'Adam Mickiewicz', 7, 16),
('Iliada', 'Homer', 9, 17),
('Kordian', 'Juliusz Słowacki', 12, 18),
('Król Edyp', 'Sofokles', 27, 19),
('Makbet', 'William Shakespeare', 5, 20),
('Oda do młodości', 'Adam Mickiewicz', 6, 21),
('Podróż', 'Ignacy Krasicki', -1, 22),
('Przedwiośnie', 'Stefan Żeromski', 2, 23),
('Potop', 'Henryk Sienkiewicz', 7, 24),
('Skąpiec', 'Molier', 11, 25),
('Świteź', 'Adam Mickiewicz', 28, 26),
('Świat zepsuty', 'Ignacy Krasicki', 3, 27),
('Wesele', 'Stanisław Wyspiański', 35, 28),
('Wojna chocimska', 'Wacław Potocki', 9, 29),
('Wierna rzeka', 'Stefan Żeromski', 6, 30),
('Wspomnienie', 'Bolesław Leśmian', 12, 31),
('Żal', 'Józef Czechowicz', 2, 32),
('test', 'test', 2, 33);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rent`
--

CREATE TABLE `rent` (
  `id_rent` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `book_id` int(11) DEFAULT NULL,
  `rent_date` date DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `payment_amount` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rent`
--

INSERT INTO `rent` (`id_rent`, `user_id`, `book_id`, `rent_date`, `return_date`, `payment_amount`) VALUES
(6, 11, 12, '2024-02-03', '2024-02-17', NULL),
(10, 10, 12, '2024-02-04', '2024-02-18', NULL),
(13, 10, 15, '2024-02-04', '2024-02-18', NULL),
(14, 12, 30, '2024-02-04', '2024-02-18', NULL),
(15, 12, 28, '2024-02-04', '2024-02-18', NULL),
(16, 12, 26, '2024-02-04', '2024-02-18', NULL),
(17, 12, 23, '2024-02-04', '2024-02-18', NULL),
(18, 12, 19, '2024-02-04', '2024-02-18', NULL),
(19, 14, 30, '2024-02-04', '2024-02-18', NULL),
(20, 14, 29, '2024-02-04', '2024-02-18', NULL),
(21, 15, 21, '2024-02-04', '2024-02-18', NULL),
(22, 15, 14, '2024-02-04', '2024-02-18', NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` int(10) NOT NULL,
  `name` varchar(11) NOT NULL,
  `surname` varchar(200) NOT NULL,
  `mail` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `books` varchar(255) DEFAULT NULL,
  `overdue_books` int(11) DEFAULT 0,
  `user_type` varchar(50) NOT NULL DEFAULT 'standard'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `surname`, `mail`, `password`, `books`, `overdue_books`, `user_type`) VALUES
(10, 'Patryk', 'Jaro', '123066@stud.ur.edu.pl', 'admin', '0, 13: 2024-02-18, 15: 2024-02-18, 22: 2024-02-18, 22: 2024-02-18', 0, 'standard'),
(11, 'Krzysztof', 'Motas', 'km660321@stud.ur.edu.pl', 'admin', '0', 0, 'standard'),
(12, 'Testowy', 'Użytkownik', 'test@test.pl', 'admin', ', 30: 2024-02-18, 28: 2024-02-18, 26: 2024-02-18, 23: 2024-02-18, 19: 2024-02-18', 0, 'standard'),
(13, 'Adam', 'Nowak', 'adamnowak@gmail.com', 'admin', NULL, 0, 'standard'),
(14, 'Piotr', 'Kowal', 'piotr333@onet.pl', 'admin', ', 30: 2024-02-18, 29: 2024-02-18', 0, 'standard'),
(15, 'Dawid', 'Wójcik', 'dawidjasper@onet.eu', 'admin', ', 21: 2024-02-18, 14: 2024-02-18', 0, 'standard');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `rent`
--
ALTER TABLE `rent`
  ADD PRIMARY KEY (`id_rent`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `book_id` (`book_id`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `rent`
--
ALTER TABLE `rent`
  MODIFY `id_rent` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `rent`
--
ALTER TABLE `rent`
  ADD CONSTRAINT `rent_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `rent_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
