(ns cm.proutes.cuadrantes
  (:require [cheshire.core :refer [generate-string]]
            [cm.models.crud :refer [build-postvars db Delete Query Save]]
            [cm.models.grid :refer :all]
            [cm.models.util :refer [fix-id get-session-id parse-int]]
            [cm.views.layout :refer :all]
            [cm.views.proutes.cuadrantes
             :refer
             [cuadrantes-scripts cuadrantes-view]]))

(defn cuadrantes [request]
  (let [title   "Cuadrantes"
        ok      (get-session-id)
        js      (cuadrantes-scripts)
        content (cuadrantes-view title)]
    (application title ok js content)))

;; Start cuadrantes grid
(def search-columns
  ["id"
   "name"
   "leader"
   "leader_phone"
   "leader_cell"
   "leader_email"
   "notes"
   "CASE WHEN status = 'T' THEN 'Activo' WHEN status = 'F' THEN 'Inactivo' ELSE '??' END"])

(def aliases-columns
  ["id"
   "name"
   "leader"
   "leader_phone"
   "leader_cell"
   "leader_email"
   "notes"
   "CASE WHEN status = 'T' THEN 'Activo' WHEN status = 'F' THEN 'Inactivo' ELSE '??' END AS status"])

(defn cuadrantes-grid [{params :params}]
  (try
    (let [table    "cuadrantes"
          scolumns (convert-search-columns search-columns)
          aliases  aliases-columns
          join     ""
          search   (grid-search (:search params nil) scolumns)
          order    (grid-sort (:sort params nil) (:order params nil))
          order    (grid-sort-extra order "name")
          offset   (grid-offset (parse-int (:rows params)) (parse-int (:page params)))
          rows     (grid-rows table aliases join search order offset)]
      (generate-string rows))
    (catch Exception e (.getMessage e))))
;; End cuadrantes grid

;; Start cuadrantes form
(def cuadrantes-form-sql
  "SELECT id as id,
   name,
   leader,
   leader_phone,
   leader_cell,
   leader_email,
   notes,
   status
   FROM cuadrantes
   WHERE id = ?")

(defn cuadrantes-form [id]
  (try
    (let [row (Query db [cuadrantes-form-sql id])]
      (generate-string (first row)))
    (catch Exception e (.getMessage e))))
;; End cuadrantes form

(defn cuadrantes-save [{params :params}]
  (try
    (let [id       (fix-id (:id params))
          postvars (build-postvars "cuadrantes" params)
          result   (Save db :cuadrantes postvars ["id = ?" id])]
      (if (seq result)
        (generate-string {:success "Correctamente Processado!"})
        (generate-string {:error "No se pudo processar!"})))))

(defn cuadrantes-delete [{params :params}]
  (try
    (let [id     (:id params nil)
          result (if-not (nil? id)
                   (Delete db :cuadrantes ["id = ?" id])
                   nil)]
      (if (seq result)
        (generate-string {:success "Removido appropiadamente!"})
        (generate-string {:error "No se pudo remover!"})))))
