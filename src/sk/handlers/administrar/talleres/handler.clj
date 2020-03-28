(ns sk.handlers.administrar.talleres.handler
  (:require [sk.models.crud :refer [build-form-row
                                    build-form-save
                                    build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util :refer [get-session-id
                                    user-email
                                    user-level]]
            [sk.layout :refer [application]]
            [sk.handlers.administrar.talleres.view :refer [talleres-view talleres-scripts]]))

(defn talleres
  [_]
  (try
    (let [title "Talleres de bicicleta"
          ok (get-session-id)
          js (talleres-scripts)
          content (talleres-view title)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn talleres-grid
  [{params :params}]
  (try
    (let [table "talleres"
          args {:sort-extra "nombre"}]
      (build-grid params table args))
    (catch Exception e (.getMessage e))))

(defn talleres-form
  [id]
  (try
    (let [table "talleres"]
      (build-form-row table id))
    (catch Exception e (.getMessage e))))

(defn talleres-save
  [{params :params}]
  (try
    (let [table "talleres"
          upload-folder "talleres"]
      (build-form-save params table upload-folder))
    (catch Exception e (.getMessage e))))

(defn talleres-delete
  [{params :params}]
  (try
    (let [table "talleres"]
      (build-form-delete params table))
    (catch Exception e (.getMessage e))))
