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
  (let [title   "Grupos de Ciclistas"
        ok      (get-session-id)
        js      (cuadrantes-scripts)
        content (cuadrantes-view title)]
    (application title ok js content)))

(defn cuadrantes-grid
  [{params :params}]
  (let [table "cuadrantes"
        args  {:sort-extra "name"}]
    (build-grid params table args)))

(defn cuadrantes-form
  [id]
  (let [table "cuadrantes"]
    (build-form-row table id)))

(defn cuadrantes-save
  [{params :params}]
  (let [table "cuadrantes"]
    (build-form-save params table)))

(defn cuadrantes-delete
  [{params :params}]
  (let [table "cuadrantes"]
    (build-form-delete params table)))
