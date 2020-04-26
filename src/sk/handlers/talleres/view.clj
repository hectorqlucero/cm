(ns sk.handlers.talleres.view
  (:require [clojure.string :refer [upper-case]]))

(defn reporte-view [title rows]
  (list
    [:div.container
     [:div.col-12.text-center
      [:h3 {:style "color:#fa981b;text-transform:uppercase;font-weight:bold;"} title]]
     (for [row rows]
       [:div.row.col-3
        [:div.col-auto
         [:div.row
          [:div.col-auto
           [:h3.h3-primary {:style "color:#127ba3;font-weight:bold;"} (upper-case (:nombre row))]]]
         [:div.row
          [:div.col-auto
           [:button.btn.btn-info.btn.lg.btn-block {:data-toggle    "popover"
                                                   :data-placement "bottom"
                                                   :data-content   (str (:historia row))} "Historia"]]]
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

(defn reporte-scripts []
  (list
    [:script
     "
     $(function() {
      $('[data-toggle=\"popover\"]').popover();
     });
      "]))
