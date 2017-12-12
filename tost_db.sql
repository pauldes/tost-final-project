-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 12, 2017 at 05:33 PM
-- Server version: 5.7.14
-- PHP Version: 7.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tost_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `id_event` mediumint(9) NOT NULL,
  `event_name` varchar(255) NOT NULL,
  `event_desc` varchar(2047) NOT NULL,
  `event_date` datetime NOT NULL,
  `id_event_cat` smallint(6) NOT NULL,
  `id_event_tag` smallint(6) NOT NULL,
  `id_place` mediumint(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `event_cat`
--

CREATE TABLE `event_cat` (
  `id_event_cat` smallint(6) NOT NULL,
  `event_cat_name` varchar(63) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `event_tag`
--

CREATE TABLE `event_tag` (
  `id_event_tag` smallint(6) NOT NULL,
  `event_tag_name` varchar(63) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `event_tag_belongs_to_cat`
--

CREATE TABLE `event_tag_belongs_to_cat` (
  `id_event_tag_bel_to_cat` smallint(6) NOT NULL,
  `id_event_tag` smallint(6) NOT NULL,
  `Id_event_cat` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `places`
--

CREATE TABLE `places` (
  `id_place` mediumint(9) NOT NULL,
  `place_name` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `google_place_id` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `place_categories` varchar(63) CHARACTER SET utf8mb4 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `places`
--

INSERT INTO `places` (`id_place`, `place_name`, `google_place_id`, `place_categories`) VALUES
(1, 'Barberousse', 'ChIJRaKS__vq9EcRccKWVXZ3sXY', 'Bar'),
(2, 'Oslow', 'ChIJAQCwF17q9EcRN817GaJoojg', 'Café,Brunch'),
(8, 'Aloha', 'ChIJ6_hMo__q9EcRhJhSxH47Y64', 'Bar'),
(9, 'Brasserie Georges', 'ChIJ9_SKPUvq9EcRsvhxDZsc158', 'Café'),
(14, 'Athina', 'ChIJgzfjCPzq9EcRO1sx6eTW3U4', 'Café,Brunch,');

-- --------------------------------------------------------

--
-- Table structure for table `place_tag`
--

CREATE TABLE `place_tag` (
  `id_place_tag` smallint(6) NOT NULL,
  `place_tag_name` varchar(63) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `place_tag_belongs_to_cat`
--

CREATE TABLE `place_tag_belongs_to_cat` (
  `id_place_tag_bel_to_cat` smallint(6) NOT NULL,
  `id_place_tag` smallint(6) NOT NULL,
  `id_place_cat` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `pro`
--

CREATE TABLE `pro` (
  `id_pro` mediumint(9) NOT NULL,
  `id_place` mediumint(9) NOT NULL,
  `pro_mail` varchar(63) NOT NULL,
  `pro_pwd` varchar(127) NOT NULL,
  `salt` varchar(63) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `userx`
--

CREATE TABLE `userx` (
  `id_user` mediumint(9) NOT NULL,
  `user_pseudo` varchar(255) NOT NULL,
  `user_mail` varchar(63) NOT NULL,
  `user_avatar` varchar(511) DEFAULT NULL,
  `user_pwd` varchar(127) NOT NULL,
  `user_salt` varchar(63) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `userx`
--

INSERT INTO `userx` (`id_user`, `user_pseudo`, `user_mail`, `user_avatar`, `user_pwd`, `user_salt`) VALUES
(6, 'maria', 'lola@lola.fr', NULL, '471b146b6d998b4266aadec8ee257a323ac216a6558a75c16b8dcd8920083da0', 'MQW8ta43');

-- --------------------------------------------------------

--
-- Table structure for table `user_interested_event`
--

CREATE TABLE `user_interested_event` (
  `id_user_intd_event` int(11) NOT NULL,
  `id_user` mediumint(9) NOT NULL,
  `id_event` mediumint(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `user_liked_places`
--

CREATE TABLE `user_liked_places` (
  `id_user_liked_place` int(11) NOT NULL,
  `id_user` mediumint(9) NOT NULL,
  `id_place` mediumint(9) NOT NULL,
  `user_like` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_liked_places`
--

INSERT INTO `user_liked_places` (`id_user_liked_place`, `id_user`, `id_place`, `user_like`) VALUES
(1, 6, 1, 1),
(2, 6, 8, 1),
(3, 6, 9, 1),
(8, 6, 14, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_recommendations`
--

CREATE TABLE `user_recommendations` (
  `id_user_recommendation` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_places` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user_recommendations`
--

INSERT INTO `user_recommendations` (`id_user_recommendation`, `id_user`, `id_places`) VALUES
(1, 6, '1,2');

-- --------------------------------------------------------

--
-- Table structure for table `user_used_event_tag`
--

CREATE TABLE `user_used_event_tag` (
  `id_user_used_event_tag` int(11) NOT NULL,
  `id_user` mediumint(9) NOT NULL,
  `id_event_tag` smallint(6) NOT NULL,
  `score` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `user_used_place_tag`
--

CREATE TABLE `user_used_place_tag` (
  `id_user_used_place_tag` int(11) NOT NULL,
  `id_user` mediumint(9) NOT NULL,
  `id_place_tag` smallint(6) NOT NULL,
  `score` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`id_event`);

--
-- Indexes for table `event_cat`
--
ALTER TABLE `event_cat`
  ADD PRIMARY KEY (`id_event_cat`),
  ADD UNIQUE KEY `event_cat_name` (`event_cat_name`);

--
-- Indexes for table `event_tag`
--
ALTER TABLE `event_tag`
  ADD PRIMARY KEY (`id_event_tag`),
  ADD UNIQUE KEY `event_tag_name` (`event_tag_name`);

--
-- Indexes for table `event_tag_belongs_to_cat`
--
ALTER TABLE `event_tag_belongs_to_cat`
  ADD PRIMARY KEY (`id_event_tag_bel_to_cat`);

--
-- Indexes for table `places`
--
ALTER TABLE `places`
  ADD PRIMARY KEY (`id_place`);

--
-- Indexes for table `place_tag`
--
ALTER TABLE `place_tag`
  ADD PRIMARY KEY (`id_place_tag`),
  ADD UNIQUE KEY `place_tag_name` (`place_tag_name`);

--
-- Indexes for table `place_tag_belongs_to_cat`
--
ALTER TABLE `place_tag_belongs_to_cat`
  ADD PRIMARY KEY (`id_place_tag_bel_to_cat`);

--
-- Indexes for table `pro`
--
ALTER TABLE `pro`
  ADD PRIMARY KEY (`id_pro`);

--
-- Indexes for table `userx`
--
ALTER TABLE `userx`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `user_mail` (`user_mail`);

--
-- Indexes for table `user_interested_event`
--
ALTER TABLE `user_interested_event`
  ADD PRIMARY KEY (`id_user_intd_event`);

--
-- Indexes for table `user_liked_places`
--
ALTER TABLE `user_liked_places`
  ADD PRIMARY KEY (`id_user_liked_place`);

--
-- Indexes for table `user_recommendations`
--
ALTER TABLE `user_recommendations`
  ADD PRIMARY KEY (`id_user_recommendation`),
  ADD UNIQUE KEY `id_user` (`id_user`);

--
-- Indexes for table `user_used_event_tag`
--
ALTER TABLE `user_used_event_tag`
  ADD PRIMARY KEY (`id_user_used_event_tag`);

--
-- Indexes for table `user_used_place_tag`
--
ALTER TABLE `user_used_place_tag`
  ADD PRIMARY KEY (`id_user_used_place_tag`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
  MODIFY `id_event` mediumint(9) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `event_cat`
--
ALTER TABLE `event_cat`
  MODIFY `id_event_cat` smallint(6) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `event_tag`
--
ALTER TABLE `event_tag`
  MODIFY `id_event_tag` smallint(6) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `event_tag_belongs_to_cat`
--
ALTER TABLE `event_tag_belongs_to_cat`
  MODIFY `id_event_tag_bel_to_cat` smallint(6) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `places`
--
ALTER TABLE `places`
  MODIFY `id_place` mediumint(9) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `place_tag`
--
ALTER TABLE `place_tag`
  MODIFY `id_place_tag` smallint(6) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `place_tag_belongs_to_cat`
--
ALTER TABLE `place_tag_belongs_to_cat`
  MODIFY `id_place_tag_bel_to_cat` smallint(6) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `pro`
--
ALTER TABLE `pro`
  MODIFY `id_pro` mediumint(9) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `userx`
--
ALTER TABLE `userx`
  MODIFY `id_user` mediumint(9) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `user_interested_event`
--
ALTER TABLE `user_interested_event`
  MODIFY `id_user_intd_event` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user_liked_places`
--
ALTER TABLE `user_liked_places`
  MODIFY `id_user_liked_place` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `user_recommendations`
--
ALTER TABLE `user_recommendations`
  MODIFY `id_user_recommendation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `user_used_event_tag`
--
ALTER TABLE `user_used_event_tag`
  MODIFY `id_user_used_event_tag` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user_used_place_tag`
--
ALTER TABLE `user_used_place_tag`
  MODIFY `id_user_used_place_tag` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
