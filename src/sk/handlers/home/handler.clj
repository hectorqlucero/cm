(ns sk.handlers.home.handler
  (:require [cheshire.core :refer [generate-string]]
            [noir.response :refer [redirect]]
            [noir.session :as session]
            [noir.util.crypt :as crypt]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [sk.handlers.home.view :refer [login-script login-view]]
            [sk.layout :refer [application]]
            [sk.models.crud :refer [Query config db]]
            [sk.models.util :refer [get-session-id]]))

;; Start Main
(def main-sql
  "SELECT
   username
   FROM users
   WHERE id = ?")

(defn get-main-title []
  [:div {:style "margin-left:20px;
                margin-right:20px;
                margin-bottom:20px;"}
   [:h3 "Bienvenido al sitio Ciclismo Mexicali"]
   [:ul
    [:li [:strong "Eventos: "] "Listado de todos los eventos y carreras.  Ej. Paseo Rosarito Ensenada"]
    [:li [:strong "Rodadas: "] "Rodadas organizadas por grupos o particulares.  Ej. Ver las rodadas de Grupos y confirmar asistencia a rodadas."]
    [:li [:strong "Aventuras: "] "Aquí podrás escribir acerca de tus aventuras en el ciclismo o en alguna rodada donde participes."]
    [:li [:strong "Fotos: "] "Aqui puedes ver las fotos de las rodadas de Ciclismo Mexicali"]
    [:li [:strong "Videos: "] "Aqui puedes ver los de las rodadas de Ciclismo Mexicali"]
    [:li [:strong "Frases de ciclistas: "] "Aqui puedes ver frases de ciclistas"]
    [:li [:strong "Talleres: "] "Talleres de reparación/venta de bicicletas"]
    [:li [:strong "Grupos: "] "Grupos ciclistas ej. Cuadrante Rosita, Sector Ciclista Azul etc..."]
    [:li [:strong "Entrar: "] "Aquí podrás registrarte como miembro y así crear rodadas para invitar a otros ciclistas.  Las rodadas que hayas creado saldrán en el calendario de rodadas y ciclistas podrán confirmar asistencia y recibirás un correo electronico.  Si cancelas la rodada todos los que confirmaron serán notificados con un correo electrónico que la rodada se canceló"]]
   [:br] [:br]
   [:div {:style "margin-left:20px;margin-right:20px;margin-bottom:20px;"}
    [:p [:a.btn.btn-secondary {:href "https://www.facebook.com/ciclismourbanomexicali"
                               :target "_blank"} [:strong.text-secondary "Clic aqui para ir a la pagina de facebook."]]]
    [:p "Este sitio es para todos los ciclistas ya sea de Mexicali o cualquiera que venga a Mexicali por causas de trabajo o vacaciones."]
    [:hr]
    [:p "Si tienes alguna sugerencia o necesitas ayuda, estamos para ayudarte"]
    [:p [:a.btn.btn-secondary {:href "mailto:lucero_systems@fastmail.com"
                               :target "_blank"} [:stron.text-secondary "Mandame un Correo"]]]]])

(defn main
  [_]
  (try
    (let [title (:site config)
          ok (get-session-id)
          content [:div [:span {:style "margin-left:20px;"} (get-main-title)]]]
      (application title ok nil content))
    (catch Exception e (.getMessage e))))
;; End Main

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
    (redirect "/")
    (catch Exception e (.getMessage e))))
