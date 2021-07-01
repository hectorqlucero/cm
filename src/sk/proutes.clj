(ns sk.proutes
  (:require [compojure.core :refer [defroutes GET POST]]
            [sk.handlers.administrar.aventuras.handler :as aventuras]
            [sk.handlers.administrar.cuadrantes.handler :as cuadrantes]
            [sk.handlers.administrar.eventos.handler :as eventos]
            [sk.handlers.administrar.rodadas.handler :as rodadas]
            [sk.handlers.administrar.talleres.handler :as talleres]
            [sk.handlers.administrar.users.handler :as users]
            [sk.handlers.administrar.fotos.handler :as fotos]
            [sk.handlers.administrar.videos.handler :as videos]))

(defroutes proutes
  ;; Start users
  (GET "/administrar/users"  req [] (users/users req))
  (POST "/administrar/users" req [] (users/users-grid req))
  (GET "/administrar/users/edit/:id" [id] (users/users-form id))
  (POST "/administrar/users/save" req [] (users/users-save req))
  (POST "/administrar/users/delete" req [] (users/users-delete req))
  ;; End users

  ;; Start rodadas
  (GET "/administrar/rodadas" req [] (rodadas/rodadas req))
  (POST "/administrar/rodadas" req [] (rodadas/rodadas-grid req))
  (GET "/administrar/rodadas/edit/:id" [id] (rodadas/rodadas-form id))
  (POST "/administrar/rodadas/save" req [] (rodadas/rodadas-save req))
  (POST "/administrar/rodadas/delete" req [] (rodadas/rodadas-delete req))
  ;; End rodadas

  ;; Start aventuras
  (GET "/administrar/aventuras" req [] (aventuras/aventuras req))
  (POST "/administrar/aventuras" req [] (aventuras/aventuras-grid req))
  (GET "/administrar/aventuras/edit/:id" [id] (aventuras/aventuras-form id))
  (POST "/administrar/aventuras/save" req [] (aventuras/aventuras-save req))
  (POST "/administrar/aventuras/delete" req [] (aventuras/aventuras-delete req))
  ;; End aventuras

  ;; Start eventos
  (GET "/administrar/eventos" req [] (eventos/eventos req))
  (POST "/administrar/eventos" req [] (eventos/eventos-grid req))
  (GET "/administrar/eventos/edit/:id" [id] (eventos/eventos-form id))
  (POST "/administrar/eventos/save" req [] (eventos/eventos-save req))
  (POST "/administrar/eventos/delete" req [] (eventos/eventos-delete req))
  ;; End eventos

  ;; Start talleres
  (GET "/administrar/talleres" req [] (talleres/talleres req))
  (POST "/administrar/talleres" req [] (talleres/talleres-grid req))
  (GET "/administrar/talleres/edit/:id" [id] (talleres/talleres-form id))
  (POST "/administrar/talleres/save" req [] (talleres/talleres-save req))
  (POST "/administrar/talleres/delete" req [] (talleres/talleres-delete req))
  ;; End talleres

  ;; Start cuadrantes
  (GET "/administrar/cuadrantes" req [] (cuadrantes/cuadrantes req))
  (POST "/administrar/cuadrantes" req [] (cuadrantes/cuadrantes-grid req))
  (GET "/administrar/cuadrantes/edit/:id" [id] (cuadrantes/cuadrantes-form id))
  (POST "/administrar/cuadrantes/save" req [] (cuadrantes/cuadrantes-save req))
  (POST "/administrar/cuadrantes/delete" req [] (cuadrantes/cuadrantes-delete req))
  ;; End cuadrantes

  ;; Start fotos
  (GET "/administrar/fotos" req [] (fotos/fotos req))
  (POST "/administrar/fotos" req [] (fotos/fotos-grid req))
  (GET "/administrar/fotos/edit/:id" [id] (fotos/fotos-form id))
  (POST "/administrar/fotos/save" req [] (fotos/fotos-save req))
  (POST "/administrar/fotos/delete" req [] (fotos/fotos-delete req))
  ;; End fotos

  ;; Start videos
  (GET "/administrar/videos" req [] (videos/videos req))
  (POST "/administrar/videos" req [] (videos/videos-grid req))
  (GET "/administrar/videos/edit/:id" [id] (videos/videos-form id))
  (POST "/administrar/videos/save" req [] (videos/videos-save req))
  (POST "/administrar/videos/delete" req [] (videos/videos-delete req))
  ;; End videos
  )
