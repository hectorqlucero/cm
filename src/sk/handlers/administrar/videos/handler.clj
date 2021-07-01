(ns sk.handlers.administrar.videos.handler
  (:require [sk.models.crud :refer [build-form-row
                                    build-form-save
                                    build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.layout :refer [application]]
            [sk.models.util :refer [get-session-id]]
            [sk.handlers.administrar.videos.view :refer [videos-view
                                                         videos-scripts]]))
(defn videos
  [_]
  (try
    (let [title "Videos"
          ok (get-session-id)
          js (videos-scripts)
          content (videos-view title)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn videos-grid
  [{params :params}]
  (try
    (let [table "videos"]
      (build-grid params table))
    (catch Exception e (.getMessage e))))

(defn videos-form
  [id]
  (try
    (let [table "videos"]
      (build-form-row table id))
    (catch Exception e (.getMessage e))))

(defn videos-save
  [{params :params}]
  (try
    (let [table "videos"]
      (build-form-save params table))
    (catch Exception e (.getMessage e))))

(defn videos-delete
  [{params :params}]
  (try
    (let [table "videos"]
      (build-form-delete params table))
    (catch Exception e (.getMessage e))))
