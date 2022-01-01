(ns sk.handlers.admin.frases.handler
  (:require [sk.models.crud :refer [build-form-row build-form-save build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.layout :refer [application]]
            [sk.models.util :refer [get-session-id user-level]]
            [sk.handlers.admin.frases.view :refer [frases-view frases-scripts]]))

(defn frases [_]
  (let [title "Frases"
        ok (get-session-id)
        js (frases-scripts)
        content (frases-view title)]
    (if
     (or
      (= (user-level) "A")
      (= (user-level) "S"))
      (application title ok js content)
      (application title ok nil "solo <strong>los administradores </strong> pueden accessar esta opción!!!"))))

(defn frases-grid
  "builds grid. parameters: params table & args args: {:join 'other-table' :search-extra name='pedro' :sort-extra 'name,lastname'}"
  [{params :params}]
  (let [table "frases"
        args {:sort-extra "autor"}]
    (build-grid params table args)))

(defn frases-form [id]
  (let [table "frases"]
    (build-form-row table id)))

(defn frases-save [{params :params}]
  (let [table "frases"]
    (build-form-save params table)))

(defn frases-delete [{params :params}]
  (let [table "frases"]
    (build-form-delete params table)))
