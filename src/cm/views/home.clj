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