(ns sk.handlers.videos.handler
  (:require [hiccup.page :refer [html5]]
            [sk.layout :refer [application]]
            [sk.models.crud :refer [db Query]]
            [sk.models.util :refer [get-session-id]]))

(def videos-sql
  "
  SELECT
  DATE_FORMAT(fecha, '%W ') as dia,
  DATE_FORMAT(fecha, '%e de %M %Y') as f_fecha,
  titulo,
  enlace
  FROM videos
  ORDER BY fecha desc")

(defn get-rows []
  (Query db videos-sql))

(defn process-row [row]
  [:a.list-group-item.list-group-item-action.list-group-item-secondary
   {:href (:enlace row)
    :data-options "plain:true"
    :target "_blank"} (str (:dia row) " " (:f_fecha row) " " (:titulo row))])

(defn handle-body []
  (let [rows (Query db videos-sql)]
    (map process-row rows)))

(defn get-videos []
  (html5
   [:div.container
    [:div.list-group
     [:a.list-group-item.list-group-item-action {:style "text-align:center;"} [:h2 [:strong.text-warning "Clic abajo para ver videos"]]]
     (handle-body)]]))

(defn videos [_]
  (let [title "Videos - Rodadas"
        ok (get-session-id)
        content (get-videos)]
    (application title ok nil content)))
