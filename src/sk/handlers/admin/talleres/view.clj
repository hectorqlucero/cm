(ns sk.handlers.admin.talleres.view
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
     :prompt "Nombre del taller"
     :data-options "label:'Nombre:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "direccion"
     :name "direccion"
     :class "easyui-textbox"
     :prompt "Domicilio del taller"
     :data-options "label:'Domicilio:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "telefono"
     :name "telefono"
     :class "easyui-maskedbox"
     :mask "(999) 999-9999"
     :prompt "Telefono aqui"
     :data-options "label:'Telefono:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "horarios"
     :name "horarios"
     :class "easyui-textbox"
     :prompt "Horarios del taller"
     :data-options "label:'Horarios:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "sitio"
     :name "sitio"
     :class "easyui-textbox"
     :prompt "Sitio ej. enlace a facebook o pagina web"
     :data-options "label:'Enlace Sitio:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "direcciones"
     :name "direcciones"
     :class "easyui-textbox"
     :prompt "Enlace a google maps"
     :data-options "label:'Direcciones:',
        labelPosition:'top',
        required:false,
        multiline:true,
        height:120,
        width:'100%'"})
   (build-field
    {:id "historia"
     :name "historia"
     :class "easyui-textbox"
     :prompt "Historia o detalles del taller"
     :data-options "label:'Historia/Detalles:',
        labelPosition:'top',
        required:false,
        multiline:true,
        height:120,
        width:'100%'"})))

(defn toolbar-extra []
  (list
   [:a {:href         "javascript:void(0)"
        :class        "easyui-linkbutton"
        :data-options "iconCls:'icon-back',plain:true"
        :onclick      "returnItem('/talleres/list')"} "Regresar a Talleres"]))

(defn talleres-view [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/admin/talleres"
    (list
     [:th {:data-options "field:'nombre',sortable:true,width:100"} "Nombre"]
     [:th {:data-options "field:'direccion',sortable:true,width:100"} "Direccion"]
     [:th {:data-options "field:'telefono',sortable:true,width:100"} "Telefono"]))
   (build-toolbar (toolbar-extra))
   (build-dialog title (dialog-fields))
   (build-dialog-buttons)))

(defn talleres-scripts []
  (include-js "/js/grid.js"))
