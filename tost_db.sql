-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 11, 2018 at 12:17 AM
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
  `id` mediumint(9) NOT NULL,
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
  `id` smallint(6) NOT NULL,
  `event_cat_name` varchar(63) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `event_tag`
--

CREATE TABLE `event_tag` (
  `id` smallint(6) NOT NULL,
  `event_tag_name` varchar(63) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `event_tag_belongs_to_cat`
--

CREATE TABLE `event_tag_belongs_to_cat` (
  `id` smallint(6) NOT NULL,
  `id_event_tag` smallint(6) NOT NULL,
  `Id_event_cat` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `places`
--

CREATE TABLE `places` (
  `id` mediumint(9) NOT NULL,
  `place_name` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `google_place_id` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `place_categories` varchar(63) CHARACTER SET utf8mb4 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `places`
--

INSERT INTO `places` (`id`, `place_name`, `google_place_id`, `place_categories`) VALUES
(1, 'Barberousse', 'ChIJRaKS__vq9EcRccKWVXZ3sXY', 'Bar'),
(2, 'Oslow', 'ChIJAQCwF17q9EcRN817GaJoojg', 'Café,Brunch'),
(8, 'Aloha', 'ChIJ6_hMo__q9EcRhJhSxH47Y64', 'Bar'),
(9, 'Brasserie Georges', 'ChIJ9_SKPUvq9EcRsvhxDZsc158', 'Café'),
(14, 'Athina', 'ChIJgzfjCPzq9EcRO1sx6eTW3U4', 'Café,Brunch,'),
(15, 'Mademoiselle Rêve', 'ChIJgyqsLVzq9EcR3BSQelHZ0m8', 'Bar,'),
(16, 'Le MoMa', 'ChIJfc4loP_q9EcR-a496XWLZ90', 'Bar,'),
(17, 'Café J\'adore', 'ChIJacwKnf3q9EcR3ey_K-pyAWs', 'Café,'),
(18, 'Slake Coffee House', 'ChIJzTyCSFTq9EcRvLMegp9RRFA', 'Café,'),
(19, 'The GentleCat - Bar cats', 'ChIJW7u1RrPr9EcRLgkNWC-LFYI', 'Café,'),
(20, 'Le Bieristan', 'ChIJKaAdPS7A9EcRfPHngQl7wNo', 'Bar,'),
(21, 'Candy Cookie Boulevard', 'ChIJAcvZsf_q9EcRXrSoUV0bPAQ', 'Café,'),
(22, 'Puzzle Cafe', 'ChIJ5VH_Xv_q9EcRXJ8zq7oOPkE', 'Café'),
(23, 'COMME A LA MAISON Coffee Shop', 'ChIJMy0DYUbq9EcRIfg-CYIQ2fg', 'Café,Brunch'),
(27, 'Barge, La Marquise', 'ChIJ3X0PI1rq9EcR3cfk3kuxhns', 'Bar'),
(28, 'Comme à La Maison', 'ChIJaYeQikbq9EcRKlTEnM3YwR4', 'Café,Brunch');

-- --------------------------------------------------------

--
-- Table structure for table `place_tag`
--

CREATE TABLE `place_tag` (
  `id` smallint(6) NOT NULL,
  `place_tag_name` varchar(63) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `place_tag`
--

INSERT INTO `place_tag` (`id`, `place_tag_name`) VALUES
(42, 'Afterworks'),
(16, 'Atmosphère intime'),
(26, 'Bar à shooter'),
(25, 'Bar bières'),
(27, 'Bar dansant'),
(11, 'Bières locales'),
(15, 'Bio'),
(37, 'Boissons à volonté'),
(45, 'Brunch à volonté'),
(35, 'Bubble Tea'),
(7, 'Cadre insolite'),
(46, 'Café-théâtre'),
(24, 'Cave à vin'),
(47, 'CB sans minimum'),
(36, 'Chaï Latte'),
(44, 'Chocolat chaud réputé'),
(23, 'Cocktails originaux'),
(19, 'Déco branchée'),
(33, 'Dégustation de rhum'),
(34, 'Dégustation de whisky'),
(21, 'Diffusion de matchs'),
(3, 'Etudier et travailler'),
(41, 'Family-friendly'),
(18, 'Forfait à la durée'),
(43, 'Happy hours'),
(22, 'Irish Pub'),
(32, 'Lait de soja ou d\'avoine'),
(17, 'Latino et Salsa'),
(29, 'Lunch'),
(5, 'Music Live et Concerts'),
(30, 'Pâtisseries'),
(8, 'Péniche'),
(31, 'Petit-déjeuner'),
(38, 'Planches apéro'),
(4, 'Pour amateurs de café'),
(2, 'Prises'),
(9, 'Rooftop'),
(13, 'Sans gluten'),
(39, 'Self-service'),
(10, 'Smoothies et jus de fruit pressés'),
(40, 'Tapas'),
(20, 'Terrasse'),
(28, 'Underground'),
(12, 'Vegan'),
(14, 'Végétarien'),
(6, 'Vue de qualité'),
(1, 'Wifi');

-- --------------------------------------------------------

--
-- Table structure for table `place_tag_belongs_to_cat`
--

CREATE TABLE `place_tag_belongs_to_cat` (
  `id` smallint(6) NOT NULL,
  `id_place_tag` smallint(6) NOT NULL,
  `id_place_cat` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `pro`
--

CREATE TABLE `pro` (
  `id` mediumint(9) NOT NULL,
  `id_place` mediumint(9) NOT NULL,
  `pro_mail` varchar(63) NOT NULL,
  `pro_pwd` varchar(127) NOT NULL,
  `salt` varchar(63) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tag_used_for_places`
--

CREATE TABLE `tag_used_for_places` (
  `id` int(11) NOT NULL,
  `id_place_tag` smallint(6) NOT NULL,
  `id_place` mediumint(9) NOT NULL,
  `score` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tag_used_for_places`
--

INSERT INTO `tag_used_for_places` (`id`, `id_place_tag`, `id_place`, `score`) VALUES
(1, 11, 22, '2'),
(2, 43, 27, '1'),
(3, 6, 27, '1'),
(4, 3, 28, '1'),
(5, 4, 28, '1'),
(6, 2, 28, '1'),
(7, 30, 28, '1'),
(8, 31, 28, '1'),
(9, 1, 28, '1');

-- --------------------------------------------------------

--
-- Table structure for table `userx`
--

CREATE TABLE `userx` (
  `id` mediumint(9) NOT NULL,
  `user_pseudo` varchar(255) NOT NULL,
  `user_mail` varchar(63) NOT NULL,
  `user_avatar` varchar(511) DEFAULT NULL,
  `user_pwd` varchar(127) NOT NULL,
  `user_salt` varchar(63) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `userx`
--

INSERT INTO `userx` (`id`, `user_pseudo`, `user_mail`, `user_avatar`, `user_pwd`, `user_salt`) VALUES
(6, 'maria', 'lola@lola.fr', NULL, '471b146b6d998b4266aadec8ee257a323ac216a6558a75c16b8dcd8920083da0', 'MQW8ta43'),
(15, 'alicialbt', 'alicia@gmail.com', NULL, 'aaa822e5edd5107043fba7c95548b43e8232e6a252d1d2ac9d64cfc4a21e13d1', 'GQv0iHZY'),
(16, 'soazoen', 'soafara@gmail.com', NULL, '5a2f909e3f83b015423be5b2f52509c545c217b5ea5599755d3af4019841a7a7', 'n62p75Gi'),
(17, 'pauldes', 'pau@gmail.com', NULL, 'f4fa0b3ca3a5b830814ebc682f6da1f54a078da31273341952e24fad92359710', 'xazf9pF3'),
(18, 'iris', 'iris@gmail.com', NULL, 'a43adc65141d6a2817689d9d381160551e91220024a16c86da848dc22f6d8a36', 'FeRCXv5A'),
(19, 'martin', 'martin@gmail.com', NULL, 'aec1a84648f6999fd6a5dff375d8603d8b96dba255c3bae3d9eccab17e4b7a19', 'nVIk2u4b'),
(20, 'berenice', 'berenice@gmail.com', NULL, '2e2040968bf626f0a07e545e5fe7dcb1085881d8cbc2f252e7f20f77ac84d21a', 'Ab5gyscS'),
(21, 'emilie', 'emilie@gmail.com', NULL, 'e5237ca2a35501e6c7753fc49d5888b7247fc2991cfec80649db80e9014fbb98', 'RndtNzWP'),
(22, 'vincent', 'vincent@gmail.com', NULL, '3bbde9a9ebcf85720b3931ecbd8634e20d9a3c59b83af5215ac83de80855967c', 'x6fPTald'),
(23, 'razvan', 'razvan@gmail.com', NULL, '61dc0eef7b2c544198240c3d0053552e95282acd273837f4b5f54e5aa183723d', 'nohPkKiV');

-- --------------------------------------------------------

--
-- Table structure for table `user_groups`
--

CREATE TABLE `user_groups` (
  `id` int(11) NOT NULL,
  `owner_id` int(11) NOT NULL,
  `group_name` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL,
  `members_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user_groups`
--

INSERT INTO `user_groups` (`id`, `owner_id`, `group_name`, `members_id`) VALUES
(1, 6, 'Les amis', '17,19');

-- --------------------------------------------------------

--
-- Table structure for table `user_interested_event`
--

CREATE TABLE `user_interested_event` (
  `id` int(11) NOT NULL,
  `id_user` mediumint(9) NOT NULL,
  `id_event` mediumint(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `user_liked_places`
--

CREATE TABLE `user_liked_places` (
  `id` int(11) NOT NULL,
  `id_user` mediumint(9) NOT NULL,
  `id_place` mediumint(9) NOT NULL,
  `user_like` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_liked_places`
--

INSERT INTO `user_liked_places` (`id`, `id_user`, `id_place`, `user_like`) VALUES
(1, 6, 1, 1),
(2, 6, 8, 1),
(3, 6, 9, 1),
(8, 6, 14, 1),
(9, 18, 15, 1),
(10, 18, 16, 1),
(11, 19, 16, 1),
(12, 19, 17, 1),
(13, 19, 18, 1),
(14, 20, 17, 1),
(15, 20, 18, 3),
(16, 20, 15, 1),
(17, 20, 19, 1),
(18, 21, 20, 1),
(19, 21, 19, 1),
(20, 22, 17, 1),
(21, 22, 21, 1),
(22, 15, 21, 1),
(23, 15, 16, -1),
(24, 18, 1, -1),
(25, 18, 20, -1),
(26, 21, 9, -1),
(27, 15, 22, 1),
(28, 23, 1, 1),
(29, 15, 23, 1),
(35, 15, 27, 1),
(36, 15, 28, 3);

-- --------------------------------------------------------

--
-- Table structure for table `user_recommendations`
--

CREATE TABLE `user_recommendations` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_places` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user_recommendations`
--

INSERT INTO `user_recommendations` (`id`, `id_user`, `id_places`) VALUES
(1, 6, '15,16,19,2,17,18,21,22,23,20'),
(2, 15, '15,18,17,2,8,9,14,19,1,20'),
(3, 16, '1,2,8,9,14,15,16,17,18,19,20,21,22,23'),
(4, 17, '1,2,8,9,14,15,16,17,18,19,20,21,22,23'),
(5, 18, '22,18,8,14,21,23,17,19,2,9'),
(6, 19, '22,23,15,19,21,2,8,9,14,1,20'),
(7, 20, '16,21,2,8,14,22,23,20,1,9'),
(8, 21, '18,8,14,16,15,17,2,21,22,23,1'),
(9, 22, '22,18,15,19,23,1,2,8,9,14,20,16'),
(10, 23, '15,16,8,9,14,2,17,18,19,21,22,23,20');

-- --------------------------------------------------------

--
-- Table structure for table `user_used_event_tag`
--

CREATE TABLE `user_used_event_tag` (
  `id` int(11) NOT NULL,
  `id_user` mediumint(9) NOT NULL,
  `id_event_tag` smallint(6) NOT NULL,
  `score` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `user_used_place_tag`
--

CREATE TABLE `user_used_place_tag` (
  `id` int(11) NOT NULL,
  `id_user` mediumint(9) NOT NULL,
  `id_place_tag` smallint(6) NOT NULL,
  `score` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_used_place_tag`
--

INSERT INTO `user_used_place_tag` (`id`, `id_user`, `id_place_tag`, `score`) VALUES
(1, 15, 31, '1'),
(2, 15, 30, '1'),
(3, 15, 1, '1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `event_cat`
--
ALTER TABLE `event_cat`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `event_cat_name` (`event_cat_name`);

--
-- Indexes for table `event_tag`
--
ALTER TABLE `event_tag`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `event_tag_name` (`event_tag_name`);

--
-- Indexes for table `event_tag_belongs_to_cat`
--
ALTER TABLE `event_tag_belongs_to_cat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `places`
--
ALTER TABLE `places`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `place_tag`
--
ALTER TABLE `place_tag`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `place_tag_name` (`place_tag_name`);

--
-- Indexes for table `place_tag_belongs_to_cat`
--
ALTER TABLE `place_tag_belongs_to_cat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pro`
--
ALTER TABLE `pro`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tag_used_for_places`
--
ALTER TABLE `tag_used_for_places`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `userx`
--
ALTER TABLE `userx`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_mail` (`user_mail`);

--
-- Indexes for table `user_groups`
--
ALTER TABLE `user_groups`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_interested_event`
--
ALTER TABLE `user_interested_event`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_liked_places`
--
ALTER TABLE `user_liked_places`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_recommendations`
--
ALTER TABLE `user_recommendations`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_user` (`id_user`);

--
-- Indexes for table `user_used_event_tag`
--
ALTER TABLE `user_used_event_tag`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_used_place_tag`
--
ALTER TABLE `user_used_place_tag`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
  MODIFY `id` mediumint(9) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `event_cat`
--
ALTER TABLE `event_cat`
  MODIFY `id` smallint(6) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `event_tag`
--
ALTER TABLE `event_tag`
  MODIFY `id` smallint(6) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `event_tag_belongs_to_cat`
--
ALTER TABLE `event_tag_belongs_to_cat`
  MODIFY `id` smallint(6) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `places`
--
ALTER TABLE `places`
  MODIFY `id` mediumint(9) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
--
-- AUTO_INCREMENT for table `place_tag`
--
ALTER TABLE `place_tag`
  MODIFY `id` smallint(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;
--
-- AUTO_INCREMENT for table `place_tag_belongs_to_cat`
--
ALTER TABLE `place_tag_belongs_to_cat`
  MODIFY `id` smallint(6) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `pro`
--
ALTER TABLE `pro`
  MODIFY `id` mediumint(9) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tag_used_for_places`
--
ALTER TABLE `tag_used_for_places`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `userx`
--
ALTER TABLE `userx`
  MODIFY `id` mediumint(9) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
--
-- AUTO_INCREMENT for table `user_groups`
--
ALTER TABLE `user_groups`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `user_interested_event`
--
ALTER TABLE `user_interested_event`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user_liked_places`
--
ALTER TABLE `user_liked_places`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;
--
-- AUTO_INCREMENT for table `user_recommendations`
--
ALTER TABLE `user_recommendations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `user_used_event_tag`
--
ALTER TABLE `user_used_event_tag`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user_used_place_tag`
--
ALTER TABLE `user_used_place_tag`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
