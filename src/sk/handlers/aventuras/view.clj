(ns sk.handlers.aventuras.view
  (:require [clojure.string :refer [upper-case]]))

(defn line-rr [label value]
  [:div.row
   [:div.col-xs-4.col-sm-4.col-md-3.col-lg-2.text-primary [:strong label]]
   [:div.col-xs.8.col-sm-8.col-md-9.col-lg-10 value]])

(defn body-rr [row]
  [:div.container.border.border-dark.rounded {:style "margin-bottom:10px;"}
   [:h2.card-title (:nombre row)]
   (line-rr "Fecha:" [:strong.text-warning (str (upper-case (:dia row)) (upper-case (:f_fecha row)))])
   (when (:enlace row)
     (line-rr "Fotos:" [:a.btn.btn-secondary
                        {:href (:enlace row)
                         :data-options "plain:true"
                         :target "_blank"} [:strong.text-secondary "Clic aqui para ver fotos!"]]))
   (line-rr "Aventura:" (:aventura row)) [:br]])

(defn aventuras-view [rows]
  [:div.row
   [:div.col
    [:div.card
     (map body-rr rows)]]])

(defn aventuras-scripts []
  [:script])
