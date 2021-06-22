(ns sk.handlers.administrar.fotos.handler
  (:require [sk.models.crud :refer [build-form-row
                                    build-form-save
                                    build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.layout :refer [application]]
            [sk.models.util :refer [get-session-id]]
            [sk.handlers.administrar.fotos.view :refer [fotos-view
                                                        fotos-scripts]]))
(defn fotos
  [_]
  (try
    (let [title "Fotos"
          ok (get-session-id)
          js (fotos-scripts)
          content (fotos-view title)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn fotos-grid
  [{params :params}]
  (try
    (let [table "fotos"]
      (build-grid params table))
    (catch Exception e (.getMessage e))))

(defn fotos-form
  [id]
  (try
    (let [table "fotos"]
      (build-form-row table id))
    (catch Exception e (.getMessage e))))

(defn fotos-save
  [{params :params}]
  (try
    (let [table "fotos"]
      (build-form-save params table))
    (catch Exception e (.getMessage e))))

(defn fotos-delete
  [{params :params}]
  (try
    (let [table "fotos"]
      (build-form-delete params table))
    (catch Exception e (.getMessage e))))
