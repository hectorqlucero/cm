(ns sk.handlers.administrar.eventos.handler
  (:require [sk.models.crud :refer [build-form-row
                                    build-form-save
                                    build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util :refer [get-session-id]]
            [sk.layout :refer [application]]
            [sk.handlers.administrar.eventos.view :refer [eventos-view eventos-scripts]]))

(defn eventos
  [_]
  (try
    (let [title "Calendario - Eventos"
          ok (get-session-id)
          js (eventos-scripts)
          content (eventos-view title)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn eventos-grid
  [{params :params}]
  (try
    (let [table "eventos"
          args {:sort-extra "fecha,hora"}]
      (build-grid params table args))
    (catch Exception e (.getMessage e))))

(defn eventos-form
  [id]
  (try
    (let [table "eventos"]
      (build-form-row table id))
    (catch Exception e (.getMessage e))))

(defn eventos-save
  [{params :params}]
  (try
    (let [table "eventos"
          upload-folder "/eventos"]
      (build-form-save params table upload-folder))
    (catch Exception e (.getMessage e))))

(defn eventos-delete
  [{params :params}]
  (try
    (let [table "eventos"]
      (build-form-delete params table))
    (catch Exception e (.getMessage e))))
