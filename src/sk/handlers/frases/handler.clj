(ns sk.handlers.frases.handler
  (:require [hiccup.page :refer [html5]]
            [sk.layout :refer [application]]
            [sk.models.crud :refer [db Query]]
            [sk.models.util :refer [get-session-id]]))

(def frases-sql
  "
  SELECT * FROM frases")

(defn get-rows []
  (Query db frases-sql))

(defn process-row [frase]
  [:marquee.news-scroll {:behavior "scroll"
                         :direction "left"
                         :onmouseover "this.stop();"
                         :onmouseout "this.start();"}
   [:a {:href "#"} frase]])

(defn handle-body []
  (let [rows (get-rows)
        frase (->> (get-rows)
                   (map #(str "[&nbsp;" (:frase %) " - " (:autor %) "&nbsp;]" "&nbsp;&nbsp;"))
                   (apply str))]
    (process-row frase)))

(defn get-frases []
  (html5
   [:div.container.mt-5
    [:div.row
     [:div.col-md-12
      [:div.d-flex.justify-content-between.align-items-center.breaking-news.bg-white
       [:div.d-flex.flex-row.flex-grow-q.flex-fill.justify-content.center.bg-danger.py-2.text-white.px-1.news
        [:span.d-flex.align-items-center "&nbsp;Frases: "]]
       (handle-body)]]]]))

(defn frases [_]
  (let [title "Frases de Ciclistas"
        ok (get-session-id)
        content (get-frases)]
    (application title ok nil content)))
