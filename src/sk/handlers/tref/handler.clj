(ns sk.handlers.tref.handler
  (:require [hiccup.page :refer [html5]]
            [clojure.string :as st]
            [sk.models.crud :refer [db Query]]
            [sk.models.util :refer [current_year get-image parse-int zpl]]))

;; Start get-users
(def get-users-sql
  "SELECT
  id AS value,
  CONCAT(firstname,' ',lastname) AS text
  FROM users
  ORDER BY
  firstname,lastname")

(defn get-users []
  "Regresa todos los usuarios o vacio :ex: (get-users)"
  (Query db [get-users-sql]))
;; End get-users

;; Start get-users-email
(def get-users-email-sql
  "SELECT
  LOWER(email) as email
  FROM users
  WHERE email = ?")

(defn level-options []
  (list
   {:value "U" :text "Usuarios"}
   {:value "A" :text "Administrador"}
   {:value "S" :text "Systema"}))

(defn get-users-email [email]
  "Regresa el correo del usuario o nulo"
  (first (Query db [get-users-email-sql (st/lower-case email)])))
;; End get-users-email

(defn months []
  "Regresa un arreglo de meses en español ex: (months)"
  (list
   {:value 1 :text "Enero"}
   {:value 2 :text "Febrero"}
   {:value 3 :text "Marzo"}
   {:value 4 :text "Abril"}
   {:value 5 :text "Mayo"}
   {:value 6 :text "Junio"}
   {:value 7 :text "Julio"}
   {:value 8 :text "Agosto"}
   {:value 9 :text "Septiembre"}
   {:value 10 :text "Octubre"}
   {:value 11 :text "Noviembre"}
   {:value 12 :text "Diciembre"}))

(defn years [p n]
  "Genera listado para dropdown dependiendo de p=anterioriores de este año, n=despues de este año,
  ex: (years 5 4)"
  (let [year   (parse-int (current_year))
        pyears (for [n (range (parse-int p) 0 -1)] {:value (- year n) :text (- year n)})
        nyears (for [n (range 0 (+ (parse-int n) 1))] {:value (+ year n) :text (+ year n)})
        years  (concat pyears nyears)]
    years))

;; Start calendar events
(def rodadas-sql
  "SELECT
  id,
  titulo as title,
  detalles,
  DATE_FORMAT(salida,'%h:%i %p') as hora,
  DATE_FORMAT(fecha,'%d/%m/%Y') as fecha,
  CONCAT(fecha,'T',salida) as start,
  punto_reunion as donde,
  CASE WHEN nivel = 'P' THEN 'Principiantes' WHEN nivel = 'M' THEN 'Medio' WHEN nivel = 'A' THEN 'Avanzado' WHEN nivel = 'T' THEN 'TODOS' END as nivel,
  distancia as distancia,
  velocidad as velocidad,
  leader as leader,
  leader_email as email,
  repetir,
  CONCAT('/rodadas/asistir/',id) as url
  FROM rodadas
  ORDER BY fecha,salida")

(defn build-cal-popup [row]
  (html5
   [:div {:style "margin-bottom:10px;"}
    [:label [:strong "Titulo: "] (:title row)]]
   [:div {:style "margin-bottom:5px;"}
    [:label [:strong "Describir Rodada: "] (:detalles row)]]
   [:div {:style "margin-bottom:5px;"}
    [:label [:strong "Punto de reunion: "] (:donde row)]]
   [:div {:style "margin-bottom:5px;"}
    [:label [:strong "Nivel: "] (:nivel row)]]
   [:div {:style "margin-bottom:5px;"}
    [:label [:strong "Distancia: "] (:distancia row)]]
   [:div {:style "margin-bottom:5px;"}
    [:label [:strong "Velocidad: "] (:velocidad row)]]
   [:div {:style "margin-bottom:5px;"}
    [:label [:strong "Fecha/Rodada: "] (:fecha row)]]
   [:div {:style "margin-bottom:5px;"}
    [:label [:strong "Salida: "] (:hora row)]]
   [:div {:style "margin-bottom:5px;"}
    [:label [:strong "Lider: "] (:leader row)]]
   [:div {:style "margin-bottom:5px;"}
    [:label [:strong "Lider Email: "] (:email row)]]))

(defn calendar-events []
  (let [rows   (Query db rodadas-sql)
        events (map #(assoc % :description (build-cal-popup %)) rows)]
    events))
;; End calendar events

(defn nivel-options []
  (list
   {:value "P" :text "Principiantes"}
   {:value "M" :text "Medio"}
   {:value "A" :text "Avanzado"}
   {:value "T" :text "TODOS"}))

(defn imagen [table field idname value & extra-folder]
  (get-image table field idname value (first extra-folder)))

(defn get-item
  "Generic get field value from table"
  [table field idname idvalue]
  (let [sql (str "SELECT " field " FROM " table " WHERE " idname "='" idvalue "'")
        row (first (Query db sql))]
    ((keyword field) row)))

(defn build-time
  "Builds tipical time dropdown"
  []
  (let [items (flatten
               (for [x (range 5 21)]
                 (list
                  {:value (str (zpl x 2) ":00")
                   :text  (if (< x 12)
                            (str (zpl x 2) ":00 AM")
                            (str (if (> x 12) (zpl (- x 12) 2) (zpl x 2)) ":00 PM"))}
                  {:value (str (zpl x 2) ":30")
                   :text  (if (< x 12)
                            (str (zpl x 2) ":30 AM")
                            (str (if (> x 12) (zpl (- x 12) 2) (zpl x 2)) ":30 PM"))})))]
    items))
