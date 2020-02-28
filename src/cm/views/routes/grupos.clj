(ns cm.views.routes.grupos
  (:require [hiccup.page :refer [include-css include-js]]))

(defn reporte-view [title rows]
  (list
    (for [row rows]
      [:div.row
       [:div.col-auto
        [:div.row
         [:div.col-auto
          [:h3.h3-primary {:style "color:#127ba3;font-weight:bold;"} (clojure.string/upper-case (:name row))]]]
        [:div.row
         [:div.col-auto
          [:button.btn.btn-info.btn.lg.btn-block {:data-toggle    "popover"
                                                  :data-placement "right"
                                                  :data-content   (str (:notes row))} "Información"]]]
        [:div.row
         [:div.col-auto
          [:strong "LEADER"]
          [:span.vertical-divider-secondary {:style "color:#fa981b;"} "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp|&nbsp;"]
          (str (:leader row))]]
        [:div.row
         [:div.col-auto
          [:strong "TELEFONO"]
          [:span.vertical-divider-secondary {:style "color:#fa981b;"} "&nbsp;&nbsp;&nbsp;|&nbsp;"]
          (str (:leader_phone row))]]
        [:div.row
         [:div.col-auto
          [:strong "CELULAR"]
          [:span.vertical-divider-secondary {:style "color:#fa981b;"} "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;"]
          (str (:leader_cell row))]]
        [:div.row
         [:div.col-auto
          [:strong "EMAIL"]
          [:span.vertical-divider-secondary {:style "color:#fa981b;"} "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;"]
          (str (:leader_email row))]]
        [:div.row
         [:div.col-auto
          [:strong "STATUS"]
          [:span.vertical-divider-secondary {:style "color:#fa981b;"} "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp|&nbsp;"]
          (str (:status row))]]
        [:div.row
         [:div.col-auto
          "&nbsp;"]]]])))

(defn reporte-scripts []
  (list
    [:script
     "
     $(function() {
      $('[data-toggle=\"popover\"]').popover();
     });
      "]))
