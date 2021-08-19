(ns sk.handlers.frases.handler
  (:require [hiccup.page :refer [html5 include-js]]
            [sk.layout :refer [application]]
            [sk.models.crud :refer [db Query]]
            [sk.models.util :refer [get-session-id]]))

(def frases-sql
  "
  SELECT * FROM frases")

(defn get-rows []
  (Query db frases-sql))

(defn build-frase [row]
  [:p.text-primary  (str (:frase row) " - " (:autor row))])

(defn get-frases []
  (let [rows (get-rows)]
    (html5
     [:body {:style "background-color:black;
                    position: fixed;
                    left: 0px;
                    right: 0px;
                    top: 0px;
                    bottom: 0px;
                    overflow: hidden;
                    margin: 0;
                    padding: 0"}
      [:canvas#canvas.stretch]
      [:div#crawl-container.stretch
       [:div#crawl
        [:div#crawl-content
         [:h1 "Frases"]
         [:h2 "De Ciclistas"]
         (map build-frase rows)]]]])))

(defn frases [_]
  (let [title "Frases de Ciclistas"
        ok (get-session-id)
        js (include-js "/js/stars.js")
        content (get-frases)]
    (application title ok js content)))
