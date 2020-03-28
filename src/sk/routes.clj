(ns sk.routes
  (:require [compojure.core :refer [defroutes GET POST]]
            [cheshire.core :refer [generate-string]]
            [sk.handlers.home.handler :as home]
            [sk.handlers.registrar.handler :as registrar]
            [sk.handlers.tref.handler :as table_ref]
            [sk.handlers.eventos.handler :as eventos]
            [sk.handlers.rodadas.handler :as rodadas]
            [sk.handlers.talleres.handler :as talleres]
            [sk.handlers.grupos.handler :as grupos]))

(defroutes open-routes
  ;; Start table_ref
  (GET "/table_ref/get_users" [] (generate-string (table_ref/get-users)))
  (GET "/table_ref/validate_email/:email" [email] (generate-string (table_ref/get-users-email email)))
  (GET "/table_ref/months" [] (generate-string (table_ref/months)))
  (GET "/table_ref/years/:pyears/:nyears" [pyears nyears] (generate-string (table_ref/years pyears nyears)))
  (GET "/table_ref/calendar" [] (generate-string (table_ref/calendar-events)))
  (GET "/table_ref/nivel_options" [] (generate-string (table_ref/nivel-options)))
  (GET "/table_ref/get_imagen/:id" [id] (table_ref/imagen "eventos" "imagen" "id" id "eventos/"))
  (GET "/table_ref/get-item/:table/:field/:fname/:fval" [table field fname fval] (table_ref/get-item table field fname fval))
  ;; End table_ref

  ;; Start home
  (GET "/" request [] (home/main request))
  (GET "/home/login" request [] (home/login request))
  (POST "/home/login" [username password] (home/login! username password))
  (GET "/home/logoff" [] (home/logoff))
  ;; End home

  ;; Start registrar
  (GET "/registrar" request [] (registrar/registrar request))
  (POST "/registrar" request [] (registrar/registrar! request))
  (GET "/rpaswd" request [] (registrar/reset-password request))
  (POST "/rpaswd" request [] (registrar/reset-password! request))
  (GET "/reset_password/:token" [token] (registrar/reset-jwt token))
  (POST "/reset_password" request [] (registrar/reset-jwt! request))
  ;; End registrar

  ;; Start eventos
  (GET "/eventos/list" request [] (eventos/eventos request))
  (GET "/eventos/list/:year/:month" [year month] (eventos/display-eventos year month))
  ;; End eventos

  ;; Start rodadas
  (GET "/rodadas/list" req [] (rodadas/rodadas req))
  (GET "/rodadas/asistir/:id" [id] (rodadas/asistir id))
  (POST "/rodadas/asistir" req [] (rodadas/asistir! req))
  ;; End rodadas

  ;; Start talleres
  (GET "/talleres/list" req [] (talleres/reporte req))
  ;; End talleres

  ;; Start cuadrantes
  (GET "/grupos/list" req [] (grupos/reporte req))
  ;; End cuadrantes
  )