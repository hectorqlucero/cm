(ns sk.handlers.administrar.fotos.handler
  (:require [sk.handlers.administrar.fotos.view :refer [fotos-scripts fotos-view]]
            [sk.layout :refer [application]]
            [sk.models.crud :refer [build-form-delete build-form-row build-form-save]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util :refer [get-session-id]]))
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
    (let [table "fotos"
          args {:sort-extra "fecha desc"}]
      (build-grid params table args))
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
