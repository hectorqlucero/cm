(ns cm.views.proutes.eventos
  (:require [hiccup.page :refer [include-js]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [cm.models.util :refer [build-table
                                    build-dialog
                                    build-dialog-buttons
                                    build-toolbar
                                    build-image-field
                                    build-image-field-script
                                    build-text-editor
                                    build-field]]))

(def dialog-fields
  (list
    [:input {:type "hidden" :id "id" :name "id"}]
    (build-image-field)
    (build-field
      {:id           "titulo"
       :name         "titulo"
       :class        "easyui-textbox easyui-validatebox"
       :prompt       "VII Gran Fondo"
       :data-options "label:'Titulo para el calendario<small>(ex: VII Gran Fondo</small>):',
                      labelPosition:'top',
                      width:'100%',
                      required: true"
       :validType    "length[0,100]"})
    (build-text-editor
      {:label "Detalles del evento:"
       :id "texteditor"
       :name "detalles"
       :placeholder "Detalles del evento..."})
    (build-field
      {:id           "lugar"
       :name         "lugar"
       :class        "easyui-textbox"
       :prompt       "Punto de Reunion ej. Parque Hidalgo..."
       :data-options "label:'Punto de Reunión:',
                      labelPosition:'top',
                      width:'100%',
                      multiline:true,height:120"})
    (build-field
      {:id           "fecha"
       :name         "fecha"
       :prompt       "mm/dd/yyyy"
       :class        "easyui-datebox"
       :data-options "label:'Fecha/Evento:',
                      labelPosition:'top',
                      width:'100%',
                      required: true"})
    (build-field
      {:id    "hora"
       :name  "hora"
       :prompt "20:00"
       :class "easyui-timespinner"
       :data-options "label:'Hora:',
                      labelPosition:'top',
                      width:'100%'"})
    (build-field
      {:id           "organiza"
       :name         "organiza"
       :prompt       "Cuadrante Rosita"
       :class        "easyui-textbox"
       :data-options "label:'Quién Organiza:',
                      labelPosition:'top',
                      width:'100%',
                      required: true"})))

(defn toolbar-extra []
  (list
   [:a {:href         "javascript:void(0)"
        :class        "easyui-linkbutton"
        :data-options "iconCls:'icon-back',plain:true"
        :onclick      "returnItem('/eventos')"} "Regresar a los Eventos"]))

(defn eventos-view [title]
  (list
    (anti-forgery-field)
    (build-table
      title
      "/eventos/crear"
      (list
        [:th {:data-options "field:'fecha',sortable:true"} "Fecha"]
        [:th {:data-options "field:'hora',sortable:true"} "Hora"]
        [:th {:data-options "field:'titulo',sortable:true"} "Evento"]
        [:th {:data-options "field:'lugar',sortable:false"} "Punto de Reunión"]
        [:th {:data-options "field:'organiza',sortable:true"} "Quién Organiza"]))
    (build-toolbar (toolbar-extra))
    (build-dialog title dialog-fields)
    (build-dialog-buttons)))

(defn eventos-scripts []
  (list
    (include-js "/js/grid.js") 
    (include-js "/scripts/proutes/eventos_scripts.js")))
