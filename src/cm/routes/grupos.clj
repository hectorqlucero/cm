(ns cm.routes.grupos
  (:require [cm.models.crud :refer [db Query]]
            [cm.models.util :refer [get-session-id]]
            [cm.views.layout :refer [application]]
            [cm.views.routes.grupos :refer [reporte-scripts reporte-view]]))

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

(defn reporte [request]
  (let [title   "Grupos"
        ok      (get-session-id)
        js      (reporte-scripts)
        rows    (Query db reporte-sql)
        content (reporte-view title rows)]
    (application title ok js content)))
