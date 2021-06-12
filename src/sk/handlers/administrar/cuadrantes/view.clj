(ns sk.handlers.administrar.cuadrantes.view
  (:require [ring.util.anti-forgery :refer [anti-forgery-field]]
            [hiccup.page :refer [include-js]]
            [sk.models.util :refer [build-table
                                    build-dialog
                                    build-dialog-buttons
                                    build-toolbar
                                    build-field
                                    build-radio-buttons]]))

(def dialog-fields
  (list
   [:input {:type "hidden" :id "id" :name "id"}]
   (build-field
    {:id           "name"
     :name         "name"
     :class        "easyui-textbox"
     :data-options "label:'Nombre ex: Cuandrante Rosita',
                    labelPosition:'top',
                    width:'100%',required: true"})
   (build-field
    {:id           "leader"
     :name         "leader"
     :class        "easyui-textbox"
     :data-options "label:'Lider:',
                    labelPosition:'top',
                    width:'100%',required: true"})
   (build-field
    {:id           "leader_phone"
     :name         "leader_phone"
     :class        "easyui-maskedbox"
     :mask         "(999) 999-9999"
     :data-options "label:'Telefono:',
                    labelPosition:'top',
                    width:'100%',required: false"})
   (build-field
    {:id           "leader_cell"
     :name         "leader_cell"
     :class        "easyui-maskedbox"
     :mask         "(999) 999-9999"
     :data-options "label:'Cell:',
                    labelPosition:'top',
                    width:'100%',required: false"})
   (build-field
    {:id           "leader_email"
     :name         "leader_email"
     :class        "easyui-textbox easyui-validatebox"
     :validType    "email"
     :data-options "label:'Email:',
                    labelPosition:'top',
                    width:'100%',required: false"})
   (build-field
    {:id           "notes"
     :name         "notes"
     :class        "easyui-textbox"
     :data-options "label:'Observaciones:',
                    labelPosition:'top',
                    width:'100%',required: false,multiline:true,height:80"})
   (build-radio-buttons
    "Activo?"
    (list
     {:id           "status"
      :name         "status"
      :class        "form-control easyui-radiobutton"
      :value        "T"
      :label        "Si"
      :data-options "checked:true"}
     {:id           "status"
      :name         "status"
      :class        "form-control easyui-radiobutton"
      :value        "F"
      :label        "No"
      :data-options "checked:false"}))))

(defn toolbar-extra []
  (list
   [:a {:href         "javascript:void(0)"
        :class        "easyui-linkbutton"
        :data-options "iconCls:'icon-back',plain:true"
        :onclick      "returnItem('/grupos/list')"} "Regresar a Grupos"]))

(defn cuadrantes-view [title]
  (list
   (anti-forgery-field)
   (build-table
     title 
     "/administrar/cuadrantes" 
     (list
       [:th {:data-options "field:'name',sortable:true,fixed:false"} "Nombre del Cuadrante"]
       [:th {:data-options "field:'leader',sortable:true,fixed:false"} "Lider"]
       [:th {:data-options "field:'leader_phone',sortable:true,fixed:true"} "Telefono"]
       [:th {:data-options "field:'leader_cell',sortable:true,fixed:true"} "Cel"]
       [:th {:data-options "field:'leader_email',sortable:true,fixed:false"} "Email"]))
   (build-toolbar (toolbar-extra))
   (build-dialog title dialog-fields)
   (build-dialog-buttons)))

(defn cuadrantes-scripts []
  (include-js "/js/grid.js"))
