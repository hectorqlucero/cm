(ns cm.views.layout
  (:require [hiccup.page :refer [html5 include-css include-js]]
            [cm.models.util :refer [user-level]]))

(defn build-admin []
  (if (= (user-level) "S")
    (do
      (list
        [:a.dropdown-item {:href "/rodadas/crear"} "Rodadas"]
        [:a.dropdown-item {:href "/eventos/crear"} "Eventos"]
        [:a.dropdown-item {:href "/talleres/crear"} "Talleres"]
        [:a.dropdown-item {:href "/cuadrantes/crear"} "Grupos"]))
    [:a.dropdown-item {:href "/rodadas/crear"} "Rodadas"]))

(defn menus-private []
  (list
    [:nav.navbar.navbar-expand-sm.navbar-dark.bg-primary.fixed-top
     [:a.navbar-brand {:href "/"} "Ciclismo Mexicali"]
     [:button.navbar-toggler {:type "button"
                              :data-toggle "collapse"
                              :data-target "#collapsibleNavbar"}
      [:span.navbar-toggler-icon]]
     [:div#collapsibleNavbar.collapse.navbar-collapse
      [:ul.navbar-nav
       [:li.nav-item [:a.nav-link {:href "/eventos"} "Eventos"]]
       [:li.nav-item [:a.nav-link {:href "/rodadas"} "Rodadas"]]
       [:li.nav-item [:a.nav-link {:href "/talleres"} "Talleres"]]
       [:li.nav-item [:a.nav-link {:href "/grupos"} "Grupos"]]
       [:li.nav-item.dropdown
        [:a.nav-link.dropdown-toggle {:href "#"
                                      :id "navdrop"
                                      :data-toggle "dropdown"} "Administrar"]
        [:div.dropdown-menu
         (build-admin)]]
       [:li.nav-item [:a.nav-link {:href "/logoff"} "Salir"]]]]]))

(defn menus-public []
  (list
    [:nav.navbar.navbar-expand-sm.navbar-dark.bg-primary.fixed-top
     [:a.navbar-brand {:href "/"} "Ciclismo Mexicali"]
     [:button.navbar-toggler {:type "button"
                              :data-toggle "collapse"
                              :data-target "#collapsibleNavbar"}
      [:span.navbar-toggler-icon]]
     [:div#collapsibleNavbar.collapse.navbar-collapse
      [:ul.navbar-nav
       [:li.nav-item [:a.nav-link {:href "/eventos"} "Eventos"]]
       [:li.nav-item [:a.nav-link {:href "/rodadas"} "Rodadas"]]
       [:li.nav-item [:a.nav-link {:href "/talleres"} "Talleres"]]
       [:li.nav-item [:a.nav-link {:href "/grupos"} "Grupos"]]
       [:li.nav-item [:a.nav-link {:href "/login"} "Entrar"]]
       ]]]))

(defn app-css []
  (list
    (include-css "/easyui/themes/metro-blue/easyui.css")
    (include-css "/easyui/themes/icon.css")
    (include-css "/easyui/themes/color.css")
    (include-css "/css/main.css")
    (include-css "/bootstrap/css/bootstrap.min.css")
    (include-css "/bootstrap/css/lumen.min.css")
    (include-css "/css/main.css")))

(defn app-js []
  (list
    (include-js "/easyui/jquery.min.js")
    (include-js "/popper/popper.min.js")
    (include-js "/bootstrap/js/bootstrap.min.js")
    (include-js "/easyui/jquery.easyui.min.js")
    (include-js "/easyui/jquery.edatagrid.js")
    (include-js "/easyui/datagrid-detailview.js")
    (include-js "/easyui/datagrid-groupview.js")
    (include-js "/easyui/datagrid-bufferview.js")
    (include-js "/easyui/datagrid-scrollview.js")
    (include-js "/easyui/datagrid-filter.js")
    (include-js "/easyui/locale/easyui-lang-es.js")
    (include-js "/js/main.js")))

(defn application [title ok js & content]
  (html5 {:ng-app "Inventario" :lang "es"}
         [:head
          [:title title]
          [:meta {:charset "UTF-8"}]
          [:meta {:name "viewport"
                  :content "width=device-width, initial-scale=1"}]
          (app-css)]
         [:body
          (cond
            (= ok -1) nil
            (= ok 0) (menus-public)
            (> ok 0) (menus-private))
          [:div#content.container-fluid.easyui-panel {:style "margin-top:75px;border:none;"
                                              :data-options "closed:false"} 
           content]
          (app-js)
          js]))

(defn error-404 [error return-url]
  [:div
   [:p [:h3 [:b "Error: "]] error]
   [:p [:h3 [:a {:href return-url} "Clic aqui para " [:strong "Regresar"]]]]])
