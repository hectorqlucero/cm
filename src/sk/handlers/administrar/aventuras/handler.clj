(ns sk.handlers.administrar.aventuras.handler
  (:require [sk.handlers.administrar.aventuras.view :refer [aventuras-scripts aventuras-view]]
            [sk.layout :refer [application]]
            [sk.models.crud :refer [build-form-delete build-form-row build-form-save]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util :refer [get-session-id user-email user-level]]))

(defn aventuras
  [_]
  (let [title "Aventuras de rodadas"
        ok (get-session-id)
        js (aventuras-scripts (user-email))
        content (aventuras-view title (user-email))]
    (application title ok js content)))

(defn aventuras-filter []
  (if (= (user-level) "U")
    {:sort-extra "fecha desc"
     :search-extra (str "leader_email = '" (user-email) "'")}
    {:sort-extra "fecha desc"}))

(defn aventuras-grid
  [{params :params}]
  (let [table "aventuras"
        args (aventuras-filter)]
    (build-grid params table args)))

(defn aventuras-form
  [id]
  (let [table "aventuras"]
    (build-form-row table id)))

(defn aventuras-save
  [{params :params}]
  (let [table "aventuras"]
    (build-form-save params table)))

(defn aventuras-delete
  [{params :params}]
  (let [table "aventuras"]
    (build-form-delete params table)))
