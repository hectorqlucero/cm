(ns sk.handlers.rodadas.view
  (:require [clojure.string :as string]
            [sk.models.util
             :refer
             [build-button build-field build-form build-radio-buttons]]))

(defn line-rr [label value]
  [:div.row
   [:div.col-xs-4.col-sm-4.col-md-3.col-lg-2.text-primary [:strong label]]
   [:div.col-xs.8.col-sm-8.col-md-9.col-lg-10 value]])

(defn body-rr [row]
  [:h2 (:titulo row)
   [:div.card
    [:div.card-body {:style "font-size:.5em;"}
     (line-rr "Fecha:" [:strong.text-warning (str (string/upper-case (:dia row)) (string/upper-case (:f_fecha row)))])
     (line-rr "Detalles: " (:detalles row))
     (line-rr "Punto de reunion: " (:punto_reunion row))
     (line-rr "Hora: " (:salida row))
     (line-rr "Distancia: " (:distancia row))
     (line-rr "Velocidad: " (:velocidad row))
     (line-rr "Lider: " (:leader row))
     (line-rr "Lider Email: " (:leader_email row))
     (line-rr "Confirmar:"
              [:div.card-action
               [:a {:href (str "/rodadas/asistir/" (:id row))
                    :target "_blank"} [:strong.text-primary "Clic para confirmar asistencia"]]])]]])

(defn rr-view [rows]
  (list
   [:div.container
    (map body-rr rows)]))


(defn rr-scripts []
  [:script])


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
  [:script
   "
    var token = $('__anti-forgery-token').val();

    function goBack() {
        window.close();
    }

    function saveData() {
        $('.fm').form('submit', {
            url: '/rodadas/asistir',
            queryParams: {'__anti-forgery-token': token},
            onSubmit: function() {
              if($(this).form('validate')) {
                $('#submitbtn').linkbutton('disable');
                $('#submitbtn').linkbutton({text: 'Processando!'});
              }
              return $(this).form('enableValidation').form('validate');
            },
            success: function(result) {
                var json = JSON.parse(result);
                if(json.error && json.success) {
                    $.messager.show({
                        title: 'Error',
                        msg: json.success + '<br>' + json.error
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
                    window.close();
                }
            }
        });
    }
   "])
