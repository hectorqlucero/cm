(ns sk.handlers.admin.rodadas.handler
  (:require [cheshire.core :refer [generate-string]]
            [sk.handlers.admin.rodadas.view :refer [rodadas-scripts
                                                    rodadas-view]]
            [sk.handlers.admin.rodadas.model :refer [process-email]]
            [sk.layout :refer [application]]
            [sk.models.crud :refer [Delete db build-form-delete build-form-row build-form-save]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util :refer [get-session-id user-level user-email current_year]]))

(defn rodadas [_]
  (let [title "Rodadas"
        ok (get-session-id)
        js (rodadas-scripts)
        content (rodadas-view title)]
    (if
     (or
      (= (user-level) "U")
      (= (user-level) "A")
      (= (user-level) "S"))
      (application title ok js content)
      (application title ok nil "solo <strong>los usuarios </strong> pueden accessar esta opción!!!"))))

(defn rodadas-grid
  "builds grid. parameters: params table & args args: {:join 'other-table' :search-extra name='pedro' :sort-extra 'name,lastname'}"
  [{params :params}]
  (let [table "rodadas"
        args (if (= (user-level) "U")
               {:sort-extra   "fecha desc,salida"
                :search-extra (str "leader_email = '" (user-email) "' AND YEAR(fecha) = '" (current_year) "'")}
               {:sort-extra   "fecha desc,salida"
                :search-extra (str "YEAR(fecha) = '" (current_year) "'")})]
    (build-grid params table args)))

(defn rodadas-form [id]
  (let [table "rodadas"]
    (build-form-row table id)))

(defn rodadas-save [{params :params}]
  (let [table "rodadas"]
    (build-form-save params table)))

(defn rodadas-delete [{params :params}]
  (try
    (let [id (:id params nil)
          result (if-not (nil? id)
                   (Delete db :rodadas ["id = ??" id])
                   nil)]
      (if (seq result)
        (do
          (process-email id)
          (generate-string {:success "Eliminado con éxito!"}))
        (generate-string {:error "Incapaz de eliminar!"})))
    (catch Exception e (.getMessage e))))
