(ns sk.handlers.administrar.talleres.view
  (:require [ring.util.anti-forgery :refer [anti-forgery-field]]
            [hiccup.page :refer [include-js]]
            [sk.models.util :refer [build-table
                                    build-dialog
                                    build-dialog-buttons
                                    build-toolbar
                                    build-field]]))

(def dialog-fields
  (list
   [:input {:type "hidden" :id "id" :name "id"}]
   (build-field
    {:id           "nombre"
     :name         "nombre"
     :class        "easyui-textbox"
     :data-options "required: true,
                    width:'100%',
                    label:'Nombre del Taller:',
                    labelPosition:'top'"})
   (build-field
    {:id           "direccion"
     :name         "direccion"
     :class        "easyui-textbox"
     :data-options "label:'Direccion:',
                    labelPosition:'top',
                    width:'100%',
                    required: false"})
   (build-field
    {:id           "telefono"
     :name         "telefono"
     :class        "easyui-maskedbox"
     :mask         "(999) 999-9999"
     :data-options "label:'Telefono:',
                    labelPosition:'top',
                    width:'100%',
                    required: false"})
   (build-field
    {:id           "horarios"
     :name         "horarios"
     :class        "easyui-textbox"
     :data-options "label:'Horarios:',
                    labelPosition:'top',
                    width:'100%',
                    required: false"})
   (build-field
    {:id           "sitio"
     :name         "sitio"
     :class        "easyui-textbox"
     :data-options "label:'Sitio:',
                    labelPosition:'top',
                    width:'100%',
                    required: false"})
   (build-field
    {:id           "direcciones"
     :name         "direcciones"
     :class        "easyui-textbox"
     :data-options "label:'Direcciones:',
                    labelPosition:'top',
                    width:'100%',
                    required: false,multiline:true,height:80"})
   (build-field
    {:id           "historia"
     :name         "historia"
     :class        "easyui-textbox"
     :data-options "label:'Historia:',
                    labelPosition:'top',
                    width:'100%',
                    required: false,multiline:true,height:80"})))

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
      "/administrar/talleres"
      (list
        [:th {:data-options "field:'nombre',width:23,sortable:true"} "Nombre"]
        [:th {:data-options "field: 'direccion',width:58,sortable:false"} "Direccion"]
        [:th {:data-options "field: 'telefono',width:14,sortable:false,width:5"} "Telefono"]))
    (build-toolbar (toolbar-extra))
    (build-dialog title dialog-fields)
    (build-dialog-buttons)))

(defn talleres-scripts []
  (include-js "/js/grid.js"))
