CREATE TABLE `frases` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `frase` text,
  `autor` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
