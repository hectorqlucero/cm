(ns sk.handlers.admin.cuadrantes.view
  (:require
   [hiccup.page :refer [include-js]]
   [ring.util.anti-forgery :refer [anti-forgery-field]]
   [sk.models.util :refer
    [build-dialog build-dialog-buttons build-field build-table build-toolbar build-radio-buttons]]))

(defn dialog-fields []
  (list
   (build-field
    {:id "id"
     :name "id"
     :type "hidden"})
   (build-field
    {:id "name"
     :name "name"
     :class "easyui-textbox"
     :prompt "Nombre del grupo"
     :data-options "label:'Nombre:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "leader"
     :name "leader"
     :class "easyui-textbox"
     :prompt "Nombre del lider"
     :data-options "label:'Lider:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "leader_phone"
     :name "leader_phone"
     :class "easyui-textbox"
     :prompt "Telefono del lider"
     :data-options "label:'Telefono:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "leader_cell"
     :name "leader_cell"
     :class "easyui-textbox"
     :prompt "Celular del lider"
     :data-options "label:'Celular:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "leader_email"
     :name "leader_email"
     :class "easyui-textbox"
     :prompt "Email del lider"
     :data-options "label:'Email:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "notes"
     :name "notes"
     :class "easyui-textbox"
     :prompt "Observaciones del grupo"
     :data-options "label:'Observaciones:',
        labelPosition:'top',
        required:false,
        multiline:true,
        height:120,
        width:'100%'"})
   (build-radio-buttons
    "Activo?"
    (list
     {:id "status_no"
      :name "status"
      :class "easyui-radiobutton"
      :value "N"
      :data-options "label:'No',checked:true"}
     {:id "status_si"
      :name "status"
      :class "easyui-radiobutton"
      :value "S"
      :data-options "label:'Si'"}))))

(defn toolbar-extra []
  (list
   [:a {:href         "javascript:void(0)"
        :class        "easyui-linkbutton"
        :data-options "iconCls:'icon-back',plain:true"
        :onclick      "returnItem('/grupos/list')"} "Regresar a Grupos"]))

(defn cuadrantes-view [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/admin/cuadrantes"
    (list
     [:th {:data-options "field:'name',sortable:true,width:100"} "Grupo Ciclista"]
     [:th {:data-options "field:'leader',sortable:true,width:100"} "Lider"]
     [:th {:data-options "field:'leader_phone',sortable:true,width:100"} "Telefono"]
     [:th {:data-options "field:'leader_cell',sortable:true,width:100"} "cell"]
     [:th {:data-options "field:'leader_email',sortable:true,width:100"} "Email"]))
   (build-toolbar (toolbar-extra))
   (build-dialog title (dialog-fields))
   (build-dialog-buttons)))

(defn cuadrantes-scripts []
  (include-js "/js/grid.js"))
