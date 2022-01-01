(ns sk.proutes
  (:require [compojure.core :refer [GET POST defroutes]]
            [sk.handlers.admin.users.handler :as users]
            [sk.handlers.admin.eventos.handler :as eventos]
            [sk.handlers.admin.rodadas.handler :as rodadas]
            [sk.handlers.admin.aventuras.handler :as aventuras]
            [sk.handlers.admin.fotos.handler :as fotos]
            [sk.handlers.admin.videos.handler :as videos]
            [sk.handlers.admin.frases.handler :as frases]
            [sk.handlers.admin.talleres.handler :as talleres]
            [sk.handlers.admin.cuadrantes.handler :as cuadrantes]))

(defroutes proutes
  ;; Start users
  (GET "/admin/users"  req [] (users/users req))
  (POST "/admin/users" req [] (users/users-grid req))
  (GET "/admin/users/edit/:id" [id] (users/users-form id))
  (POST "/admin/users/save" req [] (users/users-save req))
  (POST "/admin/users/delete" req [] (users/users-delete req))
  ;; End users

  ;; Start eventos
  (GET "/admin/eventos"  req [] (eventos/eventos req))
  (POST "/admin/eventos" req [] (eventos/eventos-grid req))
  (GET "/admin/eventos/edit/:id" [id] (eventos/eventos-form id))
  (POST "/admin/eventos/save" req [] (eventos/eventos-save req))
  (POST "/admin/eventos/delete" req [] (eventos/eventos-delete req))
  ;; End eventos

  ;; Start rodadas
  (GET "/admin/rodadas"  req [] (rodadas/rodadas req))
  (POST "/admin/rodadas" req [] (rodadas/rodadas-grid req))
  (GET "/admin/rodadas/edit/:id" [id] (rodadas/rodadas-form id))
  (POST "/admin/rodadas/save" req [] (rodadas/rodadas-save req))
  (POST "/admin/rodadas/delete" req [] (rodadas/rodadas-delete req))
  ;; End rodadas

  ;; Start aventuras
  (GET "/admin/aventuras"  req [] (aventuras/aventuras req))
  (POST "/admin/aventuras" req [] (aventuras/aventuras-grid req))
  (GET "/admin/aventuras/edit/:id" [id] (aventuras/aventuras-form id))
  (POST "/admin/aventuras/save" req [] (aventuras/aventuras-save req))
  (POST "/admin/aventuras/delete" req [] (aventuras/aventuras-delete req))
  ;; End aventuras

  ;; Start fotos
  (GET "/admin/fotos"  req [] (fotos/fotos req))
  (POST "/admin/fotos" req [] (fotos/fotos-grid req))
  (GET "/admin/fotos/edit/:id" [id] (fotos/fotos-form id))
  (POST "/admin/fotos/save" req [] (fotos/fotos-save req))
  (POST "/admin/fotos/delete" req [] (fotos/fotos-delete req))
  ;; End fotos

  ;; Start videos
  (GET "/admin/videos"  req [] (videos/videos req))
  (POST "/admin/videos" req [] (videos/videos-grid req))
  (GET "/admin/videos/edit/:id" [id] (videos/videos-form id))
  (POST "/admin/videos/save" req [] (videos/videos-save req))
  (POST "/admin/videos/delete" req [] (videos/videos-delete req))
  ;; End videos

  ;; Start frases
  (GET "/admin/frases"  req [] (frases/frases req))
  (POST "/admin/frases" req [] (frases/frases-grid req))
  (GET "/admin/frases/edit/:id" [id] (frases/frases-form id))
  (POST "/admin/frases/save" req [] (frases/frases-save req))
  (POST "/admin/frases/delete" req [] (frases/frases-delete req))
  ;; End frases

  ;; Start talleres
  (GET "/admin/talleres"  req [] (talleres/talleres req))
  (POST "/admin/talleres" req [] (talleres/talleres-grid req))
  (GET "/admin/talleres/edit/:id" [id] (talleres/talleres-form id))
  (POST "/admin/talleres/save" req [] (talleres/talleres-save req))
  (POST "/admin/talleres/delete" req [] (talleres/talleres-delete req))
  ;; End talleres

  ;; Start cuadrantes
  (GET "/admin/cuadrantes"  req [] (cuadrantes/cuadrantes req))
  (POST "/admin/cuadrantes" req [] (cuadrantes/cuadrantes-grid req))
  (GET "/admin/cuadrantes/edit/:id" [id] (cuadrantes/cuadrantes-form id))
  (POST "/admin/cuadrantes/save" req [] (cuadrantes/cuadrantes-save req))
  (POST "/admin/cuadrantes/delete" req [] (cuadrantes/cuadrantes-delete req))
  ;; End cuadrantes
  )
