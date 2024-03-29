(ns sk.handlers.grupos.handler
  (:require [sk.handlers.grupos.view :refer [reporte-scripts reporte-view]]
            [sk.layout :refer [application]]
            [sk.models.crud :refer [Query db]]
            [sk.models.util :refer [get-session-id]]))

;; Start reporte
(def reporte-sql
  "SELECT
   id,
   name,
   leader,
   leader_phone,
   leader_cell,
   leader_email,
   notes,
   CASE WHEN status = 'T' THEN 'Activo' ELSE 'Inactivo' END AS status
   FROM cuadrantes
   ORDER BY
   name")

(defn reporte [_]
  (let [title   "Grupos"
        ok      (get-session-id)
        js      (reporte-scripts)
        rows    (Query db reporte-sql)
        content (reporte-view title rows)]
    (application title ok js content)))
