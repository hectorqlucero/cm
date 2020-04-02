(ns sk.handlers.eventos.handler
  (:require [sk.models.crud :refer [config db Query]]
            [sk.models.util :refer [current_year
                                    get-month-name 
                                    get-session-id 
                                    parse-int zpl]]
            [sk.layout :refer [application]]
            [sk.handlers.eventos.sql :refer [eventos-sql]]
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

(defn display-eventos [year month]
  (let [title   (get-month-name (parse-int month))
        ok      (get-session-id)
        js      (display-eventos-scripts year month)
        rows    (Query db [eventos-sql year month])
        rows    (map #(assoc % :day (zpl (% :day) 2)) rows)
        content (display-eventos-view title year month rows (str (config :path) "eventos/"))]
    (application title ok js content)))
