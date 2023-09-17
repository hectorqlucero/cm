(ns sk.handlers.admin.cmt.view
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
    {:id "nombre"
     :name "nombre"
     :class "easyui-textbox"
     :prompt "Nombre de la aventura ej. Cicloturismo por la BCS..."
     :data-options "label:'Nombre:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "comments"
     :name "comments"
     :class "easyui-textbox"
     :prompt "Detalles de esta aventura..."
     :data-options "label:'Detalles:',
        labelPosition:'top',
        required:true,
        multiline:true,
        height:120,
        width:'100%'"})
   (build-field
    {:id "maximo"
     :name "maximo"
     :prompt "Maximo limite de records o 0 para cicloturismo"
     :class "easyui-numberbox"
     :data-option "label:'Maximo:',
                   labelPosition:'top',
                   required:true,
                   min:0,
                   precision:0,
                   width:'100%'"})))

(defn cmt-view [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/admin/cmt"
    (list
     [:th {:data-options "field:'nombre',sortable:true,width:100"} "NOMBRE"]
     [:th {:data-options "field:'comments',sortable:true,width:100"} "COMMENTS"]
     [:th {:data-options "field:'maximo',sortable:true,width:100"} "MAXIMO"]))
   (build-toolbar)
   (build-dialog title (dialog-fields))
   (build-dialog-buttons)))

(defn cmt-scripts []
  (include-js "/js/grid.js"))
