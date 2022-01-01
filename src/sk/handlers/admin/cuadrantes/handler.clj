(ns sk.handlers.admin.cuadrantes.handler
  (:require [sk.models.crud :refer [build-form-row build-form-save build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.layout :refer [application]]
            [sk.models.util :refer [get-session-id user-level]]
            [sk.handlers.admin.cuadrantes.view :refer [cuadrantes-view cuadrantes-scripts]]))

(defn cuadrantes [_]
  (let [title "Grupos de Ciclistas"
        ok (get-session-id)
        js (cuadrantes-scripts)
        content (cuadrantes-view title)]
    (if
     (or
      (= (user-level) "A")
      (= (user-level) "S"))
      (application title ok js content)
      (application title ok nil "solo <strong>los administradores </strong> pueden accessar esta opción!!!"))))

(defn cuadrantes-grid
  "builds grid. parameters: params table & args args: {:join 'other-table' :search-extra name='pedro' :sort-extra 'name,lastname'}"
  [{params :params}]
  (let [table "cuadrantes"
        args {:sort-extra "name"}]
    (build-grid params table args)))

(defn cuadrantes-form [id]
  (let [table "cuadrantes"]
    (build-form-row table id)))

(defn cuadrantes-save [{params :params}]
  (let [table "cuadrantes"]
    (build-form-save params table)))

(defn cuadrantes-delete [{params :params}]
  (let [table "cuadrantes"]
    (build-form-delete params table)))
