(ns sk.handlers.talleres.view
  (:require [clojure.string :refer [upper-case]]))

(defn reporte-view [title rows]
  (list
   (for [row rows]
     [:section.p-5
      [:div.container
       [:div.card.bg-secondary.text-secondary
        [:div.card-body.text-center
         [:h3.card-title.mb-3.text-warning (upper-case (:nombre row))]
         [:p.card-text.text-uppercase.text-justify (:historia row)]
         [:p.card-text.text-uppercase
          [:div.row
           [:div.col-auto.text-left.text-primary
            [:strong "SITIO"]]
           [:div.col.text-left
            [:a {:href (str (:sitio row))
                 :target "_blank"} [:strong.text-primary "Click aqui para ir al sitio"]]]]]
         [:p.card-text.text-uppercase
          [:div.row
           [:div.col-auto.text-left.text-primary
            [:strong "DIRECCIÓN"]]
           [:div.col.text-left
            (str (:direccion row))]]]
         [:p.card-text.text-uppercase
          [:div.row
           [:div.col-auto.text-left.text-primary
            [:strong "HORARIOS"]]
           [:div.col.text-left
            (str (:horarios row))]]]
         [:p.card-text.text-uppercase
          [:div.row
           [:div.col-auto.text-left.text-primary
            [:strong "TELEFONO"]]
           [:div.col.text-left
            (str (:telefono row))]]]
         [:p.card-text.text-uppercase
          [:div.row
           [:div.col-auto.text-left.text-primary
            [:strong "MAPA"]]
           [:div.col.text-left
            [:a {:href (str (:direcciones row))
                 :target "_blank"} [:strong.text-primary "Cómo Llegar"]]]]]]]]])))

(defn reporte-scripts [])
