(ns sk.handlers.fotos.handler
  (:require [sk.layout :refer [application]]
            [sk.models.crud :refer [Query db]]
            [sk.models.util :refer [get-session-id]]))

(def fotos-sql
  "
  SELECT
  DATE_FORMAT(fecha, '%W ') as dia,
  DATE_FORMAT(fecha, '%e de %M %Y') as f_fecha,
  enlace
  FROM fotos
  ORDER BY fecha desc")

(defn get-rows []
  (Query db fotos-sql))

(defn get-fotos [title]
  [:div.container
   [:table.table.table-hover.table-bordered
    [:caption {:style "caption-side:top;"} title]
    [:thead.table-info
     [:tr
      [:th {:data-options "field:'enlace'" :style "text-align:center;"} "PROCESAR"]
      [:th {:data-options "field:'dia'"} "DIA"]
      [:th {:data-options "field:'f_fecha'"} "FECHA"]]]
    [:tbody
     (for [row (get-rows)]
       [:tr
        [:td [:a.btn.btn-info {:href (:enlace row) :target "_blank"} [:span.float-right "Ver Fotos"]]]
        [:td (:dia row)]
        [:td (:f_fecha row)]])]]])

(defn fotos [_]
  (let [title "Fotos - Ciclismo Mexicali"
        ok (get-session-id)
        content (get-fotos title)]
    (application title ok nil content)))
