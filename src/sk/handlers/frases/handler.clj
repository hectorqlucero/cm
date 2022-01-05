(ns sk.handlers.frases.handler
  (:require [sk.layout :refer [application]]
            [sk.models.crud :refer [db Query]]
            [sk.models.util :refer [get-session-id]]))

(def frases-sql
  "
  SELECT * FROM frases ORDER BY autor")

(defn get-rows []
  (Query db frases-sql))

(defn build-frase [row]
  [:p.text-primary  (str (:frase row) " - " (:autor row))])

(defn scripts []
  [:script
   "
   $(document).ready(function() {
    $('.bxslider').bxSlider({
      pager: false,
      mode: 'horizontal',
      controls: true,
      captions: true,
      slideMargin: 10
    });
   });
   "])

(defn build-body [row]
  (list
   [:li  [:img {:src "/images/placeholder.png"
                :title  (str (:frase row) " - " (:autor row))
                :height "180"
                :width "950"
                :style "margin-left:50px;"}]]))

(defn get-frases []
  (let [rows (get-rows)
        first-row (first rows)
        rest-rows (rest rows)]
    (list
     [:div.container
      [:ul.bxslider
       [:li  [:img {:src "/images/placeholder.png"
                    :title (str (:frase first-row) " - " (:autor first-row))
                    :height "180"
                    :width "950"
                    :style "margin-left:50px;"}]]
       (map build-body rest-rows)]])))

(defn frases [_]
  (let [title "Frases de Ciclistas"
        ok (get-session-id)
        js (scripts)
        content (get-frases)]
    (application title ok js content)))

(comment
  (get-frases))
