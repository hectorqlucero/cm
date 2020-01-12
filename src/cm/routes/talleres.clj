(ns cm.routes.talleres
  (:require [cm.models.crud :refer [db Query]]
            [cm.models.util :refer [get-session-id]]
            [cm.views.layout :refer [application]]
            [cm.views.routes.talleres :refer [reporte-scripts reporte-view]]))

(defn reporte [request]
  (let [title   "Talleres de bicicletas"
        ok      (get-session-id)
        js      (reporte-scripts)
        rows    (Query db "SELECT * FROM talleres ORDER BY nombre")
        content (reporte-view title rows)]
    (application title ok js content)))
