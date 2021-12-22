(ns sk.handlers.grupos.view
  (:require [sk.models.util :refer [capitalize-words]]))

(defn line-rr [label value]
  (list
   [:div.row
    [:div.col-xs.col-sm-4.col-md-3.col-lg-2.text-primary [:strong label]]
    [:div.col-xs.8.col-sm-8.col-md-9.col-lg-10 value]]))

(defn body-rr [row]
  (list
   [:div.container.border.border-dark.rounded {:style "margin-bottom:10px;"}
    [:h2.card-title (capitalize-words (:name row))]
    (line-rr "Leader:" (:leader row))
    (line-rr "Telefono:" (:leader_telefono row))
    (line-rr "Celular:" (:leader_cell row))
    (line-rr "Email:" (:leader_email row))
    (line-rr "Status:" (:status row))]))

(defn reporte-view [_ rows]
  [:div.row
   [:div.col
    [:div.card
     (map body-rr rows)]]])
(defn reporte-scripts [])
