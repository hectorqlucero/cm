(ns sk.handlers.administrar.users.handler
  (:require [sk.handlers.administrar.users.view :refer [users-scripts users-view]]
            [sk.layout :refer [application]]
            [sk.models.crud
             :refer
             [build-form-delete build-form-row build-form-save]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util :refer [get-session-id user-level]]))

(defn users
  [_]
  (let [title   "Usuarios"
        ok      (get-session-id)
        js      (users-scripts)
        content (users-view title)
        level   (user-level)]
    (if
     (or
      (= (user-level) "A")
      (= (user-level) "S"))
      (application title ok js content)
      (application title ok nil "Solo <strong>administradores</strong> pueden accesar esta opción!!!"))))

(defn users-grid
  [{params :params}]
  (let [table "users"
        args  {:sort-extra "lastname,firstname"}]
    (build-grid params table args)))

(defn users-form
  [id]
  (let [table "users"]
    (build-form-row table id)))

(defn users-save
  [{params :params}]
  (let [table "users"]
    (build-form-save params table)))

(defn users-delete
  [{params :params}]
  (let [table "users"]
    (build-form-delete params table)))
