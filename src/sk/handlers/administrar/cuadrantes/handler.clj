(ns sk.handlers.administrar.cuadrantes.handler
  (:require [sk.handlers.administrar.cuadrantes.view
             :refer
             [cuadrantes-scripts cuadrantes-view]]
            [sk.layout :refer [application]]
            [sk.models.crud
             :refer
             [build-form-delete build-form-row build-form-save]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util :refer [get-session-id]]))

(defn cuadrantes
  [_]
  (try
    (let [title   "Grupos de Ciclistas"
          ok      (get-session-id)
          js      (cuadrantes-scripts)
          content (cuadrantes-view title)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn cuadrantes-grid
  [{params :params}]
  (try
    (let [table "cuadrantes"
          args  {:sort-extra "name"}]
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
    (let [table "cuadrantes"]
      (build-form-save params table))
    (catch Exception e (.getMessage e))))

(defn cuadrantes-delete
  [{params :params}]
  (try
    (let [table "cuadrantes"]
      (build-form-delete params table))
    (catch Exception e (.getMessage e))))
