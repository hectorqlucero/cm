(ns sk.handlers.aventuras.view)

(defn line-rr [label value]
  [:div.row
   [:div.col-xs-4.col-sm-4.col-md-3.col-lg-2.text-primary [:strong label]]
   [:div.col-xs.8.col-sm-8.col-md-9.col-lg-10 value]])

(defn body-rr [row]
  [:h2 (:nombre row )
   [:div.card
    [:div.card-body {:style "font-size:.5em"}
     (line-rr "Fecha:" [:strong.text-warning (str (clojure.string/upper-case (:dia row)) (clojure.string/upper-case (:f_fecha row)))])
     (line-rr "Aventura:" (:aventura row))]]])

(defn aventuras-view [rows]
  (list
    [:div.container
     (map body-rr rows)]))

(defn aventuras-scripts []
  [:script])
