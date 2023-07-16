CREATE TABLE `rodadas` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) DEFAULT NULL,
  `detalles` text,
  `punto_reunion` text,
  `nivel` char(1) DEFAULT NULL COMMENT 'P=Principiantes,M=Intermedio,A=Avanzado,T=Todos',
  `distancia` varchar(100) DEFAULT NULL,
  `velocidad` varchar(100) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `salida` time DEFAULT NULL,
  `leader` varchar(100) DEFAULT NULL,
  `leader_email` varchar(100) DEFAULT NULL,
  `repetir` char(1) DEFAULT NULL COMMENT 'T=Si,F=No',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
