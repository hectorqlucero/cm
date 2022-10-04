CREATE TABLE `frases` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `frase` text,
  `autor` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
