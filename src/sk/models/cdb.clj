(ns sk.models.cdb
  (:require [noir.util.crypt :as crypt]
            [sk.models.crud :refer [db Insert-multi Query!]]))

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
  aventura text,
  leader_email varchar(100) DEFAULT NULL,
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

;; Start fotos
(def fotos-sql
  "
  CREATE TABLE `fotos` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `enlace` text,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8")
;; End fotos

;; Start frases
(def frases-sql
  "
  CREATE TABLE `frases` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `frase` text DEFAULT NULL,
  `autor` varchar(200),
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8")

(def frases-rows
  [{:frase "Nada se compara con el simple placer de andar en bicicleta."
    :autor "John F. Kennedy"}
   {:frase "Piense en las bicicletas como un arte montable que puede salvar el mundo."
    :autor "Grant Petersen"}
   {:frase "Cuando me duelen las piernas, digo:`!Cállense piernas! !Hagan lo que te digo que hagan!`."
    :autor "Jens Voigt"}
   {:frase "Un paseo en bicicleta por el mundo comienza con un solo golpe de pedal."
    :autor "Scott Stoll"}
   {:frase "Las bicicletas son las especies indicadoras de una comunidad, como los mariscos en una bahia."
    :autor "P. Martin Scott"}
   {:frase "Para mi no importa si está lloviendo o si brilla el sol o lo que sea: mientras conduzca una bicicleta, sé que soy el tipo más afortunado del mundo."
    :autor "Mark Cavendish"}
   {:frase "Las bicicletas pueden cambiar, pero el ciclismo es intemporal."
    :autor "Zapata Espinoza"}
   {:frase "La bicicleta es la invención más noble de la humanidad."
    :autor "William Saroyan"}
   {:frase "Un paseo en bicicleta es un escape de la tristeza."
    :autor "James E. Starrs"}
   {:frase "Habrá muchos carriles para las bicicletas en una Utopia."
    :autor "H.G. Wells"}
   {:frase "Lo que más me gusta hacer es andar en bicicleta. Yo paseo en bicicletas de carretera. Para mí es una meditación en movimiento."
    :autor "Robin Williams"}
   {:frase "Pedalea mucho o poco, largo o corto pero pedalea."
    :autor "Eddy Merckx"}
   {:frase "Una bicicleta de montaña es como tu amigo. Una bicicleta de carretera es como tu amante."
    :autor "Sean Coffey"}
   {:frase "No puedes ser bueno quedándote en casa. Si quieres volverte rápido, tienes que ir donde están los chicos rápidos."
    :autor "Steve Larsen"}
   {:frase "Al igual que los perros, las bicicletas son catalizadores sociales que atraen a una categoría superior de personas."
    :autor "Chip Brown"}
   {:frase "Amo la bicicleta. Siempre lo hice. No puedo pensar en un ser humano decente, hombre o mujer, joven o viejo, santo o pecador, que pueda resistirse a la bicicleta."
    :autor "William Saroyan"}
   {:frase "Las bicicletas son casi tan buenas como las guitarras para conocer chicas."
    :autor "Bob Weir"}
   {:frase "La bicicleta es un vehiculo curioso. Su pasajero es su motor."
    :autor "John Howard"}
   {:frase "Estrellarse es parte del ciclismo como el llanto es parte del amor."
    :autor "Johan Museeuw"}
   {:frase "Aprende a andar en bicicleta. No te arrepentirás si vives."
    :autor "Mark Twain"}
   {:frase "La carrera es ganada por el ciclista que puede sufrir más."
    :autor "Eddy Merckx"}
   {:frase "Dale a un hombre un pescado y lo aliméntaras por un día. Enséñale a un hombre a pescar y lo aliméntaras para toda la vida. Enséñale a un hombre a andar en bicicleta y se dará cuenta de que la pesca es estúpida y aburrida."
    :autor "Desmond Tutu"}
   {:frase "Qué tienen en común el ciclsmo y el vino? Tienen en común que trabajando todos los dias en ello y, sobre todo, con mucha constancia y empeño, tanto el ciclista como los vinos mejoran con el paso del tiempo."
    :autor "Miguel Induráin"}
   {:frase "La buena moral en el ciclismo proviene de unas buenas piernas."
    :autor "Sean Yates"}
   {:frase "Es lo desconocido a la vuelta de la esquina lo que hace girar mis ruedas."
    :autor "Heinz Stucke"}
   {:frase "Es montando una bicicleta que conoces mejor los contornos de un país, ya que tienes que sudar las pendientes de las colinas y las bajadas de la costa."
    :autor "Ernest Hemingway"}
   {:frase "Montar en bicicleta es la mejor droga antidepresiva y solo tiene buenos efectos secundarios."
    :autor "Gunnar Rempel"}
   {:frase "El ser humano y la bicicleta constituyen la síntesis perfecta de cuerpo y máquina."
    :autor "Richard Ballantine"}
   {:frase "Nunca es más fácil, simplemente te vuelves más rápido."
    :autor "Greg LeMond"}
   {:frase "La melancolía es incompatible con la bicicleta."
    :autor "James E. Starrs"}
   {:frase "Uno de los dias más importantes de mi vida fue cuando aprendí a andar en bicicleta."
    :autor "Michael Palin"}
   {:frase "La vida es como una bicicleta de 10 velocidades. La mayoria de nosotros tenemos engranajes que nunca utilizamos."
    :autor "Charles M. Schulz"}
   {:frase "El ciclismo no es un juego, es un deporte. Difícil, duro y desagradable, y requiere grandes sacrificios. Uno juega al fútbol, tenis o hockey. Uno no juega en el ciclismo."
    :autor "Jean de Gribaldy"}
   {:frase "Quien consigue hacerse con el dominio de la bicicleta, podrá ganar el dominio de la vida."
    :autor "Susan B. Anthony"}
   {:frase "Soy un fanático de las bicicletas; montar la bicicleta tiene un cierto romance. Ellas on hermosas de gran utilidad."
    :autor "Dave Eggers"}
   {:frase "Cuando el dia se vuelva oscuro, cuando el trabajo parezca monótono, cuando resulte difícil conservar la esperanza, simplemente sube a una bicicleta y date un paseo por la carretera, sin pensar en nada más."
    :autor "Arthur Conan Doyle"}
   {:frase "Me parece que comprar una bicicleta es una buena manera de mantenerse en contacto con la gente."
    :autor "Jan Chipchase"}
   {:frase "Abraza tu sudor. Es tu esencia y tu emancipación."
    :autor "Kristin Armstron"}
   {:frase "La tolerancia requiere el mismo esfuerzo del cerebro que mantener el equilibrio sobre una bicicleta."
    :autor "Helen Keller"}
   {:frase "Cada carrera es una guerra. Cada carrera es una pelea. Si no acudes a cada evento con esa creencia, nunca podrás alcanzar tus metas."
    :autor "Fabian Cancellara"}
   {:frase "La carrera es ganada por el ciclista que puede sufrir más."
    :autor "Eddy Merckx"}
   {:frase "Seguramente la bicicleta será siempre el vehiculo de los novelistas y los poetas."
    :autor "Christopher Morley"}
   {:frase "Si te preocupa caerte de la bicicleta, nunca te subirás."
    :autor "Lance Armstrong"}
   {:frase "La vida es como montar en bicicleta. Para mantener el equilibrio hay que seguir pedaleando."
    :autor "Albert Einstein"}
   {:frase "Siempre que veo a un adulto encima de una bicicleta recupero la esperanza en el futuro de la raza humana."
    :autor "H.G. Wells"}
   {:frase "El ciclismo es el elixir de la juventud."
    :autor "Hector Lucero Quihuis"}
   {:frase "Pedaleaba y pedaleaba sin parar, la felicidad me invadia, era invencible, pero de repente desperte."
    :autor "Hector Lucero Quihuis"}
   {:frase "Cuando mis piernas ya no quieren pedalear, las obligo a continuar."
    :autor "Martha Lucero"}])
;; End frases

(defn create-database
  "Create database tables and default admin users
  Note: First create the database on MySQL with any client"
  []
  (Query! db cuadrantes-sql)
  (Query! db eventos-sql)
  (Query! db rodadas-sql)
  (Query! db rodadas_link-sql)
  (Query! db aventuras-sql)
  (Query! db talleres-sql)
  (Query! db fotos-sql)
  (Query! db frases-sql)
  (Query! db users-sql)
  (Query! db "LOCK TABLES frases WRITE;")
  (Insert-multi db :frases frases-rows)
  (Query! db "UNLOCK TABLES;")
  (Query! db "LOCK TABLES users WRITE;")
  (Insert-multi db :users users-rows)
  (Query! db "UNLOCK TABLES;"))

(defn reset-database
  "Removes existing tables and re-creates them"
  []
  (Query! db "DROP table IF EXISTS cuadrantes")
  (Query! db "DROP table IF EXISTS eventos")
  (Query! db "DROP table IF EXISTS rodadas_link")
  (Query! db "DROP table IF EXISTS rodadas")
  (Query! db "DROP table IF EXISTS aventuras")
  (Query! db "DROP table IF EXISTS talleres")
  (Query! db "DROP table IF EXISTS fotos")
  (Query! db "DROP table IF EXISTS frases")
  (Query! db "DROP table IF EXISTS users")
  (Query! db cuadrantes-sql)
  (Query! db eventos-sql)
  (Query! db rodadas-sql)
  (Query! db rodadas_link-sql)
  (Query! db aventuras-sql)
  (Query! db talleres-sql)
  (Query! db frases-sql)
  (Query! db "LOCK TABLES frases WRITE;")
  (Insert-multi db :frases frases-rows)
  (Query! db "UNLOCK TABLES;")
  (Query! db users-sql)
  (Query! db "LOCK TABLES users WRITE;")
  (Insert-multi db :users users-rows)
  (Query! db "UNLOCK TABLES;"))

(defn migrate
  "Migrate by the seat of my pants"
  []
  (Query! db "DROP TABLE IF EXISTS frases")
  (Query! db frases-sql)
  (Query! db "LOCK TABLES frases WRITE;")
  (Insert-multi db :frases frases-rows)
  (Query! db "UNLOCK TABLES;"))
;;(migrate)
