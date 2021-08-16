(ns sk.handlers.administrar.videos.view
  (:require [hiccup.page :refer [include-js]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [sk.models.util :refer [build-table
                                    build-dialog
                                    build-dialog-buttons
                                    build-toolbar
                                    build-image-field
                                    build-text-editor
                                    build-image-field-script
                                    build-field]]))

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
    {:id "titulo"
     :name "titulo"
     :class "easyui-textbox"
     :prompt "Descripción corta aqui..."
     :data-options "label:'Titulo:',
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

(defn videos-view
  "Esto crea el grid y la forma en una ventana - Cambiar videos por el nombre correcto"
  [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/administrar/videos"
    (list ;; Aqui los campos del grid
     [:th {:data-options "field:'fecha_formatted',sortable:true"} "Fecha"]
     [:th {:data-options "field:'titulo',sortable:true"} "Titulo"]
     [:th {:data-options "field:'enlace',sortable:true"} "Enlace"]))
   (build-toolbar)
   (build-dialog title dialog-fields)
   (build-dialog-buttons)))

(defn videos-scripts
  "Esto crea el javascript necesario - Cambiar videos por el nombre correcto"
  []
  (include-js "/js/grid.js"))
