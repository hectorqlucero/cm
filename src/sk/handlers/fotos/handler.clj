(ns sk.handlers.fotos.handler
  (:require [hiccup.page :refer [html5]]
            [sk.layout :refer [application]]
            [sk.models.crud :refer [db Query]]
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

(defn process-row [row]
  [:a.list-group-item.list-group-item-action.list-group-item-secondary
   {:href (:enlace row)
    :data-options "plain:true"
    :target "_blank"} (str (:dia row) " " (:f_fecha row))])

(defn handle-body []
  (let [rows (Query db fotos-sql)]
    (map process-row rows)))

(defn get-fotos []
  (html5
   [:div.container
    [:div.list-group
     [:a.list-group-item.list-group-item-action {:style "text-align:center;"} [:h2 [:strong.text-warning "Clic abajo para ver fotos"]]]
     (handle-body)]]))

(defn fotos [_]
  (let [title "Fotos - Rodadas"
        ok (get-session-id)
        content (get-fotos)]
    (application title ok nil content)))
