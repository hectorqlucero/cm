CREATE TABLE rodadas (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `titulo` varchar(100) DEFAULT NULL,
  `detalles` text DEFAULT NULL,
  `punto_reunion` text DEFAULT NULL,
  `nivel` char(1) DEFAULT NULL COMMENT 'P=Principiantes,M=Intermedio,A=Avanzado,T=Todos',
  `distancia` varchar(100) DEFAULT NULL,
  `velocidad` varchar(100) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `salida` time DEFAULT NULL,
  `leader` varchar(100) DEFAULT NULL,
  `leader_email` varchar(100) DEFAULT NULL,
  `repetir` char(1) DEFAULT NULL COMMENT 'T=Si,F=No',
  `last_updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
