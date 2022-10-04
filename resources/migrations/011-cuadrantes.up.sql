CREATE TABLE `cuadrantes` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `leader` varchar(100) DEFAULT NULL,
  `leader_phone` varchar(45) DEFAULT NULL,
  `leader_cell` varchar(45) DEFAULT NULL,
  `leader_email` varchar(100) DEFAULT NULL,
  `notes` text,
  `status` char(1) DEFAULT NULL COMMENT 'T=Activo,F=Inactivo',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
