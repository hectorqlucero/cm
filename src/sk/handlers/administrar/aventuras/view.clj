(ns sk.handlers.administrar.aventuras.view
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
    {:id           "nombre"
     :name         "nombre"
     :class        "easyui-textbox"
     :data-options "label:'Nombre:',
                    labelPosition:'top',
                    width:'100%',required: true"})
   (build-field
     {:id           "fecha"
      :name         "fecha"
      :class        "easyui-datebox"
      :data-options "label:'Fecha/Rodada:',
                    labelPosition:'top',
                    required: true,
                    width:'100%'"})
   (build-field
    {:id           "aventura"
     :name         "aventura"
     :class        "easyui-textbox"
     :data-options "label:'Aventura:',
                    labelPosition:'top',
                    width:'100%',required: false,multiline:true,height:100"})))

(defn toolbar-extra []
  (list
   [:a {:href         "javascript:void(0)"
        :class        "easyui-linkbutton"
        :data-options "iconCls:'icon-back',plain:true"
        :onclick      "returnItem('/aventuras/list')"} "Regresar a Aventuras"]))

(defn aventuras-view [title]
  (list
   (anti-forgery-field)
   (build-table
     title 
     "/administrar/aventuras" 
     (list
       [:th {:data-options "field:'nombre',sortable:true,fixed:false,width:100"} "Nombre"]
       [:th {:data-options "field:'fecha',sortable:true,fixed:false,width:100"} "Fecha"]
       [:th {:data-options "field:'aventura',sortable:true,fixed:true,width:100"} "Aventura"]))
   (build-toolbar (toolbar-extra))
   (build-dialog title dialog-fields)
   (build-dialog-buttons)))

(defn aventuras-scripts []
  (include-js "/js/grid.js"))
