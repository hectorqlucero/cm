(ns sk.handlers.administrar.talleres.handler
  (:require [sk.handlers.administrar.talleres.view
             :refer
             [talleres-scripts talleres-view]]
            [sk.layout :refer [application]]
            [sk.models.crud
             :refer
             [build-form-delete build-form-row build-form-save]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util :refer [get-session-id]]))

(defn talleres
  [_]
  (let [title   "Talleres de bicicleta"
        ok      (get-session-id)
        js      (talleres-scripts)
        content (talleres-view title)]
    (application title ok js content)))

(defn talleres-grid
  [{params :params}]
  (let [table "talleres"
        args  {:sort-extra "nombre"}]
    (build-grid params table args)))

(defn talleres-form
  [id]
  (let [table "talleres"]
    (build-form-row table id)))

(defn talleres-save
  [{params :params}]
  (let [table         "talleres"
        upload-folder "talleres"]
    (build-form-save params table upload-folder)))

(defn talleres-delete
  [{params :params}]
  (let [table "talleres"]
    (build-form-delete params table)))
