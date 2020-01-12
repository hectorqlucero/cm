(ns cm.views.registrar
  (:require [hiccup.page :refer [include-js]]
            [cm.models.util :refer [build-form build-field build-button]]))

;; Start registrar
(defn registrar-view [title token]
  (build-form 
    title 
    token 
    (list
      (build-field {:id "firstname"
                    :name "firstname"
                    :class "easyui-textbox"
                    :data-options "label:'Nombre:', labelPosition:'top', required:true,width:'100%'"})
      (build-field {:id "lastname"
                    :name "lastname"
                    :class "easyui-textbox"
                    :data-options "label:'Apellidos:', labelPosition:'top', required:true,width:'100%'"})
      (build-field {:id "email"
                    :name "email"
                    :class "easyui-validatebox"
                    :validType "email"
                    :data-options "label:'Correo Electronico:', labelPosition:'top', required:true,width:'100%'"})
      (build-field {:id "password"
                    :name "password"
                    :class "easyui-passwordbox"
                    :data-options "label:'Contraseña:', labelPosition:'top', required:true,width:'100%'"})
      (build-field {:id "password1"
                    :name "password1"
                    :class "easyui-passwordbox"
                    :validType "confirmPass['#password']"
                    :data-options "label:'Confirmar Contraseña:', labelPosition:'top', required:true,width:'100%'"}))
    (list
      (build-button {:href "javascript:void(0)"
                     :text "Registrarse"
                     :class "easyui-linkbutton c6"
                     :id "submit"}))))

(defn registrar-scripts []
  (include-js "/scripts/registrar_scripts.js"))
;; End registrar

;; Start reset-password
(defn reset-password-view [title token]
  (build-form 
    title 
    token 
    (list
      (build-field {:type "hidden" :name "username"})
      (build-field {:id "email"
                    :name "email"
                    :class "easyui-textbox"
                    :validType "email"
                    :data-options "label:'Correo Electronico:', labelPosition:'top', required:true,width:'100%'"}))
    (build-button
      {:id "submit"
       :href "javascript:void(0)"
       :text "Resetear Contraseña"
       :onclick "submitForm()"
       :class "easyui-linkbutton c6"})))

(defn reset-password-scripts []
  (include-js "/scripts/reset_password_scripts.js"))
;; End reset-password

;; Start reset-jwt
(defn reset-jwt-view [title token username]
  (build-form
    title
    token
    (list
      [:input {:type "hidden" :name "username" :value username}]
      (build-field 
        {:id "password"
         :name "password"
         :class "easyui-passwordbox"
         :data-options "label:'Contraseña:',labelPosition:'top',required:true,width:'100%'"})
      (build-field
        {:id "password1"
         :name "password1"
         :class "easyui-passwordbox"
         :validType "confirmPass['#password']"
         :data-options "label:'Confirmar Contraseña:',labelPosition:'top',required:true,width:'100%'"}))
    (list
      (build-button
        {:id "submit"
         :href "javascript:void(0)"
         :text "Cambiar Contraseña"
         :class "easyui-linkbutton c6"}))))

(defn reset-jwt-scripts []
  (include-js "/scripts/reset_jwt_scripts.js"))
;; End reset-jwt
