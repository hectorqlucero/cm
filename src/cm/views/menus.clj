(ns cm.views.menus
  (:require [cm.models.util :refer [user-level]]))

(defn rmenu []
  (list
    [:div#menu-accordion.easyui-accordion {:style "width:100%"
                                           :data-options "onSelect: openPanel"}
     [:div {:title "Informacion"
            :style "overflow:auto;padding:10px;"}
      [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                     :style "text-align:left;display:none;padding:2px;"
                                     :href "/"} "Informacion"]]
     [:div {:title "Eventos"
            :style "overflow:auto;padding:10px;"}
      [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                     :style "text-align:left;display:none;padding:2px;"
                                     :href "/eventos"} "Eventos Ciclistas"]]
     [:div {:title "Rodadas"
            :style "overflow:auto;padding:10px;"}
      [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                     :style "text-align:left;display:none;padding:2px;"
                                     :href "/rodadas"} "Rodadas"]]
     [:div {:title "Talleres"
            :style "overflow:auto;padding:10px;"}
      [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                     :style "text-align:left;display:none;padding:2px;"
                                     :href "/talleres"} "Talleres"]]
     [:div {:title "Grupos"
            :style "overflow:auto;padding:10px;"}
      [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                     :style "text-align:left;display:none;padding:2px;"
                                     :href "/grupos"} "Grupos Ciclistas"]]
     [:div {:title "Entrar"
            :style "overflow:auto;padding:10px;"}
      [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                     :style "text-align:left;display:none;padding:2px;"
                                     :href "/login"} "Entrar al sitio"]]
     [:div {:title "Registrarse"
            :style "overflow:auto;padding:10px;"}
      [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                     :style "text-align:left;display:none;padding:2px;"
                                     :href "/registrar"} "Registrate Aqui"]]
     [:div {:title "Cambiar su Contraseña"
            :style "overflow:auto;padding:10px;"}
      [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                     :style "text-align:left;display:none;padding:2px;"
                                     :href "/rpaswd"} "Cambia tu Contraseña"]]
     [:div {:title "Ayuda"
            :style "padding:10px;"
            :data-options "iconCls:'icon-help'"}
      [:p "Aqui podras entrar al sitio y accessar otras opciones"]]]))

(defn pmenu []
  (list
    [:div#menu-accordion.easyui-accordion {:style "width:100%"
                                           :data-options "onSelect: openPanel"}
     [:div {:title "Informacion"
            :style "overflow:auto;padding:10px;"}
      [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                     :style "text-align:left;display:none;padding:2px;"
                                     :href "/"} "Informacion"]]
     [:div {:title "Eventos"
            :style "overflow:auto;padding:10px;"}
      [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                     :style "text-align:left;display:none;padding:2px;"
                                     :href "/eventos"} "Eventos Ciclistas"]]
     [:div {:title "Rodadas"
            :style "overflow:auto;padding:10px;"}
      [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                     :style "text-align:left;display:none;padding:2px;"
                                     :href "/rodadas"} "Calendario Rodadas"]]
     [:div {:title "Talleres"
            :style "overflow:auto;padding:10px;"}
      [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                     :style "text-align:left;display:none;padding:2px;"
                                     :href "/talleres"} "Talleres"]]
     [:div {:title "Grupos"
            :style "overflow:auto;padding:10px;"}
      [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                     :style "text-align:left;display:none;padding:2px;"
                                     :href "/grupos"} "Grupos Ciclistas"]]
     [:div {:title "Mant. Rodadas"
            :style "overflow:auto;padding:10px;"}
      [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                     :style "text-align:left;display:none;padding:2px;"
                                     :href "/rodadas/crear"} "Mant. Rodadas"]]
     [:div {:title "Cambiar su Contraseña"
            :style "overflow:auto;padding:10px;"}
      [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                     :style "text-align:left;display:none;padding:2px;"
                                     :href "/rpaswd"} "Cambia tu Contraseña"]]
     (if (= (user-level) "S")
       [:div {:title "Administrar"
              :style "overflow:auto;padding:10px;"}
        [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                       :style "text-align:left;display:none;padding:2px;"
                                       :href "/eventos/crear"} "Eventos"]
        [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                       :style "text-align:left;display:none;padding:2px;"
                                       :href "/talleres/crear"} "Talleres"]
        [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                       :style "text-align:left;display:none;padding:2px;"
                                       :href "/cuadrantes/crear"} "Grupos"]])
     [:div {:title "Salir"
            :style "overflow:auto;padding:10px;"}
      [:a.in-menu.easyui-linkbutton {:data-options "plain:true,size:'small',width:'98%',height:'20px;'"
                                     :style "text-align:left;display:none;padding:2px;"
                                     :href "/logoff"} "Salir del sitio"]]
     [:div {:title "Ayuda"
            :style "padding:10px;"
            :data-options "iconCls:'icon-help'"}
      [:p "Aqui podras entrar al sitio y accessar otras opciones"]]]))
