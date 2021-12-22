CREATE TABLE talleres (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nombre` VARCHAR(200) DEFAULT NULL,
  `direccion` VARCHAR(200) DEFAULT NULL,
  `telefono` VARCHAR(100) DEFAULT NULL,
  `horarios` TEXT DEFAULT NULL,
  `sitio` TEXT DEFAULT NULL,
  `direcciones` TEXT DEFAULT NULL,
  `historia` TEXT DEFAULT NULL,
  `last_updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
