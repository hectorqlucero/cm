(ns sk.handlers.talleres.view
  (:require [clojure.string :refer [upper-case]]))

(defn reporte-view [title rows]
  (list
   [:div.container-fluid
    [:div.col-12.text-center
     [:h3 {:style "color:#fa981b;text-transform:uppercase;font-weight:bold;"} title]]
    (for [row rows]
      [:div.row.col-auto
       [:div.col-auto
        [:div.row
         [:div.col-auto
          [:h3.h3-primary {:style "color:#127ba3;font-weight:bold;"} (upper-case (:nombre row))]]]
        [:div.row
         [:div.col-auto
          [:button.btn.btn-info.btn.lg.btn-block {:data-toggle    "popover"
                                                  :data-content   (str (:historia row))} "click para Historia"]]]
        [:div.row
         [:div.col-auto
          [:strong "SITIO"]
          [:span.vertical-divider-secondary {:style "color:#fa981b;"} "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;"]
          [:a {:href   (str (:sitio row))
               :target "_blank"} (str (:sitio row))]]]
        [:div.row
         [:div.col-auto
          [:strong "DIRECCIÓN"]
          [:span.vertical-divider-secondary {:style "color:#fa981b;"} "&nbsp;&nbsp;|&nbsp;"]
          (str (:direccion row))]]
        [:div.row
         [:div.col-auto
          [:strong "HORARIOS"]
          [:span.vertical-divider-secondary {:style "color:#fa981b;"} "&nbsp;&nbsp;&nbsp;|&nbsp;"]
          (str (:horarios row))]]
        [:div.row
         [:div.col-auto
          [:strong "TELEFONO"]
          [:span.vertical-divider-secondary {:style "color:#fa981b;"} "&nbsp;&nbsp;&nbsp;|&nbsp;"]
          (str (:telefono row))]]
        [:div.row
         [:div.col-auto
          [:strong "MAPA"]
          [:span.vertical-divider-secondary {:style "color:#fa981b;"} "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;"]
          [:a {:href   (str (:direcciones row))
               :target "_blank"} "Cómo Llegar"]]]
        [:div.row
         [:div.col-auto
          "&nbsp;"]]]])]))

(defn reporte-view [title rows]
  (list
   (for [row rows]
     [:section.p-5
      [:div.container
       [:div.card.bg-secondary.text-secondary
        [:div.card-body.text-center
         [:h3.card-title.mb-3.text-primary (upper-case (:nombre row))]
         [:p.card-text.text-uppercase.text-justify (:historia row)]
         [:p.card-text.text-uppercase
          [:div.row
           [:div.col-auto.text-left
            [:strong "SITIO"]]
           [:div.col.text-left
            [:a.text-primary {:href (str (:sitio row))
                              :target "_blank"} "Click aqui para ir al sitio"]]]]
         [:p.card-text.text-uppercase
          [:div.row
           [:div.col-auto.text-left
            [:strong "DIRECCIÓN"]]
           [:div.col.text-left
            (str (:direccion row))]]]
         [:p.card-text.text-uppercase
          [:div.row
           [:div.col-auto.text-left
            [:strong "HORARIOS"]]
           [:div.col.text-left
            (str (:horarios row))]]]
         [:p.card-text.text-uppercase
          [:div.row
           [:div.col-auto.text-left
            [:strong "TELEFONO"]]
           [:div.col.text-left
            (str (:telefono row))]]]
         [:p.card-text.text-uppercase
          [:div.row
           [:div.col-auto.text-left
            [:strong "MAPA"]]
           [:div.col.text-left
            [:a.text-primary {:href (str (:direcciones row))
                              :target "_blank"} "Cómo Llegar"]]]]]]]])))

(defn reporte-scripts [])
