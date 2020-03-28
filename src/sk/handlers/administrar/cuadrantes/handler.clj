(ns sk.handlers.administrar.cuadrantes.handler
  (:require [sk.models.crud :refer [build-form-row
                                    build-form-save
                                    build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util :refer [get-session-id
                                    user-email
                                    user-level]]
            [sk.layout :refer [application]]
            [sk.handlers.administrar.cuadrantes.view :refer [cuadrantes-view cuadrantes-scripts]]))

(defn cuadrantes
  [_]
  (try
    (let [title "Talleres de bicicleta"
          ok (get-session-id)
          js (cuadrantes-scripts)
          content (cuadrantes-view title)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn cuadrantes-grid
  [{params :params}]
  (try
    (let [table "cuadrantes"
          args {:sort-extra "name"}]
      (build-grid params table args))
    (catch Exception e (.getMessage e))))

(defn cuadrantes-form
  [id]
  (try
    (let [table "cuadrantes"]
      (build-form-row table id))
    (catch Exception e (.getMessage e))))

(defn cuadrantes-save
  [{params :params}]
  (try
    (let [table "cuadrantes"
          upload-folder "cuadrantes"]
      (build-form-save params table upload-folder))
    (catch Exception e (.getMessage e))))

(defn cuadrantes-delete
  [{params :params}]
  (try
    (let [table "cuadrantes"]
      (build-form-delete params table))
    (catch Exception e (.getMessage e))))
