(ns sk.handlers.eventos.view
  (:require [clojure.java.io :as io])
  (:import [java.util UUID]))

(defn read-image [source]
  (let [bin (io/file source)]
    (io/reader bin)))

(defn build-options [month-name year month]
  {:width   152
   :height  152
   :style   "cursor:pointer;cursor:hand;"
   :src     (str "/images/Calendario_" month-name ".jpeg")
   :onclick (str "showEvents(" year "," month ")")})

(defn eventos-view [title year]
  (list
   [:h1 {:style "text-align:center;margin:0;margin-bottom:0;font-size:2em;font-weight:bold;color:#fa981b;text-transform:uppercase;"} title]
   [:div.d-flex.flex-wrap.align-content-center.align-content-stretch 
    [:div.d-flex.justify-content-center
     [:img.evento_image.img-fluid (build-options "enero" year 1)]]
    [:div.d-flex.justify-content-center
     [:img.evento_image.img-fluid (build-options "febrero" year 2)]]
    [:div.d-flex.justify-content-center
     [:img.evento_image.img-fluid (build-options "marzo" year 3)]]
    [:div.d-flex.justify-content-center
     [:img.evento_image.img-fluid (build-options "abril" year 4)]]
    [:div.d-flex.justify-content-center
     [:img.evento_image.img-fluid (build-options "mayo" year 5)]]
    [:div.d-flex.justify-content-center
     [:img.evento_image.img-fluid (build-options "junio" year 6)]]
    [:div.d-flex.justify-content-center
     [:img.evento_image.img-fluid (build-options "julio" year 7)]]
    [:div.d-flex.justify-content-center
     [:img.evento_image.img-fluid (build-options "agosto" year 8)]]
    [:div.d-flex.justify-content-center
     [:img.evento_image.img-fluid (build-options "septiembre" year 9)]]
    [:div.d-flex.justify-content-center
     [:img.evento_image.img-fluid (build-options "octubre" year 10)]]
    [:div.d-flex.justify-content-center
     [:img.evento_image.img-fluid (build-options "noviembre" year 11)]]
    [:div.d-flex.justify-content-center
     [:img.evento_image.img-fluid (build-options "diciembre" year 12)]]]))

(defn eventos-scripts []
  (list
    [:script
     (str
       "function showEvents(year, month) {
          var url = '/eventos/list/' + year + '/' + month;
          window.location.href = url;
        }
       ")]))

(defn display-eventos-view [title _ _ rows _]
  (list
     [:div.col-12.text-center
      [:h3 {:style "color:#fa981b;text-transform:uppercase;font-weight:bold;"} title]
      [:button.btn.btn-primary {:onclick "window.location.href='/eventos/list'"} "Regresar"]]
    (for [row rows]
      (list
        [:div.row.col-12
         [:div.col-auto
          [:a {:href "#"}
           [:button.btn.btn-primary.btn-lg (:day row)]]]
         [:div.col-auto
          [:div.row
           [:div.col-auto
            [:h3.h3-primary {:style "color:#127ba3;font-weight:bold;"} (:descripcion_corta row)]
            [:button.btn.btn-info.btn-block {:data-toggle    "popover"
                                             :data-html      "true"
                                             :data-content   (:descripcion row)
                                             :style          "width:150px;"} "clic para Detalles"]]]
          [:div.row
           [:div.col-auto
            [:img {:src     (str "/uploads/eventos/" (row :imagen) "?t=" (str (UUID/randomUUID)))
                   :id      (row :id)
                   :width   "99"
                   :height  "80"
                   :style   "cursor: pointer;"
                   :onclick (str "resize_image(" (row :id) ")")
                   :onError "this.src='/images/placeholder_profile.png'"}]]]
          [:div.row
           [:div.col-auto
            [:strong "LUGAR"]
            [:span.vertical-divider-secondary {:style "color:#fa981b;font-weight:bold"} " | "]
            (:punto_reunion row)]]
          [:div.row
           [:div.col-auto
            [:strong "FECHA"]
            [:span.vertical-divider-secondary {:style "color:#fa981b;font-weight:bold;"} " | "]
            (:fecha row) " (" [:span {:style "color:rgb(18,123,163);"} (:fecha_dow row)] ")"]]
          [:div.row
           [:div.col-auto
            [:strong "HORA"]
            [:span.vertical-divider-secondary {:style "color:#fa981b;font-weight:bold;"} "&nbsp;&nbsp;| "]
            (:hora row)]]
          [:div.row.warning
           [:div.col-auto {:style "color:#127ba3;"}]
           [:span {:style "color:#127ba3;"} (:leader row)]]]]
        [:br]
        [:div.row [:div.col-auto "&nbsp;"]]))))

(defn display-eventos-scripts [year month]
  (list
    [:script
     (str
       "
       function resize_image(id) {
          var img = $('#'+id);
          if(img.width() < 500) {
          img.animate({width: '500', height: '500'}, 1000);
          } else {
          img.animate({width: img.attr(\"width\"), height: img.attr(\"height\")}, 1000);
          }
       }
       $(function() {
        $('[data-toggle=\"popover\"]').popover()
       })

       function printPDF() {
        var year = " year ";
        var month = " month ";
        url = '/eventos/print' + year + '/' + month;
        window.location.href = url;
       }
       ")]))
