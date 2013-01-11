-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 28, 2012 at 04:35 PM
-- Server version: 5.5.28
-- PHP Version: 5.3.10-1ubuntu3.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `IF26`
--

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `login` varchar(40) NOT NULL,
  `pwd` varchar(60) NOT NULL,
  `coef` int(11) NOT NULL DEFAULT '0',
  `date_debut` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `date_token` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `token` varchar(30) DEFAULT NULL,
  `IDGCM` text NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`login`, `pwd`, `coef`, `date_debut`, `date_token`, `token`, `IDGCM`) VALUES
('test.if26@gmail.com', 'ec474e93c9d613d12857758a8f5e534c39547329950ddbb731b4171.1', 0, '2012-12-28 15:34:31', '2012-12-28 15:34:31', '118519352850ddbc07d45c97.', 'APA91bELPeJTjIsTQXcg99M2LXVQA1u81yMWgnZQASiOZ45Q-CW9WNcFHtJ6rUqCLfeJqIEMidZdNJpDJgMdyR0axTstAEkZhIvrNdQMEVd6jeG3rvrhy9Z67k1akZPCt4fuhyIga-onGZD-gexe_r3MYsqO1Hd_Bw');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
