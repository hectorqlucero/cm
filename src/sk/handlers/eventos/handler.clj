(ns sk.handlers.eventos.handler
  (:require [sk.models.crud :refer [config db Query]]
            [sk.models.util :refer [current_year
                                    get-month-name 
                                    get-session-id 
                                    parse-int zpl]]
            [sk.layout :refer [application]]
            [sk.handlers.eventos.view :refer [eventos-view
                                              eventos-scripts
                                              display-eventos-view
                                              display-eventos-scripts]]))

(defn eventos [_]
  (let [title   "Eventos"
        ok      (get-session-id)
        js      (eventos-scripts)
        content (eventos-view title (current_year))]
    (application title ok js content)))

;; Start display-eventos
(def eventos-sql
  "
  SELECT
  id,
  imagen,
  DAY(fecha) as day,
  CASE WHEN DAYNAME(fecha) = 'Sunday' THEN 'Domingo' WHEN DAYNAME(fecha) = 'Monday' THEN 'Lunes' WHEN DAYNAME(fecha) = 'Tuesday' THEN 'Martes' WHEN DAYNAME(fecha) = 'Wednesday' THEN 'Miercoles' WHEN DAYNAME(fecha) = 'Thursday' THEN 'Jueves' WHEN DAYNAME(fecha) = 'Friday' THEN 'Viernes' WHEN DAYNAME(fecha) = 'Saturday' THEN 'Sabado' END AS fecha_dow,
  DATE_FORMAT(fecha,'%m/%d/%Y') AS fecha,
  detalles as descripcion,
  titulo as descripcion_corta,
  lugar as punto_reunion,
  TIME_FORMAT(hora,'%h:%i %p') as hora,
  organiza as leader
  FROM eventos
  WHERE
  YEAR(fecha) = ?
  AND MONTH(fecha) = ?
  ORDER BY
  DAY(fecha),
  hora ")

(defn display-eventos [year month]
  (let [title   (get-month-name (parse-int month))
        ok      (get-session-id)
        js      (display-eventos-scripts year month)
        rows    (Query db [eventos-sql year month])
        rows    (map #(assoc % :day (zpl (% :day) 2)) rows)
        content (display-eventos-view title year month rows (str (config :path) "eventos/"))]
    (application title ok js content)))
;; End display-eventos