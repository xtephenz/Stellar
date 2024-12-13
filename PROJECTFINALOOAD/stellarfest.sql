-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 12, 2024 at 07:07 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stellarfest`
--

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

CREATE TABLE `events` (
  `event_id` varchar(11) NOT NULL,
  `event_name` varchar(50) NOT NULL,
  `event_date` varchar(11) NOT NULL,
  `event_location` varchar(50) NOT NULL,
  `event_description` varchar(200) NOT NULL,
  `organizer_id` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`event_id`, `event_name`, `event_date`, `event_location`, `event_description`, `organizer_id`) VALUES
('EVT002', 'Bebe Party!', '11/12/2025', 'Elibam', 'Abunima', 'USR001'),
('EVT004', 'IbnuAbnu', '11/11/2025', 'Armubie', 'Eliba', 'USR001'),
('EVT005', 'Season Greetings', '22/12/2024', 'aefaefaefae', 'aefea', 'USR012');

-- --------------------------------------------------------

--
-- Table structure for table `invitations`
--

CREATE TABLE `invitations` (
  `invitation_id` varchar(11) NOT NULL,
  `event_id` varchar(11) NOT NULL,
  `user_id` varchar(11) NOT NULL,
  `invitation_status` varchar(50) NOT NULL,
  `invitation_role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `invitations`
--

INSERT INTO `invitations` (`invitation_id`, `event_id`, `user_id`, `invitation_status`, `invitation_role`) VALUES
('INV001', 'EVT001', 'USR007', 'Accepted', 'Guest'),
('INV002', 'EVT001', 'USR008', 'Accepted', 'Vendor'),
('INV003', 'EVT001', 'USR009', 'Pending', 'Vendor'),
('INV004', 'EVT001', 'USR010', 'Accepted', 'Guest'),
('INV005', 'EVT005', 'USR008', 'Pending', 'Vendor'),
('INV006', 'EVT005', 'USR007', 'Pending', 'Guest'),
('INV007', 'EVT005', 'USR010', 'Accepted', 'Guest'),
('INV008', 'EVT005', 'USR009', 'Pending', 'Vendor');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` varchar(11) NOT NULL,
  `user_email` varchar(50) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `user_password` varchar(50) NOT NULL,
  `user_role` varchar(50) NOT NULL,
  `product_name` varchar(11) DEFAULT NULL,
  `product_description` varchar(224) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_email`, `user_name`, `user_password`, `user_role`, `product_name`, `product_description`) VALUES
('USR001', 'ADMIN@email.com', 'admin', 'admin', 'Admin', '', ''),
('USR004', 'KamalaHarris@email.com', 'Kamala Harris', 'KamalaHarris123', 'Admin', '', ''),
('USR006', 'AdeleDazeem@gmail.com', 'Idina Menzel', 'IdinaQueendom', 'Admin', '', ''),
('USR007', 'Goober@gmail.com', 'goobie', 'goobier2013', 'Guest', '', ''),
('USR008', 'Vendor@email.com', 'Vendor', 'VendorTest123', 'Vendor', 'halo', 'bandung'),
('USR010', 'iamaguest@email.com', 'guest', 'guest123', 'Guest', '', ''),
('USR011', 'guest@email.com', 'guest123', 'guest123', 'Guest', '', ''),
('USR012', 'eventOrganizer@email.com', 'event organizer', 'event', 'Event Organizer', NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`event_id`);

--
-- Indexes for table `invitations`
--
ALTER TABLE `invitations`
  ADD PRIMARY KEY (`invitation_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
