(ns sk.handlers.aventuras.handler
  (:require [sk.handlers.aventuras.view :refer [aventuras-scripts aventuras-view]]
            [sk.models.crud :refer [build-form-save]]
            [sk.layout :refer [application]]
            [sk.handlers.aventuras.model :refer [get-cmt-rows get-rows]]
            [sk.models.util :refer [get-session-id parse-int]]))

(defn aventuras [id]
  (let [title "Cicloturismo"
        ok (get-session-id)
        js (aventuras-scripts)
        rows (get-rows (parse-int id))
        crow (first (get-cmt-rows (parse-int id)))
        content (aventuras-view rows crow)]
    (application title ok js content)))

(defn comentarios [{params :params}]
  (let [table "aventuras_link"]
    (build-form-save params table)))

(comment
  (get-rows "1"))
