(ns sk.proutes
  (:require [compojure.core :refer [defroutes GET POST]]
            [sk.handlers.administrar.rodadas.handler :as rodadas]
            [sk.handlers.administrar.aventuras.handler :as aventuras]
            [sk.handlers.administrar.eventos.handler :as eventos]
            [sk.handlers.administrar.talleres.handler :as talleres]
            [sk.handlers.administrar.cuadrantes.handler :as cuadrantes]))

(defroutes proutes
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
  )
