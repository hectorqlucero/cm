(ns cm.proutes.eventos
  (:require [cheshire.core :refer [generate-string]]
            [cm.models.crud :refer [build-postvars config db Delete Query Save Update]]
            [cm.models.grid :as grid]
            [cm.models.util
             :refer
             [fix-id get-session-id parse-int upload-image]]
            [cm.views.layout :as layout]
            [cm.views.proutes.eventos :refer [eventos-scripts eventos-view]]))

(defn eventos [_]
  (let [title   "Calendario - Eventos"
        ok      (get-session-id)
        js      (eventos-scripts)
        content (eventos-view title)]
    (layout/application title ok js content)))

;; Start eventos grid
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
          scolumns nil
          aliases  aliases-columns
          join     ""
          search   (grid/grid-search (:search params nil) scolumns)
          order    (grid/grid-sort (:sort params nil) (:order params nil))
          order    (grid/grid-sort-extra order "fecha,hora")
          offset   (grid/grid-offset (parse-int (:rows params)) (parse-int (:page params)))
          rows     (grid/grid-rows table aliases join search order offset)]
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

;; Start eventos-save
(defn get-id [id postvars]
  (if (nil? id)
    (let [result (Save db :eventos postvars ["id = ?" id])]
      (str (:generated_key (first result))))
    id))

(defn eventos-save [{params :params}]
  (try
    (let [id (fix-id (:id params))
          file (:file params)
          postvars (dissoc (build-postvars "eventos" params) :file)
          the-id (str (get-id id postvars))
          path (str (:uploads config) "/eventos/")
          image-name (upload-image file the-id path)
          postvars (assoc postvars :imagen image-name :id the-id)
          result (Update db :eventos postvars ["id = ?" the-id])]
      (if (seq result)
        (generate-string {:success "Correctamente Processado!"})
        (generate-string {:error "No se pudo processar!"})))))
;; End eventos-save

(defn eventos-delete [{params :params}]
  (try
    (let [id     (:id params nil)
          result (if-not (nil? id)
                   (Delete db :eventos ["id = ?" id])
                   nil)]
      (if (seq result)
        (generate-string {:success "Removido appropiadamente!"})
        (generate-string {:error "No se pudo remover!"})))))
