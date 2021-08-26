(ns sk.handlers.administrar.fotos.view
  (:require [hiccup.page :refer [include-js]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [sk.models.util :refer [build-dialog build-dialog-buttons build-field build-table build-toolbar]]))

(def dialog-fields
  (list
   [:input {:type "hidden" :id "id" :name "id"}]
   (build-field
    {:id "fecha"
     :name "fecha"
     :class "easyui-datebox"
     :prompt "mm/dd/aaaa ex. 02/07/1957 es: Febrero 2 de 1957"
     :data-options "label:'Fecha:',
                     labelPosition:'top',
                     required:true,
                     width:'100%'"})
   (build-field
    {:id "enlace"
     :name "enlace"
     :class "easyui-textbox"
     :prompt "enlace"
     :data-options "label:'Enlace:',
                     labelPosition:'top',
                     required:true,
                     width:'100%'"})))

(defn fotos-view
  "Esto crea el grid y la forma en una ventana - Cambiar fotos por el nombre correcto"
  [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/administrar/fotos"
    (list ;; Aqui los campos del grid
     [:th {:data-options "field:'fecha_formatted',sortable:true"} "Fecha"]
     [:th {:data-options "field:'enlace',sortable:true"} "Enlace"]))
   (build-toolbar)
   (build-dialog title dialog-fields)
   (build-dialog-buttons)))

(defn fotos-scripts
  "Esto crea el javascript necesario - Cambiar fotos por el nombre correcto"
  []
  (include-js "/js/grid.js"))
