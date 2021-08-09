(ns sk.handlers.aventuras.view
  (:require [clojure.string :refer [upper-case]]))

(defn line-rr [label value]
  [:div.row
   [:div.col-xs-4.col-sm-4.col-md-3.col-lg-2.text-primary [:strong label]]
   [:div.col-xs.8.col-sm-8.col-md-9.col-lg-10 value]])

(defn body-rr [row]
  [:h2 (:nombre row)
   [:div.card
    [:div.card-body {:style "font-size:.5em"}
     (line-rr "Fecha:" [:strong.text-warning (str (upper-case (:dia row)) (upper-case (:f_fecha row)))])
     (when (:enlace row)
       (line-rr "Fotos:" [:a.list-group-item.list-group-item-action.list-group-item-secondary
                          {:href (:enlace row)
                           :data-options "plain:true"
                           :target "_blank"} "Clic aqui para ver fotos!"]))
     (line-rr "Aventura:" (:aventura row))]]])

(defn aventuras-view [rows]
  (list
   [:div.container
    (map body-rr rows)]))

(defn aventuras-scripts []
  [:script])
