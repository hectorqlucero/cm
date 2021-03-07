(ns sk.models.cdb
  (:require [sk.models.crud :refer :all]
            [noir.util.crypt :as crypt]))


;; Start users table
(def users-sql
  "CREATE TABLE users (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  lastname varchar(45) DEFAULT NULL,
  firstname varchar(45) DEFAULT NULL,
  username varchar(45) DEFAULT NULL,
  password TEXT DEFAULT NULL,
  dob varchar(45) DEFAULT NULL,
  cell varchar(45) DEFAULT NULL,
  phone varchar(45) DEFAULT NULL,fax varchar(45) DEFAULT NULL,
  email varchar(100) DEFAULT NULL,
  level char(1) DEFAULT NULL COMMENT 'A=Administrador,U=Usuario,S=Sistema',
  active char(1) DEFAULT NULL COMMENT 'T=Active,F=Not active'
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8")

(def users-rows
  [{:lastname  "Lucero"
    :firstname "Hector"
    :username  "hectorqlucero@gmail.com"
    :password  (crypt/encrypt "elmo1200")
    :dob       "1957-02-07"
    :email     "hectorqlucero@gmail.com"
    :level     "S"
    :active    "T"}])
;; End users table

;; Start cuadrantes table
(def cuadrantes-sql
  "
  CREATE TABLE `cuadrantes` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `leader` varchar(100) DEFAULT NULL,
  `leader_phone` varchar(45) DEFAULT NULL,
  `leader_cell` varchar(45) DEFAULT NULL,
  `leader_email` varchar(100) DEFAULT NULL,
  `notes` text,
  `status` char(1) DEFAULT NULL COMMENT 'T=Activo,F=Inactivo',
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ")
;; End cuadrantes table

;; Start eventos
(def eventos-sql
  "
  CREATE TABLE `eventos` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `titulo` varchar(200) DEFAULT NULL,
  `detalles` text,
  `lugar` text,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `organiza` varchar(100) DEFAULT NULL,
  `imagen` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ")
;; End eventos

;; Start rodadas
(def rodadas-sql
  "
  CREATE TABLE `rodadas` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `titulo` varchar(20) DEFAULT NULL,
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
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8")
;; End rodadas

;; Start aventuras
(def aventuras-sql
  "
  CREATE TABLE aventuras (
  id int(11) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nombre varchar(100) DEFAULT NULL,
  aventura text,
  fecha date DEFAULT NULL
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8
  ")
;; End aventuras

;; Start rodadas_link
(def rodadas_link-sql
  "
  CREATE TABLE `rodadas_link` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `rodadas_id` int(11) unsigned NOT NULL,
  `user` varchar(200) DEFAULT NULL,
  `comentarios` text,
  `email` varchar(100) DEFAULT NULL,
  `asistir` char(1) DEFAULT 'T' COMMENT 'T=Si,F=No',
  PRIMARY KEY (`id`),
  KEY `fk_rodadas_link_rodadas` (`rodadas_id`),
  CONSTRAINT `fk_rodadas_link_rodadas` FOREIGN KEY (`rodadas_id`) REFERENCES `rodadas` (`id`) ON DELETE CASCADE
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8")
;; End rodadas_link

;; Start talleres
(def talleres-sql
  "
  CREATE TABLE `talleres` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(200) DEFAULT NULL,
  `direccion` varchar(200) DEFAULT NULL,
  `telefono` varchar(100) DEFAULT NULL,
  `horarios` text,
  `sitio` varchar(200) DEFAULT NULL,
  `direcciones` text,
  `historia` text,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8")
;; End talleres

(defn create-database []
  "Create database tables and default admin users
  Note: First create the database on MySQL with any client"
  (Query! db cuadrantes-sql)
  (Query! db eventos-sql)
  (Query! db rodadas-sql)
  (Query! db rodadas_link-sql)
  (Query! db aventuras-sql)
  (Query! db talleres-sql)
  (Query! db users-sql)
  (Query! db "LOCK TABLES users WRITE;")
  (Insert-multi db :users users-rows)
  (Query! db "UNLOCK TABLES;"))

(defn reset-database []
  "Removes existing tables and re-creates them"
  (Query! db "DROP table IF EXISTS cuadrantes")
  (Query! db "DROP table IF EXISTS eventos")
  (Query! db "DROP table IF EXISTS rodadas_link")
  (Query! db "DROP table IF EXISTS rodadas")
  (Query! db "DROP table IF EXISTS aventuras")
  (Query! db "DROP table IF EXISTS talleres")
  (Query! db "DROP table IF EXISTS users")
  (Query! db cuadrantes-sql)
  (Query! db eventos-sql)
  (Query! db rodadas-sql)
  (Query! db rodadas_link-sql)
  (Query! db aventuras-sql)
  (Query! db talleres-sql)
  (Query! db users-sql)
  (Query! db "LOCK TABLES users WRITE;")
  (Insert-multi db :users users-rows)
  (Query! db "UNLOCK TABLES;"))

(defn migrate []
  "Migrate by the seat of my pants"
  (Query! db "DROP TABLE IF EXISTS aventuras")
  (Query! db aventuras-sql))
;;(migrate)
