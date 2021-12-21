(ns sk.models.cdb
  (:require [noir.util.crypt :as crypt]
            [sk.models.crud :refer [Insert-multi Query! db]]))

;; Start users
(def users-rows
  [{:lastname  "User"
    :firstname "Regular"
    :username  "user@gmail.com"
    :password  (crypt/encrypt "user")
    :dob       "1957-02-07"
    :email     "user@gmail.com"
    :level     "U"
    :active    "T"}
   {:lastname "User"
    :firstname "Admin"
    :username "admin@gmail.com"
    :password (crypt/encrypt "admin")
    :dob "1957-02-07"
    :email "admin@gmail.com"
    :level "A"
    :active "T"}
   {:lastname "User"
    :firstname "Sistema"
    :username "sistema@gmail.com"
    :password (crypt/encrypt "sistema")
    :dob "1957-02-07"
    :email "sistema@gmail.com"
    :level "S"
    :active "T"}])
;; End users

;; Start frases
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
    :autor "Kristin Armstrong"}
   {:frase "La tolerancia requiere el mismo esfuerzo del cerebro que mantener el equilibrio sobre una bicicleta."
    :autor "Helen Keller"}
   {:frase "Cada carrera es una guerra. Cada carrera es una pelea. Si no acudes a cada evento con esa creencia, nunca podrás alcanzar tus metas."
    :autor "Fabian Cancellara"}
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

(defn populate-tables
  "Populates table with default data"
  [table rows]
  (Query! db (str "LOCK TABLES " table "WRITE;"))
  (Insert-multi db (keyword table) rows)
  (Query! db "UNLOCK TABLES;"))

(comment
  (populate-tables "users" users-rows)
  (populate-tables "frases" frases-rows))
