CREATE TABLE cuadrantes (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(100) DEFAULT NULL,
  `leader` varchar(100) DEFAULT NULL,
  `leader_phone` varchar(45) DEFAULT NULL,
  `leader_cell` varchar(45) DEFAULT NULL,
  `leader_email` varchar(200) DEFAULT NULL,
  `notes` text DEFAULT NULL,
  `status` char(1) DEFAULT NULL COMMENT 'T=Activo,F=Inactivo',
  `last_updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
