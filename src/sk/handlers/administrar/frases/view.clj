(ns sk.handlers.administrar.frases.view
  (:require [hiccup.page :refer [include-js]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [sk.models.util :refer [build-dialog build-dialog-buttons build-field build-table build-toolbar]]))

(def dialog-fields
  (list
   [:input {:type "hidden" :id "id" :name "id"}]
   (build-field
    {:id "frase"
     :name "frase"
     :class "easyui-textbox"
     :prompt "Aqui la frase del ciclista"
     :data-options "label:'Frase:',
                    labelPosition:'top',
                    required:true,
                    multiline:true,
                    width:'100%',
                    height:120"})
   (build-field
    {:id "autor"
     :name "autor"
     :class "easyui-textbox"
     :prompt "Autor aqui"
     :data-options "label:'Autor:',
                     labelPosition:'top',
                     required:true,
                     width:'100%'"})))

(defn frases-view
  "Esto crea el grid y la forma en una ventana - Cambiar frases por el nombre correcto"
  [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/administrar/frases"
    (list ;; Aqui los campos del grid
     [:th {:data-options "field:'frase',sortable:true"} "Frase"]
     [:th {:data-options "field:'autor',sortable:true"} "Autor"]))
   (build-toolbar)
   (build-dialog title dialog-fields)
   (build-dialog-buttons)))

(defn frases-scripts
  "Esto crea el javascript necesario - Cambiar frases por el nombre correcto"
  []
  (include-js "/js/grid.js"))
