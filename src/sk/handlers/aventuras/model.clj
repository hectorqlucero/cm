(ns sk.handlers.aventuras.model
  (:require [sk.models.crud :refer [Query db]]))

;; Start get-rows
(defn get-maximo [id]
  (:maximo (first (Query db ["SELECT maximo FROM cmt WHERE id=?" id]))))

(defn get-rows [id]
  (let [maximo (Integer. (get-maximo id))
        sort-type (if (> maximo 0) "desc" "asc")
        sql (str
             "
                 SELECT
                 aventuras.id,
                 users.id as user_id,
                 users.imagen as imagen,
                 CONCAT(users.firstname,' ',users.lastname) as nombre,
                 aventuras.aventura,
                 aventuras.fecha,
                 DATE_FORMAT(aventuras.fecha, '%W ') as dia,
                 DATE_FORMAT(aventuras.fecha, '%e de %M %Y') as f_fecha,
                 aventuras.enlace,
                 aventuras.enlacev
                 FROM aventuras
                 JOIN users ON users.username = aventuras.leader_email
                 WHERE aventuras.cmt_id = " id "
                 ORDER BY
                 aventuras.fecha " sort-type "
                 ")
        sql (if (> maximo 0) (str sql "limit " maximo) sql)]
    (Query db sql)))
;; End get-rows

;; Start get-cmt-rows
(defn get-cmt-rows [id]
  (Query db ["select nombre,comments FROM cmt WHERE id=?" id]))
;; End get-cmt-rows

;; Start get-aventuras-comments
(defn get-aventuras-comments [aventuras_id]
  (let [rows (Query db ["SELECT * FROM aventuras_link WHERE aventuras_id = ? ORDER BY id" aventuras_id])]
    rows))
;; End get-aventuras-comments

(comment
  (get-aventuras-comments 387)
  (get-cmt-rows 2)
  (get-maximo 1)
  (get-rows 1))
