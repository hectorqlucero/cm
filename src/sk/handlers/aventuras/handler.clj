(ns sk.handlers.aventuras.handler
  (:require [sk.handlers.aventuras.view :refer [aventuras-scripts aventuras-view]]
            [sk.layout :refer [application]]
            [sk.models.crud :refer [Query db]]
            [sk.models.util :refer [get-session-id]]))

;; Start aventuras
(def aventuras-sql
  "
  SELECT
  CONCAT(users.firstname,' ', users.lastname) as nombre,
  aventuras.aventura,
  DATE_FORMAT(aventuras.fecha, '%W ') as dia,
  DATE_FORMAT(aventuras.fecha, '%e de %M %Y') as f_fecha,
  enlace
  FROM aventuras 
  JOIN users ON users.username = aventuras.leader_email
  ORDER BY aventuras.fecha desc
  ")

(defn aventuras [_]
  (let [title   "Rodadas"
        ok      (get-session-id)
        js      (aventuras-scripts)
        rows    (Query db aventuras-sql)
        content (aventuras-view rows)]
    (application title ok js content)))
;; End aventuras
