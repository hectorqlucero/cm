(ns cm.views.home
  (:require [hiccup.page :refer [include-js]]
            [cm.models.util :refer [build-form build-field build-button]]))

(defn login-view [token]
  (build-form
    "Ciclismo Mexicali"
    token
    (list
      (build-field
        {:id "username"
         :name "username"
         :class "easyui-textbox easyui-validatebox"
         :data-options "label:'Email:',labelPosition:'top',required:true,width:'100%'"})
      (build-field
        {:id "password"
         :name "password"
         :class "easyui-passwordbox"
         :data-options "label:'Contraseña:',labelPosition:'top',required:true,width:'100%'"}))
    (build-button
      {:href "javascript:void(0)"
       :text "Acceder al Sitio"
       :class "easyui-linkbutton c6"
       :onClick "submitForm()"})))

(defn login-scripts []
  (include-js "/scripts/login_scripts.js"))
