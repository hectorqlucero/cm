(ns sk.handlers.grupos.view
  (:require [clojure.string :refer [upper-case]]))

(defn reporte-view [_ rows]
  (list
   (for [row rows]
     [:section.p-5
      [:div.container
       [:div.card.bg-secondary.text-secondary
        [:div.card-body.text-center
         [:h3.card-title.mb-3.text-warning (upper-case (:name row))]
         [:p.card-text.text-uppercase.text-justify (:notes row)]
         [:p.card-text.text-uppercase
          [:div.row
           [:div.col-auto.text-left.text-primary
            [:strong "LEADER"]]
           [:div.col.text-left
            (str (:leader row))]]]
         [:div.row
          [:div.col-auto.text-left.text-primary
           [:strong "TELEFONO"]]
          [:div.col.text-left
           (str (:leader_phone row))]]
         [:div.row
          [:div.col-auto.text-left.text-primary
           [:strong "CELULAR"]]
          [:div.col.text-left
           (str (:leader_cell row))]]
         [:div.row
          [:div.col-auto.text-left.text-primary
           [:strong "EMAIL"]]
          [:div.col.text-left
           (str (:leader_email row))]]
         [:div.row
          [:div.col-auto.text-left.text-primary
           [:strong "STATUS"]]
          [:div.col.text-left
           (str (:status row))]]]]]])))

(defn reporte-scripts [])
