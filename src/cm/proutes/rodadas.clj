(ns cm.proutes.rodadas
  (:require [cheshire.core :refer [generate-string]]
            [cm.models.crud :refer [build-postvars
                                    db
                                    Delete
                                    Query
                                    Save]]
            [cm.models.email :refer [host send-email]]
            [cm.models.grid :refer [convert-search-columns
                                    grid-search
                                    grid-search-extra
                                    grid-sort
                                    grid-offset
                                    grid-rows]]
            [cm.models.util :refer [fix-id
                                    get-session-id
                                    parse-int
                                    user-email
                                    user-level]]
            [cm.views.layout :refer [application]]
            [clojure.string :refer [lower-case]]
            [cm.views.proutes.rodadas :refer [rodadas-scripts rodadas-view]]))

(defn rodadas [_]
  (let [title   "Entrenamiento - Rodadas"
        ok      (get-session-id)
        js      (rodadas-scripts)
        content (rodadas-view title)]
    (application title ok js content)))

;; Start rodadas grid
(def search-columns
  ["id"
   "titulo"
   "detalles"
   "punto_reunion"
   "CASE WHEN nivel = 'P' THEN 'Principiantes' WHEN nivel = 'M' THEN 'Intermedio' WHEN nivel = 'A' THEN 'Avanzado' WHEN nivel = 'T' THEN 'TODOS' END"
   "distancia"
   "velocidad"
   "CASE WHEN repetir = 'T' THEN 'Si' ELSE 'No' END"
   "DATE_FORMAT(fecha,'%m/%d/%Y')"
   "TIME_FORMAT(salida,'%h:%i %p'"
   "leader"])

(def aliases-columns
  ["id"
   "titulo"
   "detalles"
   "CASE WHEN nivel = 'P' THEN 'Principiantes' WHEN nivel = 'M' THEN 'Intermedio' WHEN nivel = 'A' THEN 'Avanzado' WHEN nivel = 'T' THEN 'TODOS' END AS nivel"
   "distancia"
   "velocidad"
   "punto_reunion"
   "CASE WHEN repetir = 'T' THEN 'Si' ELSE 'No' END as repetir"
   "DATE_FORMAT(fecha,'%m/%d/%Y') as fecha"
   "TIME_FORMAT(salida,'%h:%i %p') as salida"
   "leader"])

(defn grid-rodadas [{params :params}]
  (try
    (let [table    "rodadas"
          level    (user-level)
          email    (user-email)
          scolumns (convert-search-columns search-columns)
          aliases  aliases-columns
          join     ""
          search   (grid-search (:search params nil) scolumns)
          search   (if (= level "U") (grid-search-extra search (str "leader_email = '" email "'")) nil)
          order    (grid-sort (:sort params nil) (:order params nil))
          offset   (grid-offset (parse-int (:rows params)) (parse-int (:page params)))
          rows     (grid-rows table aliases join search order offset)]
      (generate-string rows))
    (catch Exception e (.getMessage e))))
;; End rodadas grid

;; Start rodadas form
(def rodadas-form-sql
  "SELECT id as id,
   titulo,
   detalles,
   punto_reunion,
   nivel,
   distancia,
   velocidad,
   DATE_FORMAT(fecha,'%m/%d/%Y') as fecha,
   TIME_FORMAT(salida,'%H:%m:%s') as salida,
   salida,
   leader,
   leader_email,
   repetir
   FROM rodadas
   WHERE id = ?")

(defn form-rodadas
  [id]
  (try
    (let [row (Query db [rodadas-form-sql id])]
      (generate-string (first row)))
    (catch Exception e (.getMessage e))))
;; End rodadas form

(defn rodadas-save [{params :params}]
  (try
    (let [id           (fix-id (:id params))
          repetir      (:repetir params)
          leader_email (lower-case (:leader_email params))
          data         (build-postvars "rodadas" params)
          postvars     (assoc data :leader_email leader_email :repetir repetir)
          result       (Save db :rodadas postvars ["id = ?" id])]
      (if (seq result)
        (generate-string {:success "Correctamente Processado!"})
        (generate-string {:error "No se pudo processar!"})))
    (catch Exception e (.getMessage e))))

;;Start rodadas-delete
(defn build-recipients [rodadas_id]
  (into [] (map #(first (vals %)) (Query db ["SELECT email from rodadas_link where rodadas_id = ?" rodadas_id]))))

(defn email-delete-body [rodadas_id]
  (let [row               (first (Query db ["SELECT leader,leader_email,titulo FROM rodadas where id = ?" rodadas_id]))
        leader            (:leader row)
        leader_email      (:leader_email row)
        descripcion_corta (:titulo row)
        content           (str "<strong>Hola:</strong></br></br>La rodada organizada por: " leader " <a href='mailto:" leader_email "'>" leader_email "</a> se cancelo.  Disculpen la inconveniencia que esto pueda causar.</br>"
                               "<small><strong>Nota:</strong><i> Si desea contestarle a esta persona, por favor hacer clic en el email arriba!</i></br></br>"
                               "Muchas gracias por su participacion y esperamos que la proxima vez se pueda realizar la rodada.</br></br>"
                               "<small>Esta es un aplicación para todos los ciclistas de Mexicali. se aceptan sugerencias.  <a href='mailto: hectorqlucero@gmail.com'>Clic aquí para mandar sugerencias</a></small>")
        recipients        (build-recipients rodadas_id)
        body              {:from    "hectorqlucero@gmail.com"
                           :to      recipients
                           :cc      "hectorqlucero@gmail.com"
                           :subject (str descripcion_corta " - Cancelacion")
                           :body    [{:type    "text/html;charset=utf-8"
                                      :content content}]}]
    body))

(defn rodadas-delete [{params :params}]
  (try
    (let [id     (:id params nil)
          result (if-not (nil? id)
                   (do
                     (send-email host (email-delete-body id))
                     (Delete db :rodadas ["id = ?" id]))
                   nil)]
      (if (seq result)
        (generate-string {:success "Removido appropiadamente!"})
        (generate-string {:error "No se pudo remover!"})))
    (catch Exception e (.getMessage e))))
;;end rodadas-delete
