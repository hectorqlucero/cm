(ns sk.handlers.admin.rodadas.view
  (:require [hiccup.page :refer [include-js]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [sk.models.util :refer
             [user-level build-dialog build-dialog-buttons build-field build-table build-toolbar build-radio-buttons]]))

(defn dialog-fields []
  (list
   (build-field
    {:id "id"
     :name "id"
     :type "hidden"})
   (build-field
    {:id "titulo"
     :name "titulo"
     :class "easyui-textbox"
     :prompt "Titulo o nombre de la rodada"
     :validType "length[0,100]"
     :data-options "label:'Titulo/Nombre Rodada:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "detalles"
     :name "detalles"
     :class "easyui-textbox"
     :prompt "Detalles de la rodada aqui..."
     :data-options "label:'Detalles:',
        labelPosition:'top',
        required:true,
        multiline:true,
        height:220,
        width:'100%'"})
   (build-field
    {:id "punto_reunion"
     :name "punto_reunion"
     :class "easyui-textbox"
     :prompt "Punto de reunion ej. Soriana"
     :data-options "label:'Putno de Reunión:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
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
    {:id "distancia"
     :name "distancia"
     :class "easyui-textbox"
     :prompt "Distancia de la rodada"
     :data-options "label:'Distancia:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "velocidad"
     :name "velocidad"
     :class "easyui-textbox"
     :prompt "Velocidad de la rodada"
     :data-options "label:'Velocidad:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "fecha"
     :name "fecha"
     :class "easyui-datebox"
     :prompt "mm/dd/aaaa ex. 02/07/1957 es: Febrero 2 de 1957"
     :data-options "label:'Fecha:',
                 labelPosition:'top',
                 required:true,
                 width:'100%'"})

   (build-field
    {:id "salida"
     :name "salida"
     :class "easyui-combobox"
     :prompt "Escojer la hora..."
     :data-options "label:'Salida:',
                 labelPosition:'top',
                 method:'GET',
                 url:'/table_ref/get-time',
                 width:'100%'"})

   (build-field
    {:id "leader"
     :name "leader"
     :class "easyui-textbox"
     :prompt "El lider/organizador de la rodada"
     :data-options "label:'Lider:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "leader_email"
     :name "leader_email"
     :class "easyui-textbox easyui-validatebox"
     :prompt "Correo aqui ex. xxxx@.xxx.com"
     :validType "email"
     :data-options "label:'Lider Email:',
                 labelPosition:'top',
                 required:true,
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
        :onclick      "returnItem('/rodadas/list')"} "Regresar a Rodadas"]))

(defn rodadas-view [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/admin/rodadas"
    (list
     [:th {:data-options "field:'titulo',sortable:true,width:100"} "Titulo"]
     [:th {:data-options "field:'fecha_formatted',sortable:true,width:100"} "Fecha"]
     [:th {:data-options "field:'punto_reunion',sortable:true,width:100"} "Punto_reunion"]
     [:th {:data-options "field:'salida_formatted',sortable:true,width:100"} "Salida"]
     [:th {:data-options "field:'nivel',sortable:true,width:100"
           :formatter "nivelDesc"} "Nivel"]
     [:th {:data-options "field:'distancia',sortable:true,width:100"} "Distancia"]
     [:th {:data-options "field:'velocidad',sortable:true,width:100"} "Velocidad"]))
   (build-toolbar (toolbar-extra))
   (build-dialog title (dialog-fields))
   (build-dialog-buttons)))

(defn rodadas-scripts []
  (list
   (include-js "/js/grid.js")
   [:script
    "
     function nivelDesc(val, row, index) {
       var result = null;
       var scriptUrl = '/table_ref/get-nivel/' + val;
       $.ajax({
         url: scriptUrl,
         type: 'get',
         dataType: 'html',
         async: false,
         success: function(data) {
           result = data;
         }
       });
       return result;
     }
   "]))
