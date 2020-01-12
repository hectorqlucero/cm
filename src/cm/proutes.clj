(ns cm.proutes
  (:require [cm.proutes.rodadas :as rodadas]
            [cm.proutes.eventos :as eventos]
            [cm.proutes.talleres :as talleres]
            [cm.proutes.cuadrantes :as cuadrantes]
            [compojure.core :refer [defroutes GET POST]]))

(defroutes proutes
  ;; Start Rodadas
  (GET "/rodadas/crear" request [] (rodadas/rodadas request))
  (POST "/rodadas/crear" request [] (rodadas/grid-rodadas request))
  (GET "/rodadas/crear/edit/:id" [id] (rodadas/form-rodadas id))
  (POST "/rodadas/crear/save" request [] (rodadas/rodadas-save request))
  (POST "/rodadas/crear/delete" request [] (rodadas/rodadas-delete request))
  ;; End rodadas
  ;; Start eventos
  (GET "/eventos/crear" request [] (eventos/eventos request))
  (POST "/eventos/crear" request [] (eventos/eventos-grid request))
  (GET "/eventos/crear/edit/:id" [id] (eventos/eventos-form id))
  (POST "/eventos/crear/save" request [] (eventos/eventos-save request))
  (POST "/eventos/crear/delete" request [] (eventos/eventos-delete request))
  ;; End eventos
  ;; Start talleres
  (GET "/talleres/crear" request [] (talleres/talleres request))
  (POST "/talleres/crear" request [] (talleres/talleres-grid request))
  (GET "/talleres/crear/edit/:id" [id] (talleres/talleres-form id))
  (POST "/talleres/crear/save" request [] (talleres/talleres-save request))
  (POST "/talleres/crear/delete" request [] (talleres/talleres-delete request))
  ;; End talleres
  ;; Start cuadrantes
  (GET "/cuadrantes/crear" request [] (cuadrantes/cuadrantes request))
  (POST "/cuadrantes/crear" request [] (cuadrantes/cuadrantes-grid request))
  (GET "/cuadrantes/crear/edit/:id" [id] (cuadrantes/cuadrantes-form id))
  (POST "/cuadrantes/crear/save" request [] (cuadrantes/cuadrantes-save request))
  (POST "/cuadrantes/crear/delete" request [] (cuadrantes/cuadrantes-delete request))
  ;; End cuadrantes
  )
