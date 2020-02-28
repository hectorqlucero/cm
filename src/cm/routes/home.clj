(ns cm.routes.home
  (:require [cheshire.core :refer [generate-string]]
            [hiccup.page :refer [html5]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [cm.models.crud :refer [db
                                            Query]]
            [cm.models.util :refer [get-session-id]]
            [cm.views.layout :refer :all]
            [cm.views.home :refer [login-view
                                           login-scripts]]
            [noir.session :as session]
            [noir.util.crypt :as crypt]
            [noir.response :refer [redirect]]))

;; Start Main
(def main-sql
  "SELECT
   username
   FROM users
   WHERE id = ?")

(defn get-main-title []
  (html5
    [:div {:style "margin-left:20px;
                   margin-right:20px;
                   margin-bottom:20px;"}
     [:h3 "Bienvenido al sitio Ciclismo Mexicali"]
     [:ul
      [:li [:strong "Eventos: "] "Listado de todos los eventos y carreras.  Ex. Paseo Rosarito Ensenada"]
      [:li [:strong "Rodadas: "] "Rodadas organizadas por grupos o particulares.  Ex. Ver las rodadas de Grupos y confirmar asistencia a rodadas."]
      [:li [:strong "Talleres: "] "Talleres de reparación/venta de bicicletas"]
      [:li [:strong "Grupos: "] "Grupos ciclistas ex. Cuadrante Rosita, Sector Ciclista Azul etc..."]
      [:li [:strong "Entrar: "] "Aquí podrás registrarte como miembro y así crear rodadas para invitar a otros ciclistas.  Las rodadas que hayas creado saldrán en el calendario de rodadas y ciclistas podrán confirmar asistencia y recibirás un correo electronico.  Si cancelas la rodada todos los que confirmaron serán notificados con un correo electrónico que la rodada se canceló"]]
     [:br] [:br]
     [:div {:style "margin-left:20px;margin-right:20px;margin-bottom:20px;"}
      [:p "Este sitio es para todos los ciclistas ya sea de Mexicali o cualquiera que venga a Mexicali por causas de trabajo o vacaciones."]
      [:hr]
      [:p "Si itenes alguna sugerencia o necesitas ayuda, estamos para ayudarte"]
      [:p [:a.easyui-linkbutton.c6 {:data-options "plain:false"
                                    :href "mailto:lucero_systems@fastmail.com"} "Mandame un Correo"]]]]))

(defn main [request]
  (let [title (get-main-title)
        ok (get-session-id)
        content [:div [:span {:style "margin-left:20px;"} (get-main-title)]] ]
    (application "Mi Inventario" ok nil content)))
;; End Main

;; Start Login
(defn login [_]
  (let [title "Accesar al Sitio"
        ok (get-session-id)
        content (login-view (anti-forgery-field))
        scripts (login-scripts)]
    (if-not (= (get-session-id) 0)
      (redirect "/")
      (application title ok scripts content))))

(defn login! [username password]
  (let [row (first (Query db ["SELECT * FROM users WHERE username = ?" username]))
        active (:active row)]
    (if (= active "T")
      (do
        (if (crypt/compare password (:password row))
          (do
            (session/put! :user_id (:id row))
            (generate-string {:url "/"}))
          (generate-string {:error "Hay problemas para accesar el sitio!"})))
      (generate-string {:error "El usuario esta inactivo!"}))))
;; End login

(defn logoff []
  (session/clear!)
  (redirect "/"))
