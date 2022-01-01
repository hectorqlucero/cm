CREATE TABLE `aventuras` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `aventura` text,
  `fecha` date DEFAULT NULL,
  `leader_email` varchar(100) DEFAULT NULL,
  `enlace` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
