(ns sk.handlers.aventuras.view
  (:require [clojure.string :refer [upper-case]]
            [sk.migrations :refer [config]]))

(defn get-imagen [row]
  (if-not (nil? (:imagen row))
    (str (:path config) (:imagen row) "?" (.toString (java.util.UUID/randomUUID)))
    (str "/images/placeholder_profile.png")))

(defn line-rr [label value]
  [:div.row
   [:div.col-xs-4.col-sm-4.col-md-3.col-lg-2.text-primary [:strong label]]
   [:div.col-xs.8.col-sm-8.col-md-9.col-lg-10 value]])

(defn body-rr [row]
  (let [imagen (get-imagen row)]
    [:div.container.border.border-dark.rounded {:style "margin-bottom:10px;"}
     [:h2.card-title [:img.rounded-circle {:id (str "img" (:id row))
                                           :style "height:65px;width:65px;margin-top:5px;margin-right:16px"
                                           :src imagen
                                           :width 95
                                           :height 71}] (:nombre row)]
     (line-rr "Fecha:" [:strong.text-warning (str (upper-case (:dia row)) (upper-case (:f_fecha row)))])
     (when (:enlace row)
       (line-rr "Fotos:" [:a.btn.btn-secondary
                          {:href (:enlace row)
                           :data-options "plain:true"
                           :target "_blank"} [:strong.text-secondary "Clic aqui para ver fotos!"]]))
     (when (:enlacev row)
       (line-rr "Videos:" [:a.btn.btn-secondary
                           {:href (:enlacev row)
                            :data-options "plain:true"
                            :target "_blank"} [:strong.text-secondary "Clic aqui para ver videos!"]]))
     (line-rr "Aventura:" (:aventura row)) [:br]]))

(defn aventuras-view [rows crow]
  (list
   [:div.row
    [:div.col [:h4.strong {:style "font-style:italic;
                                  margin:10px;
                                  padding:2px;
                                  color:black;
                                  text-align: justify;
                                  text-justify: inter-word;"} (:comments crow)]]]
   [:div.row
    [:div.col
     [:div.card
      (map body-rr rows)]]]))

(defn aventuras-scripts []
  [:script])
