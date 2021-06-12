(ns sk.handlers.home.handler
  (:require [cheshire.core :refer [generate-string]]
            [hiccup.page :refer [html5]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [sk.models.crud :refer [db
                                    Query]]
            [sk.models.util :refer [get-session-id]]
            [sk.layout :refer :all]
            [sk.handlers.home.view :refer [login-view login-script]]
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
     [:li [:strong "Eventos: "] "Listado de todos los eventos y carreras.  Ej. Paseo Rosarito Ensenada"]
     [:li [:strong "Rodadas: "] "Rodadas organizadas por grupos o particulares.  Ej. Ver las rodadas de Grupos y confirmar asistencia a rodadas."]
     [:li [:strong "Aventuras: "] "Aquí podrás escribir acerca de tus aventuras en el ciclismo o en alguna rodada donde participes."]
     [:li [:strong "Talleres: "] "Talleres de reparación/venta de bicicletas"]
     [:li [:strong "Grupos: "] "Grupos ciclistas ej. Cuadrante Rosita, Sector Ciclista Azul etc..."]
     [:li [:strong "Entrar: "] "Aquí podrás registrarte como miembro y así crear rodadas para invitar a otros ciclistas.  Las rodadas que hayas creado saldrán en el calendario de rodadas y ciclistas podrán confirmar asistencia y recibirás un correo electronico.  Si cancelas la rodada todos los que confirmaron serán notificados con un correo electrónico que la rodada se canceló"]]
    [:br] [:br]
    [:div {:style "margin-left:20px;margin-right:20px;margin-bottom:20px;"}
     [:p "Este sitio es para todos los ciclistas ya sea de Mexicali o cualquiera que venga a Mexicali por causas de trabajo o vacaciones."]
     [:hr]
     [:p "Si itenes alguna sugerencia o necesitas ayuda, estamos para ayudarte"]
     [:p [:a.easyui-linkbutton.bg-secondary {:data-options "plain:true"
                                :style "background:#fafafa;text-color:#0000"
                                :href "mailto:lucero_systems@fastmail.com"} "Mandame un Correo"]]]]))

(defn main [_]
  (let [title   (get-main-title)
        ok      (get-session-id)
        content [:div [:span {:style "margin-left:20px;"} title]]]
    (application "CM" ok nil content)))
;; End Main

;; Start Login
(defn login [_]
  (let [title "Accesar al Sitio"
        ok (get-session-id)
        content (login-view (anti-forgery-field))
        scripts (login-script)]
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
  (redirect "https://ciclismomexicali.org"))
