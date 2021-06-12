(ns sk.handlers.administrar.eventos.handler
  (:require [sk.handlers.administrar.eventos.view
             :refer
             [eventos-scripts eventos-view]]
            [sk.layout :refer [application]]
            [sk.models.crud
             :refer
             [build-form-delete build-form-row build-form-save]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util :refer [current_year get-session-id]]))

(defn eventos
  [_]
  (try
    (let [title   "Calendario - Eventos"
          ok      (get-session-id)
          js      (eventos-scripts)
          content (eventos-view title)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn eventos-grid
  [{params :params}]
  (try
    (let [table "eventos"
          args  {:sort-extra   "fecha,hora"
                 :search-extra (str "YEAR(fecha) = '" (current_year) "'")}]
      (build-grid params table args))
    (catch Exception e (.getMessage e))))

(defn eventos-form
  [id]
  (try
    (let [table "eventos"]
      (build-form-row table id))
    (catch Exception e (.getMessage e))))

(defn eventos-save
  [{params :params}]
  (try
    (let [table         "eventos"
          upload-folder "eventos"]
      (build-form-save params table upload-folder))
    (catch Exception e (.getMessage e))))

(defn eventos-delete
  [{params :params}]
  (try
    (let [table "eventos"]
      (build-form-delete params table))
    (catch Exception e (.getMessage e))))
