(ns cm.views.routes.rodadas
  (:require [hiccup.page :refer [include-css include-js]]
            [cm.models.util :refer [build-form
                                    build-field
                                    build-radio-buttons
                                    build-button]]))

(defn rodadas-view []
  (list
   (include-css "/font/css/all.min.css")
   (include-css "/fullcalendar/fullcalendar.min.css")
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
    (include-js "/font/js/all.min.js")
    (include-js "/fullcalendar/lib/moment.min.js")
    (include-js "/fullcalendar/fullcalendar.min.js")
    (include-js "fullcalendar/locale-all.js")
    (include-js "/scripts/routes/rodadas_scripts.js")))

(defn asistir-view [title rodadas_id token]
  (build-form
    title
    token
    (list
      (build-field
        {:id "rodadas_id"
         :name "rodadas_id"
         :type "hidden"
         :value (str rodadas_id)})
      (build-field
        {:id "user"
         :name "user"
         :class "easyui-textbox"
         :data-options "label:'Nombre:',labelPosition:'top',
                       required:true,width:'100%'"})
      (build-field
        {:id "comentarios"
         :name "comentarios"
         :class "easyui-textbox"
         :data-options "label:'Comentarios:',labelPosition:'top',
                       width:'100%',height:'120px',required:true,
                       multiline:true"})
      (build-field
        {:id "email"
         :name "email"
         :class "easyui-textbox easyui-validatebox"
         :validType "email"
         :data-options "label:'Email:',labelPosition:'top',
                       width:'100%',required:true"})
      (build-radio-buttons
        "Asistire?"
        (list
          {:id "asistir_si"
           :name "asistir"
           :class "easyui-radiobutton"
           :value "T"
           :label "Si"
           :data-options "checked:true"}
          {:id "asistir_no"
           :name "asistir"
           :class "easyui-radiobutton"
           :label "No"
           :value "F"
           :data-options "checked:false"})))
    (list
      (build-button
        (list
          {:text "Postear"
           :id "submitbtn"
           :class "easyui-linkbutton c6"
           :style "margin-right:5px;margin-bottom:5px;"
           :data-options "iconCls:'icon-ok'"
           :href "javascript:void(0)"
           :onclick "saveData()"}
          {:text "Regresar"
           :id "regresar"
           :class "easyui-linkbutton"
           :style "margin-bottom:5px;"
           :data-options "iconCls:'icon-back'"
           :href "javascript:void(0)"
           :onclick "goBack()"})))))

(defn asistir-scripts []
  (include-js "/scripts/routes/asistir_scripts.js"))
