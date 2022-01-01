(ns sk.handlers.admin.frases.view
  (:require
   [hiccup.page :refer [include-js]]
   [ring.util.anti-forgery :refer [anti-forgery-field]]
   [sk.models.util :refer
    [build-dialog build-dialog-buttons build-field build-table build-toolbar]]))

(defn dialog-fields []
  (list
   (build-field
    {:id "id"
     :name "id"
     :type "hidden"})
   (build-field
    {:id "frase"
     :name "frase"
     :class "easyui-textbox"
     :prompt "Aqui la frase del ciclista"
     :data-options "label:'Frase:',
        labelPosition:'top',
        required:true,
        multiline:true,
        height:120,
        width:'100%'"})
   (build-field
    {:id "autor"
     :name "autor"
     :class "easyui-textbox"
     :prompt "Aqui el autor"
     :data-options "label:'Autor:',
        labelPosition:'top',
        required:true,
        width:'100%'"})))

(defn frases-view [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/admin/frases"
    (list
     [:th {:data-options "field:'frase',sortable:true,width:100"} "Frase"]
     [:th {:data-options "field:'autor',sortable:true,width:100"} "Autor"]))
   (build-toolbar)
   (build-dialog title (dialog-fields))
   (build-dialog-buttons)))

(defn frases-scripts []
  (include-js "/js/grid.js"))
