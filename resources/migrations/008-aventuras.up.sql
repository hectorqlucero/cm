CREATE TABLE `aventuras` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `aventura` text,
  `fecha` date DEFAULT NULL,
  `leader_email` varchar(100) DEFAULT NULL,
  `enlace` text,
  `enlacev` text,
  `cmt_id` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_aventuras_cmt` (`cmt_id`),
  CONSTRAINT `fk_aventuras_cmt` FOREIGN KEY (`cmt_id`) REFERENCES `cmt` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
