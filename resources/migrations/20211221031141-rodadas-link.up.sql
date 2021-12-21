CREATE TABLE rodadas_link (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `rodadas_id` int(11) UNSIGNED NOT NULL,
  `user` varchar(200) DEFAULT NULL,
  `comentarios` text DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `asistir` char(1) DEFAULT 'T' COMMENT 'T=Si,F=No',
  `last_updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  KEY `fk_rodadas_link_rodadas` (`rodadas_id`),
  CONSTRAINT `fk_rodadas_link_rodadas` FOREIGN KEY (`rodadas_id`) REFERENCES `rodadas` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
