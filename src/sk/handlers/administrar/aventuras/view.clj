(ns sk.handlers.administrar.aventuras.view
  (:require [ring.util.anti-forgery :refer [anti-forgery-field]]
            [hiccup.page :refer [include-js]]
            [sk.models.util :refer [build-table
                                    build-dialog
                                    build-dialog-buttons
                                    build-toolbar
                                    build-field
                                    build-radio-buttons]]))

(defn build-email-options [email]
  (str
    "
    label:'Email:',
    labelPosition:'top',
    width:'100%',
    required:true,
    valueField:'label',
    textField:'value',
    data:[{label:'leader_email',value:'" email "'}]"))

(defn dialog-fields [email]
  (list
    [:input {:type "hidden" :id "id" :name "id"}]
    (build-field
      {:id "leader_email"
       :name "leader_email"
       :class "easyui-combobox"
       :data-options (build-email-options email)})
    
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

(defn aventuras-view [title email]
  (list
   (anti-forgery-field)
   (build-table
     title 
     "/administrar/aventuras" 
     (list
       [:th {:data-options "field:'leader_email',sortable:true,fixed:false"} "Email"]
       [:th {:data-options "field:'fecha',sortable:true,fixed:false"} "Fecha"]
       [:th {:data-options "field:'aventura',sortable:true,fixed:true"} "Aventura"]))
   (build-toolbar (toolbar-extra))
   (build-dialog title (dialog-fields email))
   (build-dialog-buttons)))

(defn aventuras-scripts [email]
  (include-js "/js/grid.js"))
