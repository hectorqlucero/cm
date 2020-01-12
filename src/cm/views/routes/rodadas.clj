(ns cm.views.routes.rodadas
  (:require [hiccup.page :refer [include-css
                                 include-js]]))

(defn rodadas-view []
  (list
   (include-css "/bootstrap/css/bootstrap.min.css")
   (include-css "/bootstrap/css/lumen.min.css")
   (include-css "/font/css/all.min.css")
   (include-css "/fullcalendar/fullcalendar.min.css")
   (include-css "/fullcandar/fullcalendar.print.min.css")
   [:div#calendar]
   [:div#fullCalModal.modal.fade
    [:div.modal-dialog
     [:div.modal-content
      [:div.modal-header
       [:h4#modalTitle.model-title]
       [:button.close {:type         "button"
                       :data-dismiss "modal"}
        [:span {:aria-hidden "true"} "x"]
        [:span.sr-only "Cerrar"]]]
      [:div#modalBody.modal-body]
      [:div.modal-footer
       [:button.btn.btn-default {:type         "button"
                                 :data-dismiss "modal"} "Regresar al Calendario"]
       [:a#eventUrl.btn.btn-primary {:target "_blank"} "Confirmar Asistencia"]]]]]))

(defn rodadas-scripts []
  (list
    (include-js "/popper/popper.min.js")
    (include-js "/bootstrap/js/bootstrap.min.js")
    (include-js "/font/js/all.min.js")
    (include-js "/fullcalendar/lib/moment.min.js")
    (include-js "/fullcalendar/fullcalendar.min.js")
    (include-js "fullcalendar/locale-all.js")
    (include-js "/scripts/routes/rodadas_scripts.js")))

(defn asistir-view [title rodadas_id token])

; (defn asistir-view [title rodadas_id token]
;   [:div.container.col-8
;    token
;    [:div#p.easyui-panel {:title title
;                          :style "width:80%;height:auto;padding:10px;background:#EFEFEF"}
;     [:form.fm {:enctype "multipart/form-data"
;                :method  "post"
;                :style   "width:100%;background-color:#EFEFEF;"}
;      [:input {:type  "hidden"
;               :id    "rodadas_id"
;               :name  "rodadas_id"
;               :value (str rodadas_id)}]
;      (build-field
;       "Nombre:"
;       {:id           "user"
;        :name         "user"
;        :class        "form-control easyui-textbox"
;        :data-options "required:true,width:'100%'"})
;      (build-field
;       "Comentarios:"
;       {:id           "comentarios"
;        :name         "comentarios"
;        :class        "form-control easyui-textbox"
;        :multiline    "true"
;        :data-options "required:true,width:'100%'"
;        :style        "height:120px;"})
;      (build-field
;       "Email:"
;       {:id           "email"
;        :name         "email"
;        :class        "form-control easyui-validatebox"
;        :validType    "email"
;        :data-options "required:true,width:'100%'"})

;      (build-radio-buttons
;       "Asistire?"
;       (list
;        {:id           "asistir"
;         :name         "asistir"
;         :class        "form-control easyui-radiobutton"
;         :value        "T"
;         :label        "Si"
;         :data-options "checked:true"}
;        {:id           "asistir"
;         :name         "asistir"
;         :class        "form-control easyui-radiobutton"
;         :label        "No"
;         :value        "F"
;         :data-options "checked:false"}))

;      (build-button
;       (list
;        {:label "Postear"
;         :id "submitbtn"
;         :class "easyui-linkbutton c6"
;         :style "margin-right:5px;margin-bottom:5px;"
;         :data-options "iconCls:'icon-ok'"
;         :href "javascript:void(0)"
;         :onclick "saveData()"}
;        {:label "Regresar"
;         :id "regresar"
;         :class "easyui-linkbutton"
;         :style "margin-bottom:5px;"
;         :data-options "iconCls:'icon-back'"
;         :href "javascript:void(0)"
;         :onClick "goBack()"}))]]])

(defn asistir-scripts []
  (list
   [:script
    (str
     "
      function goBack() {
          window.location.href = \"/rodadas\";
      }

      function saveData() {
          $(\".fm\").form(\"submit\", {
              url: '/rodadas/asistir',
              queryParams: {'__anti-forgery-token': token},
              onSubmit: function() {
                  return $(this).form(\"enableValidation\").form(\"validate\");
              },
              success: function(result) {
                  $(\"#submitbtn\").linkbutton('disable');
                  var json = JSON.parse(result);
                  if(json.error && json.success) {
                      $.messager.show({
                          title: 'Error',
                          msg: json.success + \"<br>\" + json.error
                      });
                  } else if (json.error) {
                      $.messager.show({
                          title: 'Error',
                          msg: json.error
                      });
                  } else if (json.success) {
                      $.messager.show({
                          title: 'Success',
                          msg: json.success
                      });
                      window.location.href=\"/rodadas\";
                  }
                  $(\"#submitbtn\").linkbutton('enable');
              }
          });
      }
      ")]))
