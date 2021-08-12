(ns sk.handlers.administrar.frases.handler
  (:require [sk.models.crud :refer [build-form-row
                                    build-form-save
                                    build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.layout :refer [application]]
            [sk.models.util :refer [get-session-id]]
            [sk.handlers.administrar.frases.view :refer [frases-view
                                                         frases-scripts]]))
(defn frases
  [_]
  (try
    (let [title "Frases de Ciclistas"
          ok (get-session-id)
          js (frases-scripts)
          content (frases-view title)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn frases-grid
  [{params :params}]
  (try
    (let [table "frases"]
      (build-grid params table))
    (catch Exception e (.getMessage e))))

(defn frases-form
  [id]
  (try
    (let [table "frases"]
      (build-form-row table id))
    (catch Exception e (.getMessage e))))

(defn frases-save
  [{params :params}]
  (try
    (let [table "frases"]
      (build-form-save params table))
    (catch Exception e (.getMessage e))))

(defn frases-delete
  [{params :params}]
  (try
    (let [table "frases"]
      (build-form-delete params table))
    (catch Exception e (.getMessage e))))
