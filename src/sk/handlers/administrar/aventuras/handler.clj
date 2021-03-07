(ns sk.handlers.administrar.aventuras.handler
  (:require [sk.models.crud :refer [build-form-row
                                    build-form-save
                                    build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util :refer [get-session-id]]
            [sk.layout :refer [application]]
            [sk.handlers.administrar.aventuras.view :refer [aventuras-view aventuras-scripts]]))

(defn aventuras
  [_]
  (try
    (let [title "Aventuras de rodadas"
          ok (get-session-id)
          js (aventuras-scripts)
          content (aventuras-view title)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn aventuras-grid
  [{params :params}]
  (try
    (let [table "aventuras"
          args {:sort-extra "nombre"}]
      (build-grid params table args))
    (catch Exception e (.getMessage e))))

(defn aventuras-form
  [id]
  (try
    (let [table "aventuras"]
      (build-form-row table id))
    (catch Exception e (.getMessage e))))

(defn aventuras-save
  [{params :params}]
  (try
    (let [table "aventuras"]
      (build-form-save params table))
    (catch Exception e (.getMessage e))))

(defn aventuras-delete
  [{params :params}]
  (try
    (let [table "aventuras"]
      (build-form-delete params table))
    (catch Exception e (.getMessage e))))
