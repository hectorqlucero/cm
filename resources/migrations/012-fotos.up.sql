CREATE TABLE `fotos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `enlace` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
