CREATE TABLE eventos (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `titulo` varchar(200) DEFAULT NULL,
  `detalles` text DEFAULT NULL,
  `lugar` text DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `organiza` varchar(200) DEFAULT NULL,
  `imagen` text DEFAULT NULL,
  `last_updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
