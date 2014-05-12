-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 13, 2014 at 08:38 AM
-- Server version: 5.5.24-log
-- PHP Version: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `wap`
--

-- --------------------------------------------------------

--
-- Table structure for table `friend`
--

CREATE TABLE IF NOT EXISTS `friend` (
  `username` varchar(20) COLLATE utf8_turkish_ci NOT NULL,
  `friend_username` varchar(20) COLLATE utf8_turkish_ci NOT NULL,
  PRIMARY KEY (`username`,`friend_username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Dumping data for table `friend`
--

INSERT INTO `friend` (`username`, `friend_username`) VALUES
('aysan', 'eates'),
('aysan', 'gtezcan'),
('aysan', 'silence'),
('aysan', 'tkiziloren'),
('eates', 'silence'),
('eates', 'tkiziloren'),
('gtezcan', 'tkiziloren'),
('silence', 'eates'),
('silence', 'gtezcan'),
('silence', 'tkiziloren');

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE IF NOT EXISTS `location` (
  `username` varchar(20) COLLATE utf8_turkish_ci NOT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Dumping data for table `location`
--

INSERT INTO `location` (`username`, `latitude`, `longitude`, `update_time`) VALUES
('aysan', 39.892356666666664, 32.78517, '2012-10-04 11:24:55'),
('eates', 39.899481, 32.780637, '2012-09-07 02:18:00'),
('gtezcan', 39.895543, 32.778985, '2012-09-06 23:15:00'),
('silence', 39.892205, 32.780082, '2012-09-07 03:11:33'),
('tkiziloren', 39.893481, 32.791479, '2012-09-06 21:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE IF NOT EXISTS `profile` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) COLLATE utf8_turkish_ci NOT NULL,
  `name` varchar(20) COLLATE utf8_turkish_ci NOT NULL,
  `surname` varchar(20) COLLATE utf8_turkish_ci NOT NULL,
  `telephone` varchar(10) COLLATE utf8_turkish_ci DEFAULT NULL,
  `email` varchar(20) COLLATE utf8_turkish_ci DEFAULT NULL,
  PRIMARY KEY (`_id`),
  UNIQUE KEY `kullanici_adi` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci AUTO_INCREMENT=8 ;

--
-- Dumping data for table `profile`
--

INSERT INTO `profil` (`_id`, `username`, `name`, `surname`, `telephone`, `email`) VALUES
(1, 'silence', 'Aysan Ethem', 'Narman', '5335333333', 'silence@gmail.com'),
(2, 'tkiziloren', 'Tevfik', 'Kızılören', '', ''),
(3, 'gtezcan', 'Gülden', 'Tezcan', ' ', ' '),
(4, 'eates', 'Emel', 'Ateş', '5335333333', 'emel@yahoo.com'),
(6, 'fisler', 'Fatih', 'İşler', '5315303030', 'fatih@gmail.com'),
(7, 'aysan', 'aysan', 'ethem', '', 'kodlab@kodlab.com');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
