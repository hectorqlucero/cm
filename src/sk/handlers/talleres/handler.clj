(ns sk.handlers.talleres.handler
  (:require [sk.models.crud :refer [db Query]]
            [sk.models.util :refer [get-session-id]]
            [sk.layout :refer [application]]
            [sk.handlers.talleres.view :refer [reporte-scripts reporte-view]]))

(defn reporte [_]
  (let [title   "Talleres de bicicletas"
        ok      (get-session-id)
        js      (reporte-scripts)
        rows    (Query db "SELECT * FROM talleres ORDER BY nombre")
        content (reporte-view title rows)]
    (application title ok js content)))
