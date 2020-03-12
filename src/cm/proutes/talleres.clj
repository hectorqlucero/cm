(ns cm.proutes.talleres
  (:require [cheshire.core :refer [generate-string]]
            [cm.models.crud :refer [build-postvars db Delete Query Save]]
            [cm.models.grid :refer [convert-search-columns
                                    grid-search
                                    grid-sort
                                    grid-sort-extra
                                    grid-offset
                                    grid-rows]]
            [cm.models.util :refer [fix-id get-session-id parse-int]]
            [cm.views.layout :refer [application]]
            [cm.views.proutes.talleres :refer [talleres-scripts talleres-view]]))

(defn talleres [_]
  (let [title   "Talleres"
        ok      (get-session-id)
        js      (talleres-scripts)
        content (talleres-view title)]
    (application title ok js content)))

;; Start talleres grid
(def search-columns
  ["id"
   "nombre"])

(def aliases-columns
  ["id"
   "nombre"
   "direccion"
   "telefono"
   "horarios"
   "sitio"
   "direcciones"
   "historia"])

(defn talleres-grid [{params :params}]
  (try
    (let [table    "talleres"
          scolumns (convert-search-columns search-columns)
          aliases  aliases-columns
          join     ""
          search   (grid-search (:search params nil) scolumns)
          order    (grid-sort (:sort params nil) (:order params nil))
          order    (grid-sort-extra order "nombre")
          offset   (grid-offset (parse-int (:rows params)) (parse-int (:page params)))
          rows     (grid-rows table aliases join search order offset)]
      (generate-string rows))
    (catch Exception e (.getMessage e))))
;; End talleres grid

;; Start talleres form
(def talleres-form-sql
  "SELECT * FROM talleres WHERE id = ?")

(defn talleres-form [id]
  (try
    (let [row (Query db [talleres-form-sql id])]
      (generate-string (first row)))
    (catch Exception e (.getMessage e))))
;; End talleres form

(defn talleres-save [{params :params}]
  (try
    (let [id       (fix-id (:id params))
          postvars (build-postvars "talleres" params)
          result   (Save db :talleres postvars ["id = ?" id])]
      (if (seq result)
        (generate-string {:success "Correctamente Processado!"})
        (generate-string {:error "No se pudo processar!"})))))

(defn talleres-delete [{params :params}]
  (try
    (let [id     (:id params nil)
          result (if-not (nil? id)
                   (Delete db :talleres ["id = ?" id])
                   nil)]
      (if (seq result)
        (generate-string {:success "Removido appropiadamente!"})
        (generate-string {:error "No se pudo remover!"})))))
