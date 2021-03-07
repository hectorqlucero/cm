(ns sk.handlers.aventuras.handler
  (:require [cheshire.core :refer [generate-string]]
            [clojure.string :refer [blank?]]
            [sk.models.crud :refer [db Delete Query Query! Save]]
            [sk.models.email :refer [host send-email]]
            [sk.models.util :refer [fix-id get-session-id]]
            [sk.layout :refer [application]]
            [sk.handlers.aventuras.view
             :refer
             [aventuras-scripts 
              aventuras-view]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]))

;; Start aventuras
(def aventuras-sql
  "
  SELECT
  nombre,
  aventura,
  DATE_FORMAT(fecha, '%W ') as dia,
  DATE_FORMAT(fecha, '%e de %M %Y') as f_fecha
  FROM aventuras ORDER BY fecha desc
  ")

(defn aventuras [_]
  (let [title   "Rodadas"
        ok      (get-session-id)
        js      (aventuras-scripts)
        rows    (Query db aventuras-sql)
        content (aventuras-view rows)]
    (application title ok js content)))
;; End aventuras
