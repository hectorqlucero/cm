(ns cm.views.home
  (:require [cm.models.util :refer [build-form
                                    build-field
                                    build-button]]))

(defn login-scripts []
  [:script
  (str 
    "$(document).ready(function() {
        $(\"a#submit\").click(function() {
          $(\"form.fm\").form(\"submit\");
        });
        $(\"form.fm\").form(\"submit\", {
          onSubmit: function() {
            return $(this).form(\"enableValidation\").form(\"validate\");
          },
          success: function(data) {
            try {
              var dta = JSON.parse(data);
              if(dta.hasOwnProperty('url')) {
                window.location.href = dta.url;
              } else if(dta.hasOwnProperty('error')) {
                $messager.alert('Error', dta.error, 'error');
              }
            } catch(e) {
              console.error(\"Invalid JSON\");
            }
          }
        });
      });")])

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
         :data-options "label:'Contraseña:',labelPosition:'top',required:true,width:'100%'"})
      (build-button
        {:href "javascript:void(0)"
         :id "submit"
         :text "Acceder al Sitio"
         :class "easyui-linkbutton c6"
         :onClick "submitForm(this)"})
      )
    (list
      [:div {:style "margin-bottom:10px;"}
       [:a {:href "/registrar"} "Click para requistrarse"]]
      [:div {:style "margin-bottom:10px;"}
       [:a {:href "/rpaswd"} "Click para resetear su contraseña"]])))

; (defn login-view [token]
;   [:div.container.col-8
;    [:div.card-header
;     [:h4.mb-0 [:span {:style "width:100%;background-color:auto;"} "Entrar al Sitio"]]]
;    [:form.fm {:method "post"
;               :style "width:100%;background-color:#EFEFEF;"}
;     token
;     (build-field
;       "Email:"
;       {:id "username"
;        :name "username"
;        :class "form-control easyui-textbox"
;        :data-options "required:true"})
;     (build-field
;       "Contraseña:"
;       {:id "password"
;        :name "password"
;        :class "form-control easyui-passwordbox"
;        :data-options "required:true"})
;     (build-button
;       "Acceder al sitio"
;       {:href "javascript:void(0)"
;        :id "submit"
;        :class "btn btn-primary easyui-linkbutton"})
;     [:div.form-group.col-10
;      [:label "Nuevo Usuario?"
;       [:a {:href "/registrar"} " Clic para registrarse"]]]
;     [:div.form-group.col-10
;      [:label "Olvido su contraseña"
;       [:a {:href "/rpaswd"} " Clic para resetear su contraseña"]]]]])
