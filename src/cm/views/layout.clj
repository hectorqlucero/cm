(ns cm.views.layout
  (:require [hiccup.page :refer [html5 include-css include-js]]
            [cm.views.menus :refer [rmenu pmenu]]
            [cm.models.crud :refer [config]]
            [cm.models.util :refer [user-login]]))

(defn app-css []
  (list
   (include-css "/easyui/themes/metro-blue/easyui.css")
   (include-css "/easyui/themes/icon.css")
   (include-css "/easyui/themes/color.css")
   (include-css "/css/main.css")))

(defn app-js []
  (list
    (include-js "/easyui/jquery.min.js")
    (include-js "/easyui/jquery.easyui.min.js")
    (include-js "/easyui/jquery.edatagrid.js")
    (include-js "/easyui/datagrid-detailview.js")
    (include-js "/easyui/datagrid-groupview.js")
    (include-js "/easyui/datagrid-bufferview.js")
    (include-js "/easyui/datagrid-scrollview.js")
    (include-js "/easyui/datagrid-filter.js")
    (include-js "/easyui/locale/easyui-lang-es.js")
    (include-js "/easyui/easyloader.js")
    (include-js "/js/main.js")))

(defn application [title ok js & content]
  (html5
    [:head
     [:title title]
     [:meta {:charset "UTF-8"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1.0, shrink-to-fit=no"}]
     [:meta {:http-equiv "Page-Enter"
             :content "blendTrans(Duration=.01"}]
     [:meta {:http-equiv "Page-Exit"
             :content "blendTrans(Duration=.01)"}]
     (app-css)]
    [:body.easyui-layout
     [:div#loader]
     [:div {:style "height:20px;"
            :data-options "region:'north'"}
      [:div {:style "float:left;padding-left:3px;"} [:span {:style "font-weight:bold;"} "Sitio: "] (config :site-name)]
      [:div {:style "float:right;padding-right:epx;"} [:span {:style "font-weight:bold;"} "Usuario: "] (user-login)]
      ]
     [:div {:style "height:30px;"
            :data-options "region:'south',split:true"} [:span {:style "font-size:.6em"} "Lucero Systems"]]
     [:div#menu-section {:style "padding:10px;"
                         :title "Menu"
                         :data-options "region:'west',
                                        hideCollapsedContent:false,
                                        split:true,
                                        collapsible:false,
                                        collapsed:true,
                                        expandMode:'dock',
                                        onOpen: menu,
                                        width:'25%',
                                        cache:true"}
      (cond
        (= ok -1) nil
        (= ok 0) (rmenu)
        (> ok 0) (pmenu))]
     [:div {:data-options "region:'center'"
            :id "center-container"
            :title "<--- Clic para abrir el menu"
            :style "padding;5px;background:#eee"}
      [:div#content {:style "padding:5px;margin-bottom:5px;"} content]]
     (app-js)
     js]))

(defn error-404 [error return-url]
  [:div
   [:p [:h3 [:b "Error: "]] error]
   [:p [:h3 [:a {:href return-url} "Clic aqui para " [:strong "Regresar"]]]]])
