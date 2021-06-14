(ns sk.handlers.administrar.aventuras.handler
  (:require [sk.models.crud :refer [build-form-row
                                    build-form-save
                                    build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util :refer [get-session-id
                                    current_year
                                    user-email
                                    user-level]]
            [sk.layout :refer [application]]
            [sk.handlers.administrar.aventuras.view :refer [aventuras-view aventuras-scripts]]))

(defn aventuras
  [_]
  (let [title "Aventuras de rodadas"
        ok (get-session-id)
        js (aventuras-scripts (user-email))
        content (aventuras-view title (user-email))]
    (application title ok js content)))

(defn aventuras-filter []
  (if (= (user-level) "U")
    {:sort-extra "fecha"
     :search-extra (str "leader_email = '" (user-email) "'")}
    {:sort-extra "fecha"}))

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
