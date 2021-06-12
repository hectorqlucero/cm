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

; (defn eventos-view [title year]
;   (list
;    [:h1 {:style "text-align:center;margin:0;margin-bottom:0;font-size:2em;font-weight:bold;color:#fa981b;text-transform:uppercase;"} title]
;    [:div.d-flex.flex-wrap.align-content-center.align-content-stretch 
;     [:div.d-flex.justify-content-center
;      [:img.evento_image.img-fluid (build-options "enero" year 1)]]
;     [:div.d-flex.justify-content-center
;      [:img.evento_image.img-fluid (build-options "febrero" year 2)]]
;     [:div.d-flex.justify-content-center
;      [:img.evento_image.img-fluid (build-options "marzo" year 3)]]
;     [:div.d-flex.justify-content-center
;      [:img.evento_image.img-fluid (build-options "abril" year 4)]]
;     [:div.d-flex.justify-content-center
;      [:img.evento_image.img-fluid (build-options "mayo" year 5)]]
;     [:div.d-flex.justify-content-center
;      [:img.evento_image.img-fluid (build-options "junio" year 6)]]
;     [:div.d-flex.justify-content-center
;      [:img.evento_image.img-fluid (build-options "julio" year 7)]]
;     [:div.d-flex.justify-content-center
;      [:img.evento_image.img-fluid (build-options "agosto" year 8)]]
;     [:div.d-flex.justify-content-center
;      [:img.evento_image.img-fluid (build-options "septiembre" year 9)]]
;     [:div.d-flex.justify-content-center
;      [:img.evento_image.img-fluid (build-options "octubre" year 10)]]
;     [:div.d-flex.justify-content-center
;      [:img.evento_image.img-fluid (build-options "noviembre" year 11)]]
;     [:div.d-flex.justify-content-center
;      [:img.evento_image.img-fluid (build-options "diciembre" year 12)]]]))

(defn eventos-view [title year]
  (list
    [:section.p-5.bg-white
     [:div.container
      [:h2.text-center.text-white title]
      [:div.row.g-4
       [:div.col-md-6.col-lg-3
        [:div.card.bg-light
         [:div.card-body.text-center
          [:img.rounded-circle.mb-3 (build-options "enero" year 1)]]]]
       [:div.col-md-6.col-lg-3
        [:div.card.bg-light
         [:div.card-body.text-center
          [:img.rounded-circle.mb-3 (build-options "febrero" year 2)]]]]
       [:div.col-md-6.col-lg-3
        [:div.card.bg-light
         [:div.card-body.text-center
          [:img.rounded-circle.mb-3 (build-options "marzo" year 3)]]]]
       [:div.col-md-6.col-lg-3
        [:div.card.bg-light
         [:div.card-body.text-center
          [:img.rounded-circle.mb-3 (build-options "abril" year 4)]]]]
       [:div.col-md-6.col-lg-3
        [:div.card.bg-light
         [:div.card-body.text-center
          [:img.rounded-circle.mb-3 (build-options "mayo" year 5)]]]]
       [:div.col-md-6.col-lg-3
        [:div.card.bg-light
         [:div.card-body.text-center
          [:img.rounded-circle.mb-3 (build-options "junio" year 6)]]]]
       [:div.col-md-6.col-lg-3
        [:div.card.bg-light
         [:div.card-body.text-center
          [:img.rounded-circle.mb-3 (build-options "julio" year 7)]]]]
       [:div.col-md-6.col-lg-3
        [:div.card.bg-light
         [:div.card-body.text-center
          [:img.rounded-circle.mb-3 (build-options "agosto" year 8)]]]]
       [:div.col-md-6.col-lg-3
        [:div.card.bg-light
         [:div.card-body.text-center
          [:img.rounded-circle.mb-3 (build-options "septiembre" year 9)]]]]
       [:div.col-md-6.col-lg-3
        [:div.card.bg-light
         [:div.card-body.text-center
          [:img.rounded-circle.mb-3 (build-options "octubre" year 10)]]]]
       [:div.col-md-6.col-lg-3
        [:div.card.bg-light
         [:div.card-body.text-center
          [:img.rounded-circle.mb-3 (build-options "noviembre" year 11)]]]]
       [:div.col-md-6.col-lg-3
        [:div.card.bg-light
         [:div.card-body.text-center
          [:img.rounded-circle.mb-3 (build-options "diciembre" year 12)]]]]]]]))

(defn eventos-scripts []
  (list
    [:script
     (str
       "function showEvents(year, month) {
          var url = '/eventos/list/' + year + '/' + month;
          window.location.href = url;
        }
       ")]))

(defn line-rr [label value]
  [:div.row
   (if-not (nil? label)
     [:div.col-xs-4.col-sm-4.col-md-3.col-lg-2.text-primary [:strong label]])
   [:div.col-xs.8.col-sm-8.col-md-9.col-lg-10 value]])

(defn body-rr [row]
  [:h2 (:descripcion_corta row)
   [:div.card
    [:div.card-body {:style "font-size:.5em;"}
     (line-rr nil [:img.card-img-top.mb-3.w-auto {:src (str "/uploads/eventos/" (:imagen row))
                                                  :onError "this.src='/images/placeholder_profile.png'"}])
     (line-rr "Fecha:" (:f_fecha row))
     (line-rr "Detalles: " (:descripcion row))
     (line-rr "Lugar: " (:punto_reunion row))
     (line-rr "Hora: " (:hora row))
     (line-rr "Organiza: " (:leader row))]]])

(defn display-eventos-view [title _ _ rows _]
  (list
    [:div.container
     [:div.col-12.text-center
      [:h3 {:style "color:#fa981b;text-transform:uppercase;font-weight:bold;"} title]
      [:button.btn.btn-primary {:onclick "window.location.href='/eventos/list'"} "Regresar"]]
     (map body-rr rows)]))

(defn display-eventos-scripts [year month]
  (list
    [:script nil ]))
