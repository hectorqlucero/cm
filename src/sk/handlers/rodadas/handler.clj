(ns sk.handlers.rodadas.handler
  (:require [cheshire.core :refer [generate-string]]
            [clojure.string :refer [blank?]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [sk.handlers.rodadas.view
             :refer
             [asistir-scripts asistir-view rr-scripts rr-view]]
            [sk.layout :refer [application]]
            [sk.models.crud :refer [Delete Query Query! Save db]]
            [sk.models.email :refer [host send-email]]
            [sk.models.util :refer [fix-id get-session-id]]))

;; Start rodadas
(defn purge []
  (Delete db :rodadas ["fecha < CURRENT_DATE() AND repetir != 'T'"]))

(defn repeat-event []
  (let [purge-rows (Query db "SELECT id from rodadas where fecha < CURRENT_DATE()")
        purge-keys (apply str (interpose "," (map #(str (:id %)) purge-rows)))
        sql        (str "DELETE from rodadas_link where rodadas_id IN(" purge-keys ")")]
    (if-not (blank? purge-keys)
      (Query! db sql)
      nil)
    (Query! db "UPDATE rodadas SET fecha = DATE_ADD(fecha,INTERVAL 7 DAY) WHERE fecha < CURRENT_DATE()")))
;; End rodadas

;; Start rr
(def rr-sql
  "SELECT 
  id,
  fecha,
  DATE_FORMAT(fecha, '%W ') as dia,
  DATE_FORMAT(fecha, '%e de %M %Y') as f_fecha,
  titulo,
  detalles,
  punto_reunion,
  TIME_FORMAT(salida, '%h:%i %p') as salida,
  distancia,
  velocidad,
  leader,
  leader_email
  FROM rodadas ORDER BY fecha,salida")

(defn rr [_]
  (purge)
  (repeat-event)
  (let [title   "Rodadas de entrenamiento"
        ok      (get-session-id)
        js      (rr-scripts)
        rows    (Query db rr-sql)
        content (rr-view rows)]
    (application title ok js content)))
;; End rr

;;Start form-assistir
(def asistir-sql
  "
  SELECT
   leader,
   leader_email,
   titulo
   FROM rodadas
   WHERE
   id = ?
  ")

(defn email-body [rodadas_id user email comentarios asistir_desc]
  (let [row               (first (Query db [asistir-sql rodadas_id]))
        leader            (:leader row)
        leader_email      (:leader_email row)
        descripcion_corta (:titulo row)
        content           (str "<strong>Hola " leader ":</strong></br></br>"
                               "Mi nombre es <strong>" user "</strong> y mi correo electronico es <a href='mailto:" email "'>" email "</a> y estoy confirmando que <strong>"  asistir_desc "</strong> al evento.</br>"
                               "<small><strong>Nota:</strong><i> Si desea contestarle a esta persona, por favor hacer clic en el email arriba!</i></br></br>"
                               "<strong>Comentarios:</strong> " comentarios "</br></br>"
                               "<small>Esta es una aplicación para todos los ciclistas de Mexicali. se aceptan sugerencias.  <a href='mailto: hectorqlucero@gmail.com'>Clic aquí para mandar sugerencias</a></small>")
        body              {:from    "lucero_systems@fastmail.com"
                           :to      leader_email
                           :cc      "hectorqlucero@fastmail.com"
                           :subject (str descripcion_corta " - Confirmar asistencia")
                           :body    [{:type    "text/html;charset=utf-8"
                                      :content content}]}]
    body))

(defn email-user-body [rodadas_id user email _ _]
  (let [row               (first (Query db [asistir-sql rodadas_id]))
        descripcion_corta (:titulo row)
        content           (str "<strong>Hola " user ":</strong></br></br>"
                               "Gracias por confirmar asistencia a este evento.</br></br>"
                               "<small>Esta es una aplicación para todos los ciclistas de Mexicali.  Se aceptan sugerencias.  <a href='mailto: hectorqlucero@fastmail.com'>Clic aquí para mandar sugerencias.</a></smaill>")
        body              {:from    "lucero_systems@fastmail.com"
                           :to      email
                           :cc      "hectorqlucero@fastmail.com"
                           :subject (str descripcion_corta " - Asistencia Comfirmada!")
                           :body    [{:type    "text/html;charset=utf-8"
                                      :content content}]}]
    body))

(defn asistir [rodadas_id]
  (let [row        (first (Query db ["select titulo,DATE_FORMAT(fecha,'%d/%m/%Y') as fecha,TIME_FORMAT(salida,'%h:%i %p') as hora from rodadas where id = ?" rodadas_id]))
        event_desc (:titulo row)
        fecha      (:fecha row)
        hora       (:hora row)
        title      (str fecha " - " hora " [" event_desc "] Confirmar asistencia")
        ok         (get-session-id)
        js         (asistir-scripts)
        content    (asistir-view title rodadas_id (anti-forgery-field))]
    (application title ok js content)))

(defn asistir! [{params :params}]
  (try
    (let [rodadas_id   (fix-id (:rodadas_id params))
          email        (:email params)
          asistir_desc (if (= (:asistir params) "T")
                         "ASISTIRE"
                         "NO ASISTIRE")
          postvars     {:rodadas_id  rodadas_id
                        :user        (:user params)
                        :comentarios (:comentarios params)
                        :email       email
                        :asistir     (:asistir params)}
          body         (email-body rodadas_id (:user params) email (:comentarios params) asistir_desc)
          user-body    (email-user-body rodadas_id (:user params) email (:comentarios params) asistir_desc)
          result       (Save db :rodadas_link postvars ["rodadas_id = ? and email = ?" rodadas_id email])]
      (if (seq result)
        (do
          (send-email host body)
          (send-email host user-body)
          (generate-string {:success "Correctamente Processado!"}))
        (generate-string {:error "No se pudo processar!"})))
    (catch Exception e (.getMessage e))))
;;End form-assistir
