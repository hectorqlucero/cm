(ns sk.routes
  (:require [cheshire.core :refer [generate-string]]
            [compojure.core :refer [defroutes GET POST]]
            [sk.handlers.home.handler :as home]
            [sk.handlers.registrar.handler :as registrar]
            [sk.handlers.tref.handler :as table_ref]
            [sk.handlers.fotos.handler :as fotos]
            [sk.handlers.videos.handler :as videos]
            [sk.handlers.frases.handler :as frases]
            [sk.handlers.talleres.handler :as talleres]
            [sk.handlers.grupos.handler :as grupos]
            [sk.handlers.aventuras.handler :as aventuras]
            [sk.handlers.eventos.handler :as eventos]
            [sk.handlers.rodadas.handler :as rodadas]))

(defroutes open-routes
  ;; Start table_ref
  (GET "/table_ref/get_users" [] (generate-string (table_ref/get-users)))
  (GET "/table_ref/validate_email/:email" [email] (generate-string (table_ref/get-users-email email)))
  (GET "/table_ref/months" [] (generate-string (table_ref/months)))
  (GET "/table_ref/years/:pyears/:nyears" [pyears nyears] (generate-string (table_ref/years pyears nyears)))
  (GET "/table_ref/get-item/:table/:field/:fname/:fval" [table field fname fval] (table_ref/get-item table field fname fval))
  (GET "/table_ref/get-time" [] (generate-string (table_ref/build-time)))
  (GET "/table_ref/levels" [] (generate-string (table_ref/level-options)))
  (GET "/table_ref/get-titulo/:id" [id] (table_ref/get-titulo id))
  (GET "/table_ref/get-titulos" [] (generate-string (table_ref/get-titulos)))
  (GET "/table_ref/get-paises" [] (generate-string (table_ref/get-pais)))
  (GET "/table_ref/get-pais/:id" [id] (table_ref/get-pais-id id))
  (GET "/table_ref/get-cmt" [] (generate-string (table_ref/get-cmt)))
  ;; End table_ref

  ;; Start home
  (GET "/" request [] (home/main request))
  (GET "/home/login" request [] (home/login request))
  (POST "/home/login" [username password] (home/login! username password))
  (GET "/home/logoff" [] (home/logoff))
  ;; End home

  ;; Start registrar
  (GET "/register" request [] (registrar/registrar request))
  (POST "/register" request [] (registrar/registrar! request))
  (GET "/rpaswd" request [] (registrar/reset-password request))
  (POST "/rpaswd" request [] (registrar/reset-password! request))
  (GET "/reset_password/:token" [token] (registrar/reset-jwt token))
  (POST "/reset_password" request [] (registrar/reset-jwt! request))
  ;; End registrar

  ;; Start aventuras
  (GET "/aventuras/:id" [id] (aventuras/aventuras id))
  ;; End aventuras

  ;; Start eventos
  (GET "/eventos/list" req [] (eventos/eventos req))
  (GET "/eventos/list/:year/:month" [year month] (eventos/display-eventos year month))
  ;; End eventos

  ;; Start rodadas
  (GET "/rodadas/list" req [] (rodadas/rr req))
  (GET "/rodadas/asistir/:id" [id] (rodadas/asistir id))
  (POST "/rodadas/asistir" req [] (rodadas/asistir! req))
  ;; End rodadas

  ;; Start fotos
  (GET "/fotos/list" req [] (fotos/fotos req))
  ;; End fotos

  ;; Start videos
  (GET "/videos/list" req [] (videos/videos req))
  ;; End videos

  ;; Start frases
  (GET "/frases/list" req [] (frases/frases req))
  ;; End frases

  ;; Start talleres
  (GET "/talleres/list" req [] (talleres/reporte req))
  ;; End talleres

  ;; Start grupos
  (GET "/grupos/list" req [] (grupos/reporte req))
  ;; End grupos
  )
