(ns sk.handlers.admin.eventos.view
  (:require
   [hiccup.page :refer [include-js]]
   [ring.util.anti-forgery :refer [anti-forgery-field]]
   [sk.models.util :refer
    [build-dialog build-dialog-buttons build-field build-table build-toolbar build-image-field build-image-field-script]]))

(defn dialog-fields []
  (list
   (build-field
    {:id "id"
     :name "id"
     :type "hidden"})
   (build-image-field)
   (build-field
    {:id "titulo"
     :name "titulo"
     :class "easyui-textbox easyui-validatebox"
     :prompt "Titulo del evento"
     :data-options "label:'Titulo:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "detalles"
     :name "detalles"
     :class "easyui-textbox"
     :prompt "Detalles del evento"
     :data-options "label:'Detalles del evento:',
        labelPosition:'top',
        required:true,
        multiline:true,
        height:220,
        width:'100%'"})
   (build-field
    {:id "lugar"
     :name "lugar"
     :class "easyui-textbox"
     :prompt "Donde se reunen ex. Soriana"
     :data-options "label:'Punto de Reunión:',
        labelPosition:'top',
        required:true,
        multiline:true,
        height:120,
        width:'100%'"})
   (build-field
    {:id "fecha"
     :name "fecha"
     :class "easyui-datebox"
     :prompt "mm/dd/aaaa ex. 02/07/1957 es: Febrero 2 de 1957"
     :data-options "label:'Fecha/Evento:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "hora"
     :name "hora"
     :class "easyui-combobox"
     :prompt "Escojer la hora..."
     :data-options "label:'Hora:',
        labelPosition:'top',
        method: 'GET',
        url:'/table_ref/get-time',
        required:true,
        width:'100%'"})
   (build-field
    {:id "organiza"
     :name "organiza"
     :class "easyui-textbox"
     :prompt "Quién organiza el evento"
     :data-options "label:'Quién Organiza:',
        labelPosition:'top',
        required:true,
        width:'100%'"})))

(defn toolbar-extra []
  (list
   [:a {:href         "javascript:void(0)"
        :class        "easyui-linkbutton"
        :data-options "iconCls:'icon-back',plain:true"
        :onclick      "returnItem('/eventos/list')"} "Regresar a los Eventos"]))

(defn eventos-view [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/admin/eventos"
    (list
     [:th {:data-options "field:'id',sortable:true,width:100"} "Id"]
     [:th {:data-options "field:'fecha_formatted',sortable:true,width:100"} "Fecha"]
     [:th {:data-options "field:'hora_formatted',sortable:true,width:100"} "Hora"]
     [:th {:data-options "field:'titulo',sortable:true,width:100"} "Evento"]
     [:th {:data-options "field:'lugar',sortable:true,width:100"} "Punto de Reunión"]
     [:th {:data-options "field:'organiza',sortable:true,width:100"} "Quíen Organiza"]))
   (build-toolbar (toolbar-extra))
   (build-dialog title (dialog-fields))
   (build-dialog-buttons)))

(defn eventos-scripts []
  (list
   (include-js "/js/grid.js")
   [:script (build-image-field-script)]))
