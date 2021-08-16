(ns sk.handlers.administrar.rodadas.handler
  (:require [cheshire.core :refer [generate-string]]
            [sk.handlers.administrar.rodadas.model :refer [process-email]]
            [sk.handlers.administrar.rodadas.view
             :refer
             [rodadas-scripts rodadas-view]]
            [sk.layout :refer [application]]
            [sk.models.crud
             :refer
             [db Delete build-form-delete build-form-row build-form-save]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util
             :refer
             [current_year get-session-id user-email user-level]]))

(defn rodadas
  [_]
  (let [title   "Entrenamiento - Rodadas"
        ok      (get-session-id)
        js      (rodadas-scripts)
        content (rodadas-view title)]
    (application title ok js content)))

(defn rodadas-grid
  [{params :params}]
  (let [table "rodadas"
        args  (if (= (user-level) "U")
                {:sort-extra   "fecha desc,salida"
                 :search-extra (str "leader_email = '" (user-email) "' AND YEAR(fecha) = '" (current_year) "'")}
                {:sort-extra   "fecha desc,salida"
                 :search-extra (str "YEAR(fecha) = '" (current_year) "'")})]
    (build-grid params table args)))

(defn rodadas-form
  [id]
  (let [table "rodadas"]
    (build-form-row table id)))

(defn rodadas-save
  [{params :params}]
  (let [table         "rodadas"
        upload-folder "rodadas"]
    (build-form-save params table upload-folder)))

(defn rodadas-delete
  [{params :params}]
  (let [table   "rodadas"
        id      (:id params)
        eresult (if-not (nil? id) (doall (process-email id)) nil)
        result  (if-not (nil? id) (Delete db (keyword table) ["id = ?" id]) nil)]
    (if (seq result)
      (generate-string {:success "Removido appropiadamente!"})
      (generate-string {:error "No se pudo remover!"}))))
