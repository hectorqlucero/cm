(ns sk.handlers.home.handler
  (:require [cheshire.core :refer [generate-string]]
            [noir.response :refer [redirect]]
            [noir.session :as session]
            [noir.util.crypt :as crypt]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [sk.handlers.home.view :refer [login-script login-view]]
            [sk.layout :refer [application error-404]]
            [sk.models.crud :refer [Query config db]]
            [sk.models.util :refer [get-session-id]]))

;; Start Main
(defn get-rides0 []
  (str
   "
<iframe allowtransparency frameborder='0' height='160' scrolling='no' src='https://www.strava.com/clubs/1017948/latest-rides/a81e6af10bcf08603aa9226fa75b0b41f600251e?show_rides=false' width='300'></iframe>
    "))
(defn get-rides1 []
  (str
   "
<iframe allowtransparency frameborder='0' height='454' scrolling='no' src='https://www.strava.com/clubs/1017948/latest-rides/a81e6af10bcf08603aa9226fa75b0b41f600251e?show_rides=true' width='300'></iframe>
    "))

(defn get-rides []
  [:div.row
   [:div.col
    [:p (get-rides0)]]
   [:div.col (get-rides1)]])

(defn main
  [_]
  (try
    (let [title (:site config)
          ok (get-session-id)
          content (get-rides)]
      (application title ok nil content))
    (catch Exception e (.getMessage e))))

;; Start Login
(defn login
  [_]
  (try
    (let [title "Conectar"
          ok (get-session-id)
          content (login-view (anti-forgery-field))
          scripts (login-script)]
      (if-not (= (get-session-id) 0)
        (redirect "/")
        (application title ok scripts content)))
    (catch Exception e (.getMessage e))))

(defn login!
  [username password]
  (try
    (let [row (first (Query db ["SELECT * FROM users WHERE username = ?" username]))
          active (:active row)]
      (if (= active "T")
        (if (crypt/compare password (:password row))
          (do
            (session/put! :user_id (:id row))
            (generate-string {:url "/"}))
          (generate-string {:error "Incapaz de accesar al sitio!"}))
        (generate-string {:error "El usuario esta inactivo!"})))
    (catch Exception e (.getMessage e))))
;; End login

(defn logoff
  []
  (try
    (session/clear!)
    (error-404 "Salida del sitio con exito!" "/")
    (catch Exception e (.getMessage e))))
