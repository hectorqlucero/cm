(ns sk.handlers.admin.aventuras.view
  (:require
   [hiccup.page :refer [include-js]]
   [ring.util.anti-forgery :refer [anti-forgery-field]]
   [sk.models.util :refer
    [build-dialog build-dialog-buttons build-field build-table build-toolbar]]))

(defn dialog-fields [email]
  (list
   (build-field
    {:id "id"
     :name "id"
     :type "hidden"})
   [:select {:id "leader_email"
             :name "leader_email"
             :class "easyui-combobox"
             :data-options "label:'Email',
                            labelPosition:'top',
                            required:true,
                            width:'100%'"}
    [:option {:value email} email]]
   (build-field
    {:id "enlace"
     :name "enlace"
     :class "easyui-textbox"
     :prompt "Enlace aqui"
     :data-options "label:'Enlace Fotos:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "enlacev"
     :name "enlacev"
     :class "easyui-textbox"
     :prompt "Enlace aqui"
     :data-options "label:'Enlace Videos:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "fecha"
     :name "fecha"
     :class "easyui-datebox"
     :prompt "mm/dd/aaaa ex. 02/07/1957 es: Febrero 2 de 1957"
     :data-options "label:'Fecha/Aventura:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "aventura"
     :name "aventura"
     :class "easyui-textbox"
     :prompt "Aqui describir la aventura"
     :data-options "label:'Aventura:',
        labelPosition:'top',
        required:true,
        multiline:true,
        height:360,
        width:'100%'"})))

(defn aventuras-view [title email]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/admin/aventuras"
    (list
     [:th {:data-options "field:'leader_email',sortable:true,width:100"} "Email"]
     [:th {:data-options "field:'enlace',sortable:true,width:100"} "Fotos"]
     [:th {:data-options "field:'enlacev',sortable:true,width:100"} "Videos"]
     [:th {:data-options "field:'fecha_formatted',sortable:true,width:100"} "Fecha"]
     [:th {:data-options "field:'aventura',sortable:true,width:100"} "Aventura"]))
   (build-toolbar)
   (build-dialog title (dialog-fields email))
   (build-dialog-buttons)))

(defn aventuras-scripts []
  (include-js "/js/grid.js"))
