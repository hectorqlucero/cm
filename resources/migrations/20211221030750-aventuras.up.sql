CREATE TABLE aventuras (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `aventura` text DEFAULT NULL,
  `leader_email` varchar(200) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `enlace` text DEFAULT NULL,
  `last_updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
