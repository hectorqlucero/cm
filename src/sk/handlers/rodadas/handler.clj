(ns sk.handlers.rodadas.handler
  (:require [cheshire.core :refer [generate-string]]
            [clojure.string :refer [blank?]]
            [sk.models.crud :refer [db Delete Query Query! Save]]
            [sk.models.email :refer [host send-email]]
            [sk.models.util :refer [fix-id get-session-id]]
            [sk.layout :refer [application]]
            [sk.handlers.rodadas.view
             :refer
             [asistir-scripts 
              asistir-view 
              rodadas-scripts 
              rodadas-view]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]))

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

(defn process-confirmados [rodadas_id]
  (let [rows (Query db ["select email from rodadas_link where rodadas_id = ? and asistir = ?" rodadas_id "T"])
        data (if (seq rows)
               (subs (clojure.string/triml (apply str (map #(str ", " (:email %)) rows))) 2)
               "ninguno")]
    data))

(defn rodadas [_]
  (purge)
  (repeat-event)
  (let [title   "Rodadas"
        ok      (get-session-id)
        js      (rodadas-scripts)
        content (rodadas-view)]
    (application title ok js content)))
;; End rodadas

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
                               "<small>Esta es un aplicación para todos los ciclistas de Mexicali. se aceptan sugerencias.  <a href='mailto: hectorqlucero@gmail.com'>Clic aquí para mandar sugerencias</a></small>")
        body              {:from    "lucero_systems@fastmail.com"
                           :to      leader_email
                           :cc      "hectorqlucero@fastmail.com"
                           :subject (str descripcion_corta " - Confirmar asistencia")
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
          result       (Save db :rodadas_link postvars ["rodadas_id = ? and email = ?" rodadas_id email])]
      (if (seq result)
        (do
          (send-email host body)
          (generate-string {:success "Correctamente Processado!"}))
        (generate-string {:error "No se pudo processar!"})))
    (catch Exception e (.getMessage e))))
;;End form-assistir
