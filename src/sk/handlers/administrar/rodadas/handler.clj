(ns sk.handlers.administrar.rodadas.handler
  (:require [sk.handlers.administrar.rodadas.view
             :refer
             [rodadas-scripts rodadas-view]]
            [sk.layout :refer [application]]
            [sk.models.crud
             :refer
             [build-form-delete build-form-row build-form-save]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util
             :refer
             [current_year get-session-id user-email user-level]]))

(defn rodadas
  [_]
  (try
    (let [title   "Entrenamiento - Rodadas"
          ok      (get-session-id)
          js      (rodadas-scripts)
          content (rodadas-view title)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn rodadas-grid
  [{params :params}]
  (try
    (let [table "rodadas"
          args  (if (= (user-level) "U")
                  {:sort-extra   "fecha,salida" 
                   :search-extra (str "leader_email = '" (user-email) "' AND YEAR(fecha) = '" (current_year) "'")}
                  {:sort-extra   "fecha,salida"
                   :search-extra (str "YEAR(fecha) = '" (current_year) "'")})]
      (build-grid params table args))
    (catch Exception e (.getMessage e))))

(defn rodadas-form
  [id]
  (try
    (let [table "rodadas"]
      (build-form-row table id))
    (catch Exception e (.getMessage e))))

(defn rodadas-save
  [{params :params}]
  (try
    (let [table         "rodadas"
          upload-folder "rodadas"]
      (build-form-save params table upload-folder))
    (catch Exception e (.getMessage e))))

(defn rodadas-delete
  [{params :params}]
  (try
    (let [table "rodadas"]
      (build-form-delete params table))
    (catch Exception e (.getMessage e))))
