(ns sk.handlers.administrar.rodadas.view
  (:require [hiccup.page :refer [include-js]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [sk.models.util :refer [user-level
                                    build-table
                                    build-dialog
                                    build-dialog-buttons
                                    build-toolbar
                                    build-text-editor
                                    build-field]]))

(defn dialog-fields []
  (list
    (build-field
      {:id "id"
       :name "id"
       :type "hidden" })
    (build-field
      {:id           "titulo"
       :name         "titulo"
       :class        "easyui-textbox"
       :data-options "label:'Titulo para el calendario<small>(ex: VII Gran Fondo</small>):',
                      labelPosition:'top',
                      required: true,
                      width:'100%'"
       :validType    "length[0,100]"})
    (build-text-editor
      {:label "Describir Rodada:"
       :id "texteditor"
       :name "detalles"
       :placeholder "Detalles de la rodada..."})
    (build-field
      {:id           "punto_reunion"
       :name         "punto_reunion"
       :class        "easyui-textbox"
       :data-options "label:'Punto de Reunión(<small>ex. Parque Hidalgo</small>):',
                      labelPosition:'top',
                      multiline:true,
                      width:'100%',
                      height:120"})
    (build-field
      {:id           "nivel"
       :name         "nivel"
       :class        "easyui-combobox"
       :data-options "label:'Nivel:',
                      labelPosition:'top',
                      url:'/table_ref/nivel_options',
                      method:'GET',
                      valueField:'value',
                      textField:'text',
                      width:'100%'"})
    (build-field
      {:id           "distancia"
       :name         "distancia"
       :class        "easyui-textbox"
       :data-options "label:'Distancia:',
                      labelPosition:'top',
                      required: true,
                      width:'100%'"})
    (build-field
      {:id           "velocidad"
       :name         "velocidad"
       :class        "easyui-textbox"
       :data-options "label:'Velocidad:',
                      labelPosition:'top',
                      required: true,
                      width:'100%'"})
    (build-field
      {:id           "fecha"
       :name         "fecha"
       :class        "easyui-datebox"
       :data-options "label:'Fecha/Rodada:',
                      labelPosition:'top',
                      required: true,
                      width:'100%'"})
    (build-field
      {:id           "salida"
       :name         "salida"
       :class        "easyui-combobox"
       :data-options "label:'Salida:',
                      labelPosition:'top',
                      url:'/table_ref/get-time',
                      method:'GET',
                      required:true,
                      width:'100%'"})
    (build-field
      {:id           "leader"
       :name         "leader"
       :class        "easyui-textbox"
       :data-options "label:'Lider:',
                      labelPosition:'top',
                      required: true,
                      width:'100%'"})
    (build-field
      {:id           "leader_email"
       :name         "leader_email"
       :class        "easyui-textbox easyui-validatebox"
       :validType    "email"
       :data-options "label:'Lider Email:',
                      labelPosition:'top',
                      required: true,
                      validType:'email',
                      width:'100%'"})

    (if (= (user-level) "S")
      (list
       [:label "Repetir?"]
       [:div {:style "margin-bottom:5px;"}
        [:input#repetir.easyui-radiobutton {:id "repetir_no"
                                            :name "repetir"
                                            :value "F"
                                            :data-options "label:'No',checked:true"}]]
       [:div {:style "margin-bottom:5px;"}
        [:input#repetir.easyui-radiobutton {:id "repetir_si"
                                            :name "repetir"
                                            :value "T"
                                            :data-options "label:'Si'"}]])
      nil)))

(defn toolbar-extra []
  (list
   [:a {:href         "javascript:void(0)"
        :class        "easyui-linkbutton"
        :data-options "iconCls:'icon-back',plain:true"
        :onclick      "returnItem('/rodadas/list')"} "Regresar al Calendario"]))

(defn rodadas-view [title]
  (list
    (anti-forgery-field)
    (build-table
      title
      "/administrar/rodadas"
      (list
        [:th {:data-options "field:'titulo',sortable:true,fixed:true,width:100"} "Titulo"]
        [:th {:data-options "field:'fecha',sortable:true,fixed:true,width:100"} "Fecha"]
        [:th {:data-options "field:'punto_reunion',sortable:false"} "Punto de Reunión"]
        [:th {:data-options "field:'salida',sortable:true,fixed:true,width:100"} "Salida"]
        [:th {:data-options "field:'nivel',sortable:true,fixed:true,width:100"} "Nivel"]
        [:th {:data-options "field:'distancia',sortable:true,fixed:true,width:100"} "Distancia"]
        [:th {:data-options "field:'velocidad',sortable:true,fixed:true,width:100"} "Velocidad"]))
    (build-toolbar (toolbar-extra))
    (build-dialog title (dialog-fields))
    (build-dialog-buttons)))

(defn rodadas-scripts []
  (list
  (include-js "/js/grid.js")
  [:script
   "
   $(function() {
      $('#texteditor').richText();
    });
    $('.dlg').dialog({
      onOpen: function() {
        $('#texteditor').val('').trigger('change');
      }
    });
    $('.fm').form({
      onLoadSuccess: function(data) {
        $('#texteditor').trigger('change');
      }
    });
   "
   ]))
