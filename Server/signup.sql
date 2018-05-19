-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Φιλοξενητής: fdb15.awardspace.net
-- Χρόνος δημιουργίας: 19 Μάη 2018 στις 13:18:56
-- Έκδοση διακομιστή: 5.7.20-log
-- Έκδοση PHP: 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Βάση δεδομένων: `2433968_egglezos`
--

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `signup`
--

CREATE TABLE `signup` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(40) NOT NULL,
  `email` varchar(40) NOT NULL,
  `lat` varchar(40) NOT NULL,
  `lon` varchar(40) NOT NULL,
  `photo` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Άδειασμα δεδομένων του πίνακα `signup`
--

INSERT INTO `signup` (`id`, `username`, `password`, `email`, `lat`, `lon`, `photo`) VALUES
(1, 'Yiani', '12345', 'Giannis@giannis.com', '37.444706', '24.942956', 'https://natrice.gr/forites/dimitri.png'),
(2, 'Kostas', '12345', 'beta@beta.com', '37.4462141', '24.9427844', 'https://natrice.gr/forites/kostas.png'),
(3, 'Panos', '12345', 'panagiotis@panagiotis.com', '37.443602886077706', '24.944028854370117', 'https://natrice.gr/forites/michel.png'),
(4, 'Billy', '12345', 'basilis@basilis.com', '37.4431684575008', '24.94268238544464', 'https://natrice.gr/forites/billy.png'),
(5, 'Cerni', '12345', 'cerni@cerni.com', '37.441089979996136', '24.941035509109497', 'https://natrice.gr/forites/cinel.png'),
(6, 'Veta', '12345', 'veta@veta.com', '37.441660715225005', '24.94075655937195', 'https://natrice.gr/forites/veta.png');

--
-- Ευρετήρια για άχρηστους πίνακες
--

--
-- Ευρετήρια για πίνακα `signup`
--
ALTER TABLE `signup`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT για άχρηστους πίνακες
--

--
-- AUTO_INCREMENT για πίνακα `signup`
--
ALTER TABLE `signup`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
