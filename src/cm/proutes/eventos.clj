(ns cm.proutes.eventos
  (:require [cheshire.core :refer [generate-string]]
            [cm.models.crud :refer [build-postvars config db Delete Query Save]]
            [cm.models.grid :refer :all]
            [cm.models.util
             :refer
             [fix-id get-session-id parse-int upload-image user-email user-level]]
            [cm.views.layout :refer :all]
            [cm.views.proutes.eventos :refer [eventos-scripts eventos-view]]))

(defn eventos [request]
  (let [title   "Calendario - Eventos"
        ok      (get-session-id)
        js      (eventos-scripts)
        content (eventos-view title)]
    (application title ok js content)))

;; Start eventos grid
(def search-columns
  ["id"
   "titulo"
   "detalles"
   "lugar"
   "DATE_FORMAT(fecha,'%m/%d/%Y')"
   "TIME_FORMAT(hora,'%h:%i %p'"
   "organiza"])

(def aliases-columns
  ["id"
   "titulo"
   "detalles"
   "lugar"
   "DATE_FORMAT(fecha,'%m/%d/%Y') as fecha"
   "TIME_FORMAT(hora,'%h:%i %p') as hora"
   "organiza"])

(defn eventos-grid [{params :params}]
  (try
    (let [table    "eventos"
          user     (or (get-session-id) "Anonimo")
          level    (user-level)
          email    (user-email)
          scolumns (convert-search-columns search-columns)
          aliases  aliases-columns
          join     ""
          search   (grid-search (:search params nil) scolumns)
          order    (grid-sort (:sort params nil) (:order params nil))
          order    (grid-sort-extra order "fecha,hora")
          offset   (grid-offset (parse-int (:rows params)) (parse-int (:page params)))
          rows     (grid-rows table aliases join search order offset)]
      (generate-string rows))
    (catch Exception e (.getMessage e))))
;; End eventos grid

;; Start eventos form
(def eventos-form-sql
  "SELECT id as id,
   imagen,
   titulo,
   detalles,
   lugar,
   DATE_FORMAT(fecha,'%m/%d/%Y') as fecha,
   TIME_FORMAT(hora,'%H:%i') as hora,
   organiza
   FROM eventos
   WHERE id = ?")

(defn eventos-form [id]
  (try
    (let [row (Query db [eventos-form-sql id])]
      (generate-string (first row)))
    (catch Exception e (.getMessage e))))
;; End eventos form

(defn eventos-save [{params :params}]
  (try
    (let [id         (fix-id (:id params))
          file       (:file params)
          image-name (if-not (zero? (:size file))
                       (upload-image file id (str (config :uploads) "/eventos/"))
                       (str (params :imagen)))
          user       (or (get-session-id) "Anonimo")
          repetir    (or (:repetir params) "F")
          data       (dissoc (build-postvars "eventos" params) :file)
          postvars   (assoc data :imagen image-name)
          result     (Save db :eventos postvars ["id = ?" id])]
      (if (seq result)
        (generate-string {:success "Correctamente Processado!"})
        (generate-string {:error "No se pudo processar!"})))))

(defn eventos-delete [{params :params}]
  (try
    (let [id     (:id params nil)
          result (if-not (nil? id)
                   (Delete db :eventos ["id = ?" id])
                   nil)]
      (if (seq result)
        (generate-string {:success "Removido appropiadamente!"})
        (generate-string {:error "No se pudo remover!"})))))
