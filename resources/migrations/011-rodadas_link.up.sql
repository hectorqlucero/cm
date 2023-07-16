CREATE TABLE `rodadas_link` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `rodadas_id` int(10) unsigned NOT NULL,
  `user` varchar(200) DEFAULT NULL,
  `comentarios` text,
  `email` varchar(100) DEFAULT NULL,
  `asistir` char(1) DEFAULT 'T' COMMENT 'T=Si,F=No',
  PRIMARY KEY (`id`),
  KEY `fk_rodadas_link_rodadas` (`rodadas_id`),
  CONSTRAINT `fk_rodadas_link_rodadas` FOREIGN KEY (`rodadas_id`) REFERENCES `rodadas` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
